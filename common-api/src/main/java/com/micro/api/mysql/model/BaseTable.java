package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.model
 * Author:   utils
 * Date:     2018/6/27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Data
public class BaseTable<T> implements Serializable {

    @ApiModelProperty(value="起时间",example="时间戳")
    private Long start;


    @ApiModelProperty(value="止时间",example="时间戳")
    private Long end;


    @ApiModelProperty(value="排序字段",example="createTime")
    private String order;


    @ApiModelProperty(value="升降序",example="0",notes = "0或不传 降序，1 升序")
    private String ascDesc;


    @ApiModelProperty(value="页数",example="1")
    private Integer pageNum;

    @ApiModelProperty(value="每页数量",example="8")
    private Integer pageSize;

    private Integer lazyLoad;

    private T model;

}
