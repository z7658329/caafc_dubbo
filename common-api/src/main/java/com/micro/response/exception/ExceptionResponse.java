package com.micro.response.exception;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Exception返回的json数据
 * @since 2016/10/18
 *
 */
@ApiModel(value = "异常响应", description = "异常响应")
public class ExceptionResponse {
    @ApiModelProperty("时间戳")
    private String timestamp;
    @ApiModelProperty("返回码")
    private Integer status;
    @ApiModelProperty("异常信息")
    private String error;
    @ApiModelProperty("异常原因")
    private Object message;
    @ApiModelProperty("路径")
    private String path;

    /**
     * Construction Method
     * @param timestamp
     * @param status
     * @param error
     * @param message
     * @param path
     */
    public ExceptionResponse(String timestamp, Integer status, String error, Object message, String path){
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ExceptionResponse create(String timestamp, Integer status, String error, Object message, String path){
        return new ExceptionResponse(timestamp,status,error,message,path);
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}