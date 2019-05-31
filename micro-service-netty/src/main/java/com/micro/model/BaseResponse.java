package com.micro.model;

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
     * 时间
     */
    private String time;

    /**
     * 路径
     */

    private String path;


    public BaseResponse() {
        this.code=-1;
        this.msg="failed";
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSuccess() {
        setResponseCode(0,"success");
    }

    public void setResponseCode(int code,String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }

    public static BaseResponse create(Object data){
        BaseResponse baseResponse =new BaseResponse();
        baseResponse.setSuccess();
        baseResponse.setData(data);
        return baseResponse;
    }


    public static BaseResponse createError(int code, String msg,Object o,String path){
        BaseResponse baseResponse =new BaseResponse();
        baseResponse.setCode(code);
        baseResponse.setMsg(msg);
        baseResponse.setData(o);
        baseResponse.setPath(path);
        return baseResponse;
    }

    public static BaseResponse createError(String msg){
         BaseResponse baseResponse =new BaseResponse();
         baseResponse.setMsg(msg);
        return baseResponse;
    }

    public static BaseResponse createError(String msg,Object o){
        BaseResponse baseResponse =new BaseResponse();
        baseResponse.setMsg(msg);
        baseResponse.setData(o);
        return baseResponse;
    }


    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", time='" + time + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}

