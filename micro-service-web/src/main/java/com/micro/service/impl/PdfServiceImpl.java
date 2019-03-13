package com.micro.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.InstitutionService;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.web.PdfService;
import com.micro.api.web.model.request.PdfModel;
import com.micro.response.global.BaseResponse;
import com.micro.util.PdfOperator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.service.impl
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

@Service
@Slf4j
public class PdfServiceImpl implements PdfService {

    @Reference(check = false)
    private InstitutionService institutionService;

    @Override
    public BaseResponse uploadPdf(PdfModel pdfModel){
        PdfOperator pdfOperator =new  PdfOperator(pdfModel.getPath());
        for(int i=1;i<=pdfOperator.getPageNum();i++){
            String pageContent = pdfOperator.getPageContent(i);
            Institution institution=new Institution();
            institution.setName(pdfModel.getName());
            institution.setPageNum(i);
            institution.setContent(pageContent);
            institutionService.save(institution);
        }
        return BaseResponse.create(null);
    }
}
