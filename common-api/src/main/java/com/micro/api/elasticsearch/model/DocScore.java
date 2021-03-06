package com.micro.api.elasticsearch.model;

import lombok.Data;

import java.util.List;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.elasticsearch.model
 * Author:   hhc
 * Date:     2019/3/20
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class DocScore extends Doc {

    private Float score;

    private String hTitle;

    private String hContent;

    private String hDescription;

    private List<String> hComments;

}
