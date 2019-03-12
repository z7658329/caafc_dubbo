/**
 * Copyright (C),长安汽车金融有限公司
 * FileName: GlobalResponseCode
 * Author:   Jolin
 * Date:     2018/4/17 18:48
 * Description: 全局错误码
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.micro.response.global;

/**
 * 〈一句话功能简述〉<br>
 * 〈全局错误码〉
 *
 * @author utils
 * @create 2018/4/17
 * @since 1.0.0
 */
public enum ResponseCodeImpl implements ResponseCode {

    GLOBAL_RETURN_SUCCESS(0, "success"),
    GLOBAL_RETURN_FAILED(-1, "failed");


    private int code;
    private String msg;

    private ResponseCodeImpl(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getCode() {
        return this.code;
    }
}
