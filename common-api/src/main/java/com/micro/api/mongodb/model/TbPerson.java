package com.micro.api.mongodb.model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;


/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.model.database
 * Author:   hhc
 * Date:     2018/8/16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
@ApiModel(value = "人员表")
public class TbPerson implements Serializable {


    private String id;

    @ApiModelProperty(value = "用户昵称", example = "abcd12345")
    private String name;

    @ApiModelProperty(value = "用户登录id", example = "abcd12345")
    private String userId;

    @ApiModelProperty(value = "密码", example = "abcd12345")
    private String password;

    @ApiModelProperty(value = "单位id", example = "abcd12345")
    private String orgAccountId;

    @ApiModelProperty(value = "单位名称", example = "abcd12345")
    private String orgAccountName;

    @ApiModelProperty(value = "职务id", example = "abcd12345")
    private String orgLevelId;

    @ApiModelProperty(value = "职务名称", example = "abcd12345")
    private String orgLevelName;

    @ApiModelProperty(value = "岗位id", example = "abcd12345")
    private String orgPostId;

    @ApiModelProperty(value = "岗位名字", example = "abcd12345")
    private String orgPostName;

    @ApiModelProperty(value = "部门id", example = "abcd12345")
    private String orgDepartmentId;

    @ApiModelProperty(value = "部门名称", example = "abcd12345")
    private String orgDepartmentName;

    @ApiModelProperty(value = "状态", example = "0",dataType = "int")
    private Integer status;

    @ApiModelProperty(value = "1 删除", example = "1",dataType = "int")
    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

}

