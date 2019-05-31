package com.micro.netty.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.micro.api.elasticsearch.DocService;
import com.micro.api.elasticsearch.InstitutionService;
import com.micro.api.elasticsearch.model.Doc;
import com.micro.api.elasticsearch.model.Institution;
import com.micro.api.mysql.TbPdfModelService;
import com.micro.api.mongodb.model.TbPdfModel;
import com.micro.api.web.PdfService;
import com.micro.api.web.model.request.PdfModel;
import com.micro.response.global.BaseResponse;
import com.micro.util.LG;
import com.micro.util.PdfOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.netty.impl
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

    @Reference(check = false)
    private DocService docService;

    @Reference(check = false)
    private TbPdfModelService tbPdfModelService;

    @Override
    public BaseResponse uploadPdf(PdfModel pdfModel){
        TbPdfModel tbPdfModel=new TbPdfModel();
        String uuid=UUID.randomUUID().toString().replace("-","");
        pdfModel.setId(uuid);
        BeanUtils.copyProperties(pdfModel,tbPdfModel);
        tbPdfModel=tbPdfModelService.insert(tbPdfModel);
        PdfOperator pdfOperator =new PdfOperator(pdfModel.getFilePath());
        StringBuffer stringBuffer=new StringBuffer();
        Doc doc = null;
        try {
            for (int i = 1; i <= pdfOperator.getPageNum(); i++) {
                String pageContent = pdfOperator.getPageContent(i);
                Institution institution = new Institution();
                institution.setPageNum(i);
                institution.setPageContent(pageContent);
                institution.setPdfId(tbPdfModel.getId());
                institutionService.insert(institution);
                stringBuffer.append(pageContent + "\r\n");
            }
            doc = new Doc();
            BeanUtils.copyProperties(pdfModel, doc);
            doc.setContent(stringBuffer.toString());
            doc = docService.insert(doc);
        }catch (Exception e){
            log.info(LG.E(),e);
        }
        if(doc==null){
            tbPdfModelService.deleteById(uuid);
            return BaseResponse.createError("elasticSearch 服务异常");
        }
        return BaseResponse.create(tbPdfModel);
    }

    @Override
    public BaseResponse updatePdf(PdfModel pdfModel) {
        TbPdfModel tbPdfModel=new TbPdfModel();
        BeanUtils.copyProperties(pdfModel,tbPdfModel);
        TbPdfModel ret= tbPdfModelService.updateById(tbPdfModel);
        if(ret==null){
            return BaseResponse.createError("数据库操作错误");
        }
        Doc doc=new Doc();
        BeanUtils.copyProperties(doc,tbPdfModel);
        Doc docret=docService.updateById(doc);
        if(docret==null){

        }
        return null;
    }
}
