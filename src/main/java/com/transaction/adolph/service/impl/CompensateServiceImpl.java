package com.transaction.adolph.service.impl;

import com.transaction.adolph.controller.param.CompensateParam;
import com.transaction.adolph.domain.CompensateParticipant;
import com.transaction.adolph.domain.CompensateTransaction;
import com.transaction.adolph.domain.enums.CompensateEnum;
import com.transaction.adolph.mapper.CompensateParticipantMapper;
import com.transaction.adolph.mapper.CompensateTransactionMapper;
import com.transaction.adolph.service.CompensateService;
import com.transaction.adolph.utils.IDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 17:18
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Service
public class CompensateServiceImpl implements CompensateService{

    private static final Logger logger = LoggerFactory.getLogger(CompensateServiceImpl.class);

    @Autowired
    private IDGenerator idGenerator;

    @Autowired
    private CompensateTransactionMapper compensateTransactionMapper;

    @Autowired
    private CompensateParticipantMapper compensateParticipantMapper;

    private static final long ONE_MINUTE = 60 * 1000L;

    private static final long THIRTY_MINUTES = 30 * 60 * 1000L;



    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Long register(CompensateParam param) {
        long txId = idGenerator.nextId();
        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now);
        //保存事务信息
        this.saveTransaction(txId,now,timestamp,param);
        //保存事务参与者信息
        this.saveParticipants(param.getParticipantsUri(),txId,timestamp);
        return txId;
    }

    /**
     * 保存事务信息
     * @param txId
     * @param now
     * @param timestamp
     * @param param
     */
    private void saveTransaction(long txId,long now,Timestamp timestamp,CompensateParam param){
        CompensateTransaction transaction = new CompensateTransaction();
        transaction.setTxId(txId);
        transaction.setCheckUri(param.getCheckUri());
        if (Objects.equals(param.getExecuteTime(),0L)){
            param.setExecuteTime(now + ONE_MINUTE);
        }
        transaction.setExecuteTime(new Timestamp(param.getExecuteTime()));
        if(Objects.equals(param.getExpirationTime(),0L)){
            param.setExpirationTime(now + THIRTY_MINUTES);
        }
        transaction.setExpirationTime(new Timestamp(param.getExpirationTime()));
        transaction.setStatus(CompensateEnum.Status.INITIAL.getIndex());
        transaction.setCreateTime(timestamp);
        transaction.setUpdateTime(timestamp);
        compensateTransactionMapper.insert(transaction);
    }

    /**
     * 保存事务参与者信息
     * @param participantsUri
     * @param txId
     * @param timestamp
     */
    private void saveParticipants(List<String> participantsUri,long txId,Timestamp timestamp){
        for (String participantUri : participantsUri){
            CompensateParticipant participant = new CompensateParticipant();
            participant.setId(idGenerator.nextId());
            participant.setTxId(txId);
            participant.setCompensateUri(participantUri);
            participant.setStatus(CompensateEnum.Status.INITIAL.getIndex());
            participant.setCreateTime(timestamp);
            participant.setUpdateTime(timestamp);
            compensateParticipantMapper.insert(participant);
        }
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateStatus(Long txId, Integer status){
        this.updateTransactionStatus(txId, status);
        this.updateParticipantStatus(txId, status);
    }

    /**
     * 更新事务发起者状态
     * @param txId
     * @param status
     */
    private void updateTransactionStatus(Long txId,Integer status){
        CompensateTransaction transaction = new CompensateTransaction();
        transaction.setTxId(txId);
        transaction.setStatus(status);
        compensateTransactionMapper.updateByPrimaryKeySelective(transaction);
    }

    /**
     * 更新事务参与者状态
     * @param txId
     * @param status
     */
    private void updateParticipantStatus(Long txId,Integer status){
        CompensateParticipant participant = new CompensateParticipant();
        participant.setStatus(status);
        Example example = new Example(CompensateParticipant.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("txId",txId);
        compensateParticipantMapper.updateByExampleSelective(participant,example);
    }
}
