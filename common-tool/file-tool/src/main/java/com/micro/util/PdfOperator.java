package com.micro.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Copyright (C),长安汽车金融有限公司
 * FileName:  com.micro.util
 * Author:   hhc
 * Date:     2019/3/6
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
@Slf4j
public class PdfOperator {

    private PDDocument doc;

    public PdfOperator(PDDocument doc) {
        this.doc = doc;
    }

    public PdfOperator(String filePath) {
        try {
            doc = PDDocument.load(new File(filePath));
        }catch (Exception e){
             log.error(LG.E(),e);
        }
    }

    public int getPageNum(){
        return doc.getNumberOfPages();
    }

    public String getPageContent(int num){
        PDFTextStripper textStripper = null;
        try {
            textStripper = new PDFTextStripper();
            textStripper.setSortByPosition(true);
            textStripper.setStartPage(num);
            textStripper.setEndPage(num);
            return textStripper.getText(doc);
        } catch (IOException e) {
            log.error(LG.E(),e);
        }
        return null;
    }

    public void free(){
        try {
            doc.close();
        } catch (IOException e) {
            log.error(LG.E(),e);
        }
    }

    public PDDocument getDoc() {
        return doc;
    }

    public void setDoc(PDDocument doc) {
        this.doc = doc;
    }

    public static void main(String[] args) {
        PdfOperator pdfOperator=new PdfOperator("C:\\Users\\lenovo\\Desktop\\长安金融\\rule\\长安金融发〔2018〕96号关于修订并印发《采购管理办法》的通知.pdf");
        log.info(LG.N(),pdfOperator.getPageNum());
        System.out.println(pdfOperator.getPageContent(1));
        LG.printLine();
        System.out.println(pdfOperator.getPageContent(2));
        LG.printLine();
        System.out.println(pdfOperator.getPageContent(3));
        LG.printLine();
        log.info(LG.N(),pdfOperator.getPageContent(4));
        LG.printLine();
    }
}
