package com.micro.api.elasticsearch.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.elasticsearch.model
 * Author:   hhc
 * Date:     2019/3/18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class Institution implements Serializable {

    private String id;

    @ApiModelProperty(value = "pdf id ", example = "1")
    private String PdfId;


    @ApiModelProperty(value = "页码 ", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "页码内容", example = "1")
    private String pageContent;

    @Override
    public String toString() {
        return "Institution{" +
                "id='" + id + '\'' +
                ", PdfId='" + PdfId + '\'' +
                ", pageNum=" + pageNum +
                '}';
    }
}
