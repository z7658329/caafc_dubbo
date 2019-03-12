/**
 * Copyright (C),长安汽车金融有限公司
 * FileName: WeixinResponseCode
 * Author:   Jolin
 * Date:     2018/4/17 19:04
 * Description: 微信平台交互相关Error
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.micro.response.weixin;

import com.micro.response.global.ResponseCode;

/**
 * 〈一句话功能简述〉<br>
 * 〈SNA平台交互相关Error〉
 *
 * @author Jolin
 * @create 2018/4/17
 * @since 1.0.0
 */
public enum SNAResponseCode implements ResponseCode {
    SNA_RETURN_CONNECTE_FAILD(-600001, "与SNA平台通讯失败"),
    SNA_RETURN_DATA_NULL(-500002, "SNA平台返回数据为空")
    ;


    private String msg;
    private int code;

    private SNAResponseCode(int code, String msg) {
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
