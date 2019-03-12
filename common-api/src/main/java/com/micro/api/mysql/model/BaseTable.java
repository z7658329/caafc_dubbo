package com.micro.api.mysql.model;

import io.swagger.annotations.ApiModelProperty;

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

public class BaseTable<T> implements Serializable {

    @ApiModelProperty(value="起时间",example="2018-07-19 09:59:07")
    private String start;


    @ApiModelProperty(value="止时间",example="2018-07-19 09:59:07")
    private String end;


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

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Integer getLazyLoad() {
        return lazyLoad;
    }

    public void setLazyLoad(Integer lazyLoad) {
        this.lazyLoad = lazyLoad;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getAscDesc() {
        return ascDesc;
    }

    public void setAscDesc(String ascDesc) {
        this.ascDesc = ascDesc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }



}
