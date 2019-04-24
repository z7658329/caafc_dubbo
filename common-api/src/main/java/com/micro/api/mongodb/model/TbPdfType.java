package com.micro.api.mongodb.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.api.mysql.model
 * Author:   hhc
 * Date:     2019/3/18
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
@ApiModel("业务类型")
public class TbPdfType implements Serializable {

    private String id;

    @ApiModelProperty(value = "名字", example = "1")
    private String name;

    @ApiModelProperty(value = "描述", example = "1")
    private String description;

    @ApiModelProperty(value = "父id", example = "1")
    private String pid;

    @ApiModelProperty(value = "层级", example = "0",dataType = "int")
    private Integer level;

    @ApiModelProperty(value = "序号", example = "0",dataType = "int")
    private Integer orderNo;

    @ApiModelProperty(value = "状态", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "1 删除", example = "1",dataType = "int")
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;
}
