package com.transaction.adolph.service;

import com.transaction.adolph.controller.param.ConsistencyParam;
import com.transaction.adolph.domain.ConsistencyParticipant;

import java.util.List;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 14:32
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public interface ConsistencyService {

    /**
     * 注册事务
     * @param checkUri
     * @return
     */
    Long register(String checkUri);

    /**
     * 新增事务参与者
     * @param params
     */
    void add(List<ConsistencyParam> params);

    /**
     * 更新事务状态
     * @param txId
     * @param status
     */
    void updateTransactionStatus(Long txId,Integer status);

    /**
     * 更新事务状态
     * @param txId
     * @param status
     */
    void updateStatus(Long txId,Integer status);

    /**
     * 更新事务状态
     * @param txId
     * @param txPId
     */
    void updateStatus(Long txId,Long txPId);

    /**
     * 更新事务参与者状态
     * @param txPId
     * @param status
     */
   void updateParticipantStatusById(Long txPId,Integer status);

    /**
     * 获取事务参与者
     * @param txId
     * @return
     */
    List<ConsistencyParticipant> listParticipants(Long txId);

}
