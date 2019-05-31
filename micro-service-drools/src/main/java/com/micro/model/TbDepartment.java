package com.micro.model;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql.model
 * Author:   hhc
 * Date:     2019/3/15
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class TbDepartment implements Serializable {


    private String id;

    @ApiModelProperty(value = "名字", example = "1")
    private String name;

    @ApiModelProperty(value = "部门代码", example = "0",dataType = "String")
    private String code;

    @ApiModelProperty(value = "排序号", example = "0",dataType = "int")
    private Integer sortId;

    @ApiModelProperty(value = "上级部门Id", example = "0",dataType = "String")
    private String superior;

    @ApiModelProperty(value = "层级", example = "0",dataType = "int")
    private Integer level;

    @ApiModelProperty(value = "状态", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "true 删除", example = "true",dataType = "Boolean")
    private Boolean isDeleted;

    @ApiModelProperty(value = "true 启用", example = "true",dataType = "Boolean")
    private Boolean enabled;

    @ApiModelProperty(value = "true 合法", example = "true",dataType = "Boolean")
    private Boolean valid;

    @ApiModelProperty(value = "true 人员数据来源oa", example = "true",dataType = "Boolean")
    private Boolean isFromOa;

    private Date createTime;

    private Date updateTime;

}
