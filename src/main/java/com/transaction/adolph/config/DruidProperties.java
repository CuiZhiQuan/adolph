package com.transaction.adolph.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 10:33
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
@Data
public class DruidProperties {

    private Integer initialSize;

    private Integer minIdle;

    private Integer maxActive;

    private Integer maxWait;
}
