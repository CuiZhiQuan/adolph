package com.transaction.adolph.config;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 10:40
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public interface MyMapper<T> extends Mapper<T>,MySqlMapper<T> {
}
