package com.transaction.adolph.utils;

import org.springframework.core.env.Environment;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 15:56
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public class ConfigReader {

    public static String workerId = "twitter.snowflake.workerId";

    public static Environment env;

    public static String read(String name) {
        return env.getProperty(name);
    }
}
