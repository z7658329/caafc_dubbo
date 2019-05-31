package com.micro.util;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.en
 * Author:   hhc
 * Date:     2019/4/10
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class FucOnOff {

    private String onOff;
    private int step=0;
    private int len;
    private char on='1';

    FucOnOff(String onOff){
        this.onOff=onOff;
        this.len=onOff.length()-1;
    }

    public boolean next(){
        step++;
        if(step>len){
            return true;
        }else {
            return onOff.charAt(step)==on;
        }
    }
    public boolean setOnOff(String onOff){
        this.onOff=onOff;
        this.len=onOff.length()-1;
        return true;
    }
}
