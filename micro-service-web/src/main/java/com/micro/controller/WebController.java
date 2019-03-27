package com.micro.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.micro.api.web.PdfService;
import com.micro.api.web.model.request.PdfModel;
import com.micro.response.global.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.controller
 * Author:   hhc
 * Date:     2019/3/7
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@RestController
public class WebController {

    @Reference(check = false)
    private PdfService pdfService;

    @PostMapping("/pdf/upload")
    public BaseResponse uploadPdf(@RequestBody PdfModel pdfModel){
       return pdfService.uploadPdf(pdfModel) ;
    }
}
