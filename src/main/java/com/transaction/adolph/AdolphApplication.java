package com.transaction.adolph;

import com.transaction.adolph.utils.ConfigReader;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/28 17:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */

@SpringBootApplication
@MapperScan(basePackages = "com.transaction.adolph.mapper")
public class AdolphApplication implements EnvironmentAware{

	public static void main(String[] args) {
		SpringApplication.run(AdolphApplication.class, args);
	}

	@Override
	public void setEnvironment(Environment environment) {
		ConfigReader.env = environment;
	}
}

