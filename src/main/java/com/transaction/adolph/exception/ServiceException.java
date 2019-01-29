package com.transaction.adolph.exception;

/**
 * @author cuizhiquan
 * @Description
 * @date 2019/1/29 10:26
 * @Copyright (c) 2017, DaChen All Rights Reserved.
 */
public class ServiceException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private Integer resultCode;

    public ServiceException(Integer resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }

    public ServiceException(String message) {
        super(message);
    }

    public Integer getResultCode() {
        return resultCode;
    }
}
