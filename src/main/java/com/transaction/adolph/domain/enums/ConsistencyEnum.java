package com.transaction.adolph.domain.enums;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/31 14:38
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public class ConsistencyEnum {

    public enum Status {

        NEW(0,"新注册的事务"),
        HAS_PARTICIPANT(1,"已经添加了事务参与者"),
        TO_BE_SENT(2,"，消息待发送"),
        SENT(3,"已经发送过，但是不保证发送成功"),
        SUCCESS(4,"发送成功");

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
