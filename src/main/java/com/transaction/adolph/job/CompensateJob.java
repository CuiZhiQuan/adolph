package com.transaction.adolph.job;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.transaction.adolph.domain.CompensateParticipant;
import com.transaction.adolph.domain.CompensateTransaction;
import com.transaction.adolph.domain.enums.CompensateEnum;
import com.transaction.adolph.mapper.CompensateParticipantMapper;
import com.transaction.adolph.mapper.CompensateTransactionMapper;
import com.transaction.adolph.service.CompensateService;
import com.transaction.adolph.utils.JSONMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cuizhiquan
 * @Description 补偿事务定时任务
 * @date 2019/1/29 21:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Component
public class CompensateJob {

    private static final Logger logger = LoggerFactory.getLogger(CompensateJob.class);

    @Autowired
    private CompensateService compensateService;

    @Autowired
    private CompensateTransactionMapper compensateTransactionMapper;

    @Autowired
    private CompensateParticipantMapper compensateParticipantMapper;

    @Autowired
    private RestTemplate restTemplate;

    private static final long CHECK_RATE = 20 * 1000;

    private static final int PAGE_INDEX = 1;

    private static final int PAGE_SIZE = 50;

    /**
     * 补偿事务定时任务 20秒 执行一次
     */
    @Scheduled(fixedRate = CHECK_RATE)
    //TODO 这里需要加分布式锁
    private void checkTransaction(){
        Example example = this.constructExample();
        Page<CompensateTransaction> page = PageHelper.startPage(PAGE_INDEX,PAGE_SIZE);
        List<CompensateTransaction> list = compensateTransactionMapper.selectByExample(example);
        for (CompensateTransaction transaction : list){
            JSONMessage message = restTemplate.getForObject(transaction.getCheckUri(), JSONMessage.class,transaction.getTxId());
            if (message.getResultCode().equals(1)) {
                if (message.getData().equals(true)) {
                    compensateService.updateStatus(transaction.getTxId(),CompensateEnum.Status.SUCCESS.getIndex());
                }
                if (message.getData().equals(false)) {
                    this.compensate(transaction.getTxId());
                }
            } else {
                logger.error("compensate error url:{} errorMessage {}", transaction.getCheckUri(), message.getDetailMsg());
            }
        }
    }

    /**
     * 构造查询条件
     * @return
     */
    private Example constructExample(){
        Example example = new Example(CompensateTransaction.class);
        Example.Criteria criteria = example.createCriteria();
        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now);
        criteria.andLessThanOrEqualTo("executeTime",timestamp);
        criteria.andGreaterThanOrEqualTo("expirationTime",timestamp);
        criteria.andEqualTo("status", CompensateEnum.Status.INITIAL.getIndex());
        example.setOrderByClause("createTime asc");
        return example;
    }

    /**
     * 补偿
     * @param txId
     */
    private void compensate(Long txId) {
        List<CompensateParticipant> participants = this.listParticipants(txId);
        boolean allSuccess = true;
        for (CompensateParticipant participant : participants){
            boolean success = this.doCompensate(participant.getCompensateUri(),participant.getTxId());
            if(!success){
                allSuccess = false;
                break;
            }
        }
        if (allSuccess){
            compensateService.updateStatus(txId,CompensateEnum.Status.COMPENSATE.getIndex());
        }
    }

    /**
     * 获取事务参与者
     * @param txId
     * @return
     */
    private List<CompensateParticipant> listParticipants(Long txId){
        Example example = new Example(CompensateParticipant.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("txId",txId);
        criteria.andEqualTo("status", CompensateEnum.Status.INITIAL.getIndex());
        return compensateParticipantMapper.selectByExample(example);
    }


    /**
     * 执行补偿
     * @param compensateUri
     * @param txId
     * @return
     */
    private boolean doCompensate(String compensateUri,Long txId){
        JSONMessage message = restTemplate.getForObject(compensateUri, JSONMessage.class,txId);
        return message.getResultCode().equals(1);
    }
}
