package com.micro.util;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.util
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class LG {

    private static String MARK="=======>{}";
    private static String LINE="--------------------------------------------";
    public static String N(){
        return Thread.currentThread() .getStackTrace()[2].getMethodName()+MARK;
    }

    public static void printLine(){
        System.out.println(LINE);
    }

}
