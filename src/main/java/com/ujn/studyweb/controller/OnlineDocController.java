//package com.ujn.studyweb.controller;
//
//import org.apache.commons.io.IOUtils;
//import org.jodconverter.DocumentConverter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Controller
//public class OnlineDocController {
//    /**
//     * 注入DocumentConverter（jodconverter内置）
//     */
//    @Autowired
//    private DocumentConverter converter;
//    @RequestMapping("/toPdfFile")
//    public void toPdfFile(HttpServletResponse response) {
//
//        File file = new File("F:\\QQFile\\854799920\\FileRecv\\006.pptx");
//        ServletOutputStream outputStream = null;
//        InputStream in = null;
//        //转换之后文件生成的地址
//        File newFile = new File("F:\\ujnProject\\ppt\\");
//        if (!newFile.exists()) newFile.mkdirs();
//        try {
//            //文件转化
//            converter.convert(file).to(new File("F:\\ujnProject\\ppt\\006.pptx")).execute();
//            outputStream = response.getOutputStream();
//            in = new FileInputStream(new File("F:\\ujnProject\\ppt\\006.pptx"));
//            IOUtils.copy(in, outputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (in != null) in.close();
//                if (outputStream != null) outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
