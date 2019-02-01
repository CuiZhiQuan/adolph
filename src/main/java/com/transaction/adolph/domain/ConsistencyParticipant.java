package com.transaction.adolph.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 10:58
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Data
@Table(name = "t_consistency_participant")
public class ConsistencyParticipant {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 事务ID
     */
    private Long txId;

    /**
     * 消息需要发送到的Exchange名称
     */
    private String exchangeName;

    /**
     * 路由Key
     */
    private String routingKey;

    /**
     * 具体的消息内容，包含txId
     */
    private String message;

    /**
     * 状态
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
