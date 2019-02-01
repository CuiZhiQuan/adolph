package com.transaction.adolph.service.impl;

import com.transaction.adolph.controller.param.ConsistencyParam;
import com.transaction.adolph.domain.ConsistencyParticipant;
import com.transaction.adolph.domain.ConsistencyTransaction;
import com.transaction.adolph.domain.enums.ConsistencyEnum;
import com.transaction.adolph.mapper.ConsistencyParticipantMapper;
import com.transaction.adolph.mapper.ConsistencyTransactionMapper;
import com.transaction.adolph.service.ConsistencyService;
import com.transaction.adolph.utils.IDGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 14:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Service
public class ConsistencyServiceImpl implements ConsistencyService {

    @Autowired
    private IDGenerator idGenerator;

    @Autowired
    private ConsistencyTransactionMapper consistencyTransactionMapper;

    @Autowired
    private ConsistencyParticipantMapper consistencyParticipantMapper;

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Long register(String checkUri) {
        ConsistencyTransaction transaction = new ConsistencyTransaction();
        long txId = idGenerator.nextId();
        transaction.setTxId(txId);
        transaction.setCheckUri(checkUri);
        transaction.setStatus(ConsistencyEnum.Status.NEW.getIndex());
        long now = System.currentTimeMillis();
        Timestamp timestamp = new Timestamp(now);
        transaction.setCreateTime(timestamp);
        transaction.setUpdateTime(timestamp);
        consistencyTransactionMapper.insert(transaction);
        return txId;
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void add(List<ConsistencyParam> params) {
        for (ConsistencyParam param : params){
            ConsistencyParticipant participant = new ConsistencyParticipant();
            BeanUtils.copyProperties(param,participant);
            participant.setId(idGenerator.nextId());
            participant.setStatus(ConsistencyEnum.Status.HAS_PARTICIPANT.getIndex());
            long now = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(now);
            participant.setCreateTime(timestamp);
            participant.setUpdateTime(timestamp);
            consistencyParticipantMapper.insert(participant);
        }
        long txId = params.get(0).getTxId();
        this.updateTransactionStatus(txId,ConsistencyEnum.Status.HAS_PARTICIPANT.getIndex());
    }

    @Override
    public void updateTransactionStatus(Long txId, Integer status) {
        ConsistencyTransaction transaction = new ConsistencyTransaction();
        transaction.setTxId(txId);
        transaction.setStatus(status);
        consistencyTransactionMapper.updateByPrimaryKeySelective(transaction);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateStatus(Long txId,Integer status) {
        this.updateTransactionStatus(txId,status);
        this.updateParticipantStatus(txId,status);
    }

    /**
     * 更新事务参与者状态
     * @param txId
     * @param status
     */
    private void updateParticipantStatus(Long txId,Integer status){
        ConsistencyParticipant participant = new ConsistencyParticipant();
        participant.setStatus(status);
        Example example = new Example(ConsistencyParticipant.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("txId",txId);
        consistencyParticipantMapper.updateByExampleSelective(participant,example);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateParticipantStatusById(Long txPId,Integer status){
        ConsistencyParticipant participant = new ConsistencyParticipant();
        participant.setId(txPId);
        participant.setStatus(status);
        consistencyParticipantMapper.updateByPrimaryKeySelective(participant);
    }

    @Override
    @Transactional(rollbackFor = Throwable.class)
    public void updateStatus(Long txId, Long txPId) {
        this.updateParticipantStatusById(txPId,ConsistencyEnum.Status.SUCCESS.getIndex());
        List<ConsistencyParticipant> participants = this.listParticipants(txId);
        boolean allSuccess = true;
        for (ConsistencyParticipant participant : participants){
            boolean success = ConsistencyEnum.Status.SUCCESS.getIndex().equals(participant.getStatus());
            if(!success){
                allSuccess = false;
                break;
            }
        }
        if (allSuccess){
            this.updateTransactionStatus(txId,ConsistencyEnum.Status.SUCCESS.getIndex());
        }
    }

    @Override
    public List<ConsistencyParticipant> listParticipants(Long txId){
        Example example = new Example(ConsistencyParticipant.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("txId",txId);
        return consistencyParticipantMapper.selectByExample(example);
    }
}
