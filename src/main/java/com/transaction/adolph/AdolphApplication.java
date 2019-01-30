package com.transaction.adolph;

import com.transaction.adolph.utils.ConfigReader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/28 17:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "com.transaction.adolph.mapper")
public class AdolphApplication implements EnvironmentAware{

	public static void main(String[] args) {
		SpringApplication.run(AdolphApplication.class, args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Override
	public void setEnvironment(Environment environment) {
		ConfigReader.env = environment;
	}
}

