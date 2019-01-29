package com.transaction.adolph.config;

import com.transaction.adolph.utils.ConfigReader;
import com.transaction.adolph.utils.IDGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 16:03
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Configuration
public class SnowFlakeConfig {

    @Bean
    public IDGenerator idGenerator(){
        String workerIdStr = ConfigReader.read(ConfigReader.workerId);
        Long workerId = Long.valueOf(workerIdStr);
        return  new IDGenerator(workerId,0);
    }
}
