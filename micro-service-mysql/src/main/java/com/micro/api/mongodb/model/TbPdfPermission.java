package com.micro.api.mongodb.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;
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

@Entity
@DynamicInsert
@DynamicUpdate
@Data
@ApiModel("权限表")
public class TbPdfPermission implements Serializable {

    @Id
    private String id;

    @ApiModelProperty(value = "pdfId", example = "1")
    private String pdfId;

    @ApiModelProperty(value = "部门Id", example = "1")
    private String depId;

    @ApiModelProperty(value = "岗位Id", example = "1")
    private String postId;

    @ApiModelProperty(value = "人员Id", example = "1")
    private String personId;

    @ApiModelProperty(value = "权限类别：1:公开,2:部门，3:岗位，4:个人", example = "1")
    private String permissionType;

    @ApiModelProperty(value = "状态", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "1 删除", example = "1",dataType = "int")
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;
}
