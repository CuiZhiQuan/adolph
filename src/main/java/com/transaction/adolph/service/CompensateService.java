package com.transaction.adolph.service;

import com.transaction.adolph.controller.param.CompensateParam;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 17:16
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public interface CompensateService {

    /**
     * 注册补偿事务
     * @param param
     * @return
     */
    Long register(CompensateParam param);

    /**
     * 更新事务状态
     * @param txId
     * @param status
     */
    void updateStatus(Long txId, Integer status);
}
