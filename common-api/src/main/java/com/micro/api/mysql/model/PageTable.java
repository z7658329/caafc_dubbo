package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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
public class PageTable<T> implements Serializable {

    @ApiModelProperty(value="总页数")
    private Integer totalPages;

    @ApiModelProperty(value="总数")
    private Long totalElements;

    List<T> models;

}
