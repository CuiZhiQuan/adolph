package com.transaction.adolph.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.transaction.adolph.domain.ConsistencyParticipant;
import com.transaction.adolph.domain.ConsistencyTransaction;
import com.transaction.adolph.domain.enums.ConsistencyEnum;
import com.transaction.adolph.mapper.ConsistencyTransactionMapper;
import com.transaction.adolph.service.ConsistencyService;
import com.transaction.adolph.utils.JSONMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 18:17
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Component
public class ConsistencyJob {

    private static final Logger logger = LoggerFactory.getLogger(ConsistencyJob.class);

    @Autowired
    private ConsistencyService consistencyService;

    @Autowired
    private ConsistencyTransactionMapper consistencyTransactionMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    private static final long SEND_RATE = 500L;

    private static final long RE_SEND_RATE = 60 * 1000L;

    private static final long CHECK_RATE = 2 * 60 * 1000L;

    private static final int PAGE_INDEX = 1;

    private static final int PAGE_SIZE = 50;

    /**
     * 发送MQ消息
     */
    @Scheduled(fixedRate = SEND_RATE)
    //TODO 这里需要加分布式锁
    public void sendMq() {
        List<ConsistencyTransaction> transactions = this.listToBeSent(ConsistencyEnum.Status.TO_BE_SENT.getIndex());
        for (ConsistencyTransaction transaction : transactions){
            this.doSend(transaction.getTxId());
            consistencyService.updateStatus(transaction.getTxId(),ConsistencyEnum.Status.SENT.getIndex());
        }
    }

    @Scheduled(fixedRate = RE_SEND_RATE)
    //TODO 这里需要加分布式锁
    public void resendMq() {
        List<ConsistencyTransaction> transactions = this.listSent(ConsistencyEnum.Status.SENT.getIndex());
        for (ConsistencyTransaction transaction : transactions){
            this.doSend(transaction.getTxId());
        }
    }

    @Scheduled(fixedRate = CHECK_RATE)
    //TODO 这里需要加分布式锁
    public void check() {
        List<ConsistencyTransaction> transactions = this.listHasParticipant(ConsistencyEnum.Status.HAS_PARTICIPANT.getIndex());
        for (ConsistencyTransaction transaction : transactions){
            this.doCheck(transaction.getCheckUri(),transaction.getTxId());
        }
    }

    /**
     * 发送MQ消息
     * @param txId
     */
    private void doSend(Long txId){
        List<ConsistencyParticipant> participants = consistencyService.listParticipants(txId);
        for (ConsistencyParticipant participant : participants){
            String message = this.addPIdToMessage(participant.getMessage(),participant.getId());
            rabbitTemplate.convertAndSend(participant.getExchangeName(),participant.getRoutingKey(),message);
        }
    }

    /**
     * 在消息中添加事务参与者ID
     * @param message
     * @param txPId
     * @return
     */
    private String addPIdToMessage(String message,Long txPId){
        JSONObject jsonObject = JSON.parseObject(message);
        jsonObject.put("txPId",txPId);
        return jsonObject.toJSONString();
    }

    /**
     * 执行校验
     * @param checkUri
     */
    private void doCheck(String checkUri,Long txId){
        JSONMessage message = restTemplate.getForObject(checkUri, JSONMessage.class,txId);
        if (message.getResultCode().equals(1)) {
            if (message.getData().equals(true)) {
                consistencyService.updateStatus(txId,ConsistencyEnum.Status.TO_BE_SENT.getIndex());
            }
        } else {
            logger.error("compensate error url:{} errorMessage {}", checkUri, message.getDetailMsg());
        }
    }

    /**
     * 查找待发送
     * @param status
     * @return
     */
    private List<ConsistencyTransaction> listToBeSent(Integer status){
        return this.listByStatus(status);
    }

    /**
     * 查找已发送但事务参与者未确认
     * @param status
     * @return
     */
    private List<ConsistencyTransaction> listSent(Integer status){
        return this.listByStatus(status);
    }

    /**
     * 查找已添加事务参与者但事务发起者未确认
     * @param status
     * @return
     */
    private List<ConsistencyTransaction> listHasParticipant(Integer status){
        return this.listByStatus(status);
    }

    /**
     * 根据状态查找
     * @param status
     * @return
     */
    private List<ConsistencyTransaction> listByStatus(Integer status){
        Page<ConsistencyTransaction> page = PageHelper.startPage(PAGE_INDEX,PAGE_SIZE);
        Example example = new Example(ConsistencyTransaction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status", status);
        example.setOrderByClause("createTime asc");
        return consistencyTransactionMapper.selectByExample(example);
    }


}
