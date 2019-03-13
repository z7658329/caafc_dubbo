package com.micro.api.elasticsearch.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.model
 * Author:   hhc
 * Date:     2018/11/22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class Institution implements Serializable {


    private String id;
    private String name;
    private Integer pageNum;
    private String content;
    private String description;


}
