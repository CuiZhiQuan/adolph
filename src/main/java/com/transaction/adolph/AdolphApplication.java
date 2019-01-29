package com.transaction.adolph;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/28 17:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */

@SpringBootApplication
@MapperScan(basePackages = "com.transaction.adolph.mapper")
public class AdolphApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdolphApplication.class, args);
	}

}

