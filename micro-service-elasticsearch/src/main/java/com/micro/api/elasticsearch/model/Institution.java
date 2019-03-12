package com.micro.api.elasticsearch.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import java.io.Serializable;

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
@Document(indexName = "doc",type = "institution")
public class Institution implements Serializable {

    @Id
    private String id;
    private String name;
    private Integer pageNum;
    private String content;
    private String description;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Institution(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Institution() {
    }
}
