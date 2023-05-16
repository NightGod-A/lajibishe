package com.ujn.studyweb;

import com.ujn.studyweb.pojo.ClassStudent;
import com.ujn.studyweb.pojo.Classes;
import com.ujn.studyweb.pojo.User;
import com.ujn.studyweb.service.IClassStudentService;
import com.ujn.studyweb.service.IUserService;
import org.apache.poi.xslf.usermodel.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.poi.sl.usermodel.Slide;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class StudyWebApplicationTests {


    // 数据源组件
    @Autowired
    DataSource dataSource;

    // 用于访问数据库的组件
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private IUserService userService;

    @Autowired
    private IClassStudentService classStudentService;
    @Test
    void contextLoads() {
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(1);
    }

    @Test
    void zero(){
        List<Classes> classes = new ArrayList<>();
        System.out.println(classes.get(0).toString());
    }


    @Test
    void data() throws IOException {
        FileInputStream in = new FileInputStream("F:\\QQFile\\854799920\\FileRecv\\006.pptx");
        XMLSlideShow xmlSlideShow = new XMLSlideShow(in);
        in.close();
        // 获取大小
        Dimension pgsize = xmlSlideShow.getPageSize();
        // 获取幻灯片
        List<XSLFSlide> slides = xmlSlideShow.getSlides();
        List<File> imageList = new ArrayList<>();
        for (int i = 0; i < slides.size(); i++) {
            // 解决乱码问题
            List<XSLFShape> shapes = slides.get(i).getShapes();
            for (XSLFShape shape : shapes) {
                if (shape instanceof XSLFTextShape) {
                    XSLFTextShape sh = (XSLFTextShape) shape;
                    List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
                    for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
                        List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
                        for (XSLFTextRun xslfTextRun : textRuns) {
                            xslfTextRun.setFontFamily("宋体");
                        }
                    }
                }
            }
            //根据幻灯片大小生成图片
            BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics = img.createGraphics();
            graphics.setPaint(Color.white);
            graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
            // 将PPT内容绘制到img上
            slides.get(i).draw(graphics);
            //图片将要存放的路径
            String absolutePath = "F:\\QQFile\\854799920\\FileRecv\\" + File.separator + (i + 1) + ".jpg";
            File jpegFile = new File(absolutePath);
            if (!jpegFile.exists()) {
                // 判断如果图片存在则不再重复创建，建议将图片存放到一个特定目录，后面会统一删除
                FileOutputStream fileOutputStream = new FileOutputStream(jpegFile);
                ImageIO.write(img, "jpg", fileOutputStream);
            }
            // 图片路径存放
            imageList.add(jpegFile);
        }
    }

}
