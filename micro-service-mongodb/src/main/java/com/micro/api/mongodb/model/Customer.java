package com.micro.api.mongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Document(collection = "customer")
public class Customer {

    @Id
    private String id;


    private String firstName;

    private String lastName;

    //@Indexed(expireAfterSeconds = 100)
    private Date createTime;



}