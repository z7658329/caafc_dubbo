package com.micro.response.exception.global;

/**
 * 全局的异常
 *
 * Created by bysocket on 14/03/2017.
 */
public enum GlobalCustomEnum implements ExceptionInterface {

    SUCCESS(1111, "unknown"),
    NOT_FOUND(2222, "netty not found");


    private Integer status;

    private String message;

    GlobalCustomEnum(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public Integer getStatus() {
        return status;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
