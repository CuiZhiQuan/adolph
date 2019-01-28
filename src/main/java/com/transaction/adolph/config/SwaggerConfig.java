package com.transaction.adolph.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/28 17:34
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.transaction.adolph"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("分布式事务管理器")
                .description("支持基于补偿和最终一致性的分布式事务")
                .contact(contact())
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    private Contact contact(){
        return new Contact("Cadolph","","cuizhiquan123@163.com");
    }
}
