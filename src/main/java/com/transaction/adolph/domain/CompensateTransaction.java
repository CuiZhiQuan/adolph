package com.transaction.adolph.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 15:41
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Data
@Table(name = "t_compensate_transaction")
public class CompensateTransaction {

    /**
     * 事务ID
     */
    @Id
    private Long txId;

    /**
     * 校验事务发起者是否成功所使用的URL
     */
    private String checkUri;

    /**
     * 执行补偿的时间
     */
    private Timestamp executeTime;

    /**
     * 过期时间
     */
    private Timestamp expirationTime;

    /**
     * 状态 0
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;
}
