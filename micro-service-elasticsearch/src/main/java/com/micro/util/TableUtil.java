package com.micro.util;


import com.micro.api.mongodb.model.PageTable;
import org.springframework.data.domain.Page;

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
public class TableUtil extends BaseTableUtil {


    public static <T> PageTable copyTableList(Page<T> page){
        PageTable<T>pageTable=new PageTable<T>();
        pageTable.setTotalElements(page.getTotalElements());
        pageTable.setTotalPages(page.getTotalPages());
        pageTable.setModels(page.getContent());
        return pageTable ;
    }

}
