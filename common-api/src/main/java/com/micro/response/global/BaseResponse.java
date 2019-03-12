package com.micro.response.global;


public class BaseResponse {
    /**
     * 返回码
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 数据内容
     */
    private Object data;

    /**
     * 耗时
     */
    private String time;


    public BaseResponse() {
        this.setCode(ResponseCodeImpl.GLOBAL_RETURN_FAILED.getCode());
        this.setMsg(ResponseCodeImpl.GLOBAL_RETURN_FAILED.getMsg());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == ResponseCodeImpl.GLOBAL_RETURN_SUCCESS.getCode();
    }

    public void setSuccess() {
        setResponseCode(ResponseCodeImpl.GLOBAL_RETURN_SUCCESS);
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.setCode(responseCode.getCode());
        this.setMsg(responseCode.getMsg());
    }

    public static BaseResponse create(Object data){
        BaseResponse baseResponse =new BaseResponse();
        baseResponse.setSuccess();
        baseResponse.setData(data);
        return baseResponse;
    }

    public static BaseResponse createError(Object data){
        BaseResponse baseResponse =new BaseResponse();
         baseResponse.setData(data);
        return baseResponse;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", time='" + time + '\'' +
                '}';
    }
}

