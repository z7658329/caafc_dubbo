package com.micro.api.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "file")
public class FileModel {

    @Id
    private String id;

    private String name;

    private String info;

    private String contentType;

    private byte[] bytes;

    private Date createTime;

    private Date updateTime;

}