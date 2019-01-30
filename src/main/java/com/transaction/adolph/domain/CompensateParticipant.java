package com.transaction.adolph.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 15:50
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Data
@Table(name = "t_compensate_participant")
public class CompensateParticipant {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 事务Id
     */
    private Long txId;

    /**
     * 补偿所使用的URL
     */
    private String compensateUri;

    /**
     * 状态 0 没有补偿 1补偿成功 2 过期
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
