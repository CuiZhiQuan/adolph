package com.transaction.adolph.domain.enums;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 17:43
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public class CompensateEnum {

    public enum Status {

        INITIAL(0,"初始未补偿"),
        SUCCESS(1,"事务成功不用补偿"),
        COMPENSATE(2,"补偿成功");

        Integer index;
        String name;

        Status(Integer index, String name) {
            this.index = index;
            this.name = name;
        }

        public Integer getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }
    }
}
