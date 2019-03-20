package com.micro.api.elasticsearch.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

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
@Document(indexName = "institution",type = "page")
@Mapping(mappingPath="institution_mapping.json")
public class Institution implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "pdf id ", example = "1")
    private String PdfId;


    @ApiModelProperty(value = "页码 ", example = "1")
    private Integer pageNum;

    @ApiModelProperty(value = "页码内容", example = "1")
    private String pageContent;

}
