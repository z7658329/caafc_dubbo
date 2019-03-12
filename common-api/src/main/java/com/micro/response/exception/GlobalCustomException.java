package com.micro.response.exception;

import com.micro.response.exception.global.ExceptionInterface;

/**
 * 自定义统一错误异常
 */
public class GlobalCustomException extends Exception {

	private static final long serialVersionUID = 1L;
    private ExceptionInterface exceptionInterface;

    public GlobalCustomException(ExceptionInterface exceptionInterface) {
        this.exceptionInterface = exceptionInterface;
    }

    public ExceptionInterface getExceptionInterface() {
        return exceptionInterface;
    }

    public void setExceptionInterface(ExceptionInterface exceptionInterface) {
        this.exceptionInterface = exceptionInterface;
    }
}

