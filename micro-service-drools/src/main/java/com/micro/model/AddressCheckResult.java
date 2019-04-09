package com.micro.model;

import lombok.Data;


@Data
public class AddressCheckResult {

    private boolean postCodeResult = false; // true:通过校验；false：未通过校验
}
