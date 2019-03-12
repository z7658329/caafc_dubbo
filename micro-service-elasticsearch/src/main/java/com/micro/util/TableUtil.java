package com.micro.util;


import com.micro.api.mysql.model.PageTable;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;

import java.beans.PropertyDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.utils
 * Author:   hhc
 * Date:     2018/9/12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
public class TableUtil {

    private static String formatTime="yyyy-MM-dd HH:mm:ss";

    public static String getFormatTime() {
        return formatTime;
    }

    public static void setFormatTime(String formatTime) {
        TableUtil.formatTime = formatTime;
    }

    public static Date ToDate(String str){
        Date date=null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        try {
            date =sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String ToStrDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(formatTime);
        return sdf.format(date);
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
        List<String>list=new ArrayList<>();
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

    public static <T> PageTable copyTableList(Page<T> page){
        PageTable<T>pageTable=new PageTable<T>();
        pageTable.setTotalElements(page.getTotalElements());
        pageTable.setTotalPages(page.getTotalPages());
        pageTable.setModels(page.getContent());
        return pageTable ;
    }
}
