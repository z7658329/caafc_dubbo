package com.micro.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.config
 * Author:   hhc
 * Date:     2019/3/13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class test {
    public static void main(String[] args) {
        try {
            System.out.println(URLEncoder.encode("长安汽车金融有限公司", "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
