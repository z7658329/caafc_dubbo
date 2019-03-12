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
 * 〈本地业务相关Error〉
 *
 * @author Jolin
 * @create 2018/4/17
 * @since 1.0.0
 */
public enum LocalResponseCode implements ResponseCode {
    LOCAL_RETURN_SMS_NOT_FOUND(-100001, "短信验证码校验失败，数据库中不存在对应验证码，请先发送短信"),
    LOCAL_RETURN_SMS_VALIDATE_FAILED(-100002, "短信验证码未通过校验"),
    LOCAL_RETURN_SMS_EXPIRED(-100003, "短信验证码过期"),
    LOCAL_RETURN_CHANNEL_NOT_FOUND(-100004, "渠道不存在"),
    LOCAL_RETURN_CHANNEL_NOT_PRESENT(-100005, "请提供channelId"),
    LOCAL_RETURN_CUSTCODE_NOT_PRESENT(-100006, "请提供custCode"),
    LOCAL_RETURN_DEALERCODE_NOT_PRESENT(-100007, "请提供dealerCode"),
    LOCAL_RETURN_USER_BINDED(-100008, "客户已绑定"),
    LOCAL_RETURN_USER_NOT_BINDED(-100009, "客户未绑定"),
    LOCAL_RETURN_USER_NOT_FOUND(-100010, "客户不存在"),

    LOCAL_RETURN_USER_NOT_UNIQUE(-100011, "查询到多个客户"),
    LOCAL_RETURN_TYPE_NOT_FOUND(-100012, "type为空,未知的操作"),
    LOCAL_RETURN_BRANDCODE_NOT_FOUND(-100013, "brandCode为空"),
    LOCAL_RETURN_SERIESCODE_NOT_FOUND(-100014, "series为空"),
    LOCAL_RETURN_APPLYTYPECODE_NOT_FOUND(-100015, "applyTypeCode为空"),
    LOCAL_RETURN_CARMODEL_NOT_FOUND(-100016, "carModel为空"),
    LOCAL_RETURN_BIND_FAILED(-100017, "绑定失败"),
    LOCAL_RETURN_PERSONINFO_CHANGE_FAILED(-100018, "信息变更失败"),
    LOCAL_RETURN_DATABASE_FAILED(-100019, "数据库操作失败"),
    LOCAL_RETURN_PARAM_NOT_ALL(-100020, "参数不完整"),
    LOCAL_RETURN_DATA_NOT_FIND(-100021, "未查询到数据"),
    LOCAL_RETURN_DATA_DUPLICATED(-100022, "数据重复"),
    LOCAL_RETURN_FILE_NOT_EXIST(-100023, "文件不存在"),
    LOCAL_RETURN_ACTIVITY_DATABAE_FAILED(-100101, "未查询到获奖结果"),
    LOCAL_RETURN_ACTIVITY_OVERDUE_FAILED(-100102, "活动时间不正确"),
    LOCAL_RETURN_ACTIVITY_STATE_FAILED(-100103, "活动状态未开启"),
    LOCAL_RETURN_ACTIVITY_EACH_FAILED(-100104, "抽奖超过了每人奖次数"),
    LOCAL_RETURN_ACTIVITY_NUM_FAILED(-100105, "抽奖超过了总抽奖次数"),
    LOCAL_RETURN_ACTIVITY_TIME_NUM_FAILED(-100106, "某段时间中奖人数已达上限"),
    LOCAL_RETURN_ACTIVITY_DAY_NUM_FAILED(-100107, "每天中奖人数已达上限"),
    LOCAL_RETURN_ACTIVITY_PRIZE_NUM_FAILED(-100108, "活动单人总抽奖次数已达上限"),
    LOCAL_RETURN_ACTIVITY_PRIZE_NOT_FAILED(-100109, "活动该level奖没有奖品了"),
    LOCAL_RETURN_ACTIVITY_PRIZE_NOT(-100110, "用户抽奖后未获奖"),
    LOCAL_RETURN_USER_NOT_REGISTERED(-100111, "客户未注册"),
    LOCAL_RETURN_USER_INFO_INVALID(-100112, "OCR识别出的身份信息与注册或绑定时输入的信息不一致,请使用本人身份证进行操作"),
    LOCAL_RETURN_CUST_INFO_NOT_FULL(-100113, "用户信息不完整"),
    LOCAL_RETURN_FILE_TOO_LAGER(-100114, "文件太大"),
    LOCAL_RETURN_CONTACT_NOT_FOUND(-100200, "未查询到合同号的回寄信息"),
    LOCAL_RETURN_USER_REGISTERED(-100115, "客户已注册或已绑定"),
    LOCAL_RETURN_OPERATION_DUPLICATED(-100116, "重复的操作")
    ;


    private String msg;
    private int code;

    private LocalResponseCode(int code, String msg) {
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
