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
 * 〈微信平台交互相关Error〉
 *
 * @author Jolin
 * @create 2018/4/17
 * @since 1.0.0
 */
public enum WeixinResponseCode implements ResponseCode {

    WEIXIN_RETURN_SIGNATURE_ERROR(-300001, "微信公众号平台签名认证错误"),
    WEIXIN_RETURN_CHANNEL_NOT_FOUND(-300002, "微信公众号平台未找到渠道配置"),
    WEIXIN_RETURN_METHOD_NOT_FOUND(-300003, "微信公众号平台上行消息操作无匹配操作"),
    WEIXIN_RETURN_NAV_MENU_NOT_FOUND(-300004, "未查询到导航菜单"),
    WEIXIN_RETURN_GET_ACCESSTOKEN_FAILED(-300005, "获取微信公众号平台access_token失败"),
    WEIXIN_RETURN_CONNECTE_FAILD(-300006, "与微信公众号平台通讯失败"),
    WEIXIN_RETURN_FILE_SAVE_FAILD(-300007, "文件存储失败");

    private String msg;
    private int code;

    private WeixinResponseCode(int code, String msg) {
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
