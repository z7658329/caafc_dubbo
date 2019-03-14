package com.micro.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.util
 * Author:   hhc
 * Date:     2019/3/14
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class BaseTableUtil {

    private static String formatTime="yyyy-MM-dd HH:mm:ss";

    public static String getFormatTime() {
        return formatTime;
    }

    public static void setFormatTime(String formatTime) {
        BaseTableUtil.formatTime = formatTime;
    }

    public static Date toDate(String str){
        Date date=null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        try {
            date =sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getDateStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        return sdf.format(date);
    }

   public  static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
   }

    public static String[] getNoNullProperties(Object target) {
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> noEmptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            if (value != null) noEmptyName.add(p.getName());
        }
        String[] result = new String[noEmptyName.size()];
        return noEmptyName.toArray(result);
    }

    public static Map<String,Object> getNoNullMap(Object target){
        if(target == null){
            return null;
        }
        List<String> list=new ArrayList<>();
        list.add("updateTime");
        list.add("class");

        Map<String, Object> map = new HashMap<String, Object>();
        BeanWrapper srcBean = new BeanWrapperImpl(target);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        for (PropertyDescriptor p : pds) {
            Object value = srcBean.getPropertyValue(p.getName());
            String prop=p.getName();
            if (value != null&&!list.contains(prop)) {
                map.put(prop,value);
            }
        }
        return map;
    }

}
