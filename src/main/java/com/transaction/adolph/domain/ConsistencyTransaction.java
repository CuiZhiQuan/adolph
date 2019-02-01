package com.transaction.adolph.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 10:46
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Data
@Table(name = "t_consistency_transaction")
public class ConsistencyTransaction {

    /**
     * 事务ID
     */
    @Id
    private Long txId;

    /**
     * 校验事务发起者是否成功的URL
     */
    private String checkUri;

    /**
     * 事务状态
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
