package com.micro.api.elasticsearch.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Document(indexName = "doc",type = "institution")
@Mapping(mappingPath="doc_mapping.json")
public class Doc implements Serializable {


    @Id
    private String id;

    @ApiModelProperty(value = "父id 追溯历史版本", example = "1")
    private String pid;

    @ApiModelProperty(value = "名字", example = "1")
    private String title;

    @ApiModelProperty(value = "描述", example = "1")
    private String description;

    @ApiModelProperty(value = "文号", example = "1")
    private String fileNo;

    @ApiModelProperty(value = "业务类型", example = "1")
    private String pdfTypeId;

    @ApiModelProperty(value = "拟文部门 id ", example = "1")
    private String depId;

    @ApiModelProperty(value = "文件路径 ", example = "1")
    private String filePath;

    @ApiModelProperty(value = "文件内容 ", example = "1")
    private String content;

    @ApiModelProperty(value = "部门权限 ", example = "1")
    private ArrayList<String> depPermissions;

    @ApiModelProperty(value = "岗位权限 ", example = "1")
    private ArrayList<String>postPermissions;

    @ApiModelProperty(value = "人员权限 ", example = "1")
    private ArrayList<String>personPermissions;

    @ApiModelProperty(value = "批注 ", example = "1")
    private ArrayList<String>comments;

    @ApiModelProperty(value = "效力级别", example = "0",dataType = "int")
    private Integer level;

    @ApiModelProperty(value = "生效时间", example = "0",dataType = "int")
    private Date effectTime;

    @ApiModelProperty(value = "状态 0 未生效 1生效", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "1 删除", example = "1",dataType = "int")
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;


}
