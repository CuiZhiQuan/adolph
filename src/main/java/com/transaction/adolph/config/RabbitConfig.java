package com.transaction.adolph.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/2/1 14:28
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Configuration
public class RabbitConfig {

    @Bean
    RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
