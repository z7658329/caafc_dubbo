package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModelProperty;

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
public class PageTable<T> implements Serializable {

    @ApiModelProperty(value="总页数")
    private Integer totalPages;

    @ApiModelProperty(value="总数")
    private Long totalElements;

    List<T> models;

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getModels() {
        return models;
    }

    public void setModels(List<T> models) {
        this.models = models;
    }
}
