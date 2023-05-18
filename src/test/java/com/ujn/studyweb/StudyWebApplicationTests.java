package com.ujn.studyweb;

import com.itextpdf.text.pdf.PdfReader;
import com.ujn.studyweb.pojo.ClassStudent;

import com.ujn.studyweb.service.IClassStudentService;
import com.ujn.studyweb.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.apache.poi.xslf.usermodel.*;

import org.jodconverter.core.office.InstalledOfficeManagerHolder;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.util.OSUtils;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.awt.*;
import java.io.*;

import java.util.*;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


import javax.imageio.ImageIO;


import com.ujn.studyweb.utils.LocalOfficeUtils;

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

    @Value("${office.plugin.server.ports:2001,2002}")
    private String serverPorts;
    @Value("${office.plugin.task.timeout:5m}")
    private String timeOut;
    private LocalOfficeManager officeManager;
    @Test
    void contextLoads() {
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(1);
    }


    public void startOfficeManager() throws OfficeException {
        File officeHome = LocalOfficeUtils.getDefaultOfficeHome();
        if (officeHome == null) {
            throw new RuntimeException("找不到office组件，请确认'office.home'配置是否有误");
        }
        boolean killOffice = killProcess();
        if (killOffice) {
            System.out.println("检测到有正在运行的office进程，已自动结束该进程");
        }
        try {
            String[] portsString = serverPorts.split(",");
            int[] ports = Arrays.stream(portsString).mapToInt(Integer::parseInt).toArray();
            long timeout = DurationStyle.detectAndParse(timeOut).toMillis();
            officeManager = LocalOfficeManager.builder()
                    .officeHome(officeHome)
                    .portNumbers(ports)
                    .processTimeout(timeout)
                    .build();
            officeManager.start();
            InstalledOfficeManagerHolder.setInstance(officeManager);
        } catch (Exception e) {
            System.out.println("启动office组件失败，请检查office组件是否可用");
            throw e;
        }
    }

    private boolean killProcess() {
        boolean flag = false;
        try {
            if (OSUtils.IS_OS_WINDOWS) {
                Process p = Runtime.getRuntime().exec("cmd /c tasklist ");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream os = p.getInputStream();
                byte[] b = new byte[256];
                while (os.read(b) > 0) {
                    baos.write(b);
                }
                String s = baos.toString();
                if (s.contains("soffice.bin")) {
                    Runtime.getRuntime().exec("taskkill /im " + "soffice.bin" + " /f");
                    flag = true;
                }
            } else if (OSUtils.IS_OS_MAC || OSUtils.IS_OS_MAC_OSX) {
                Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "ps -ef | grep " + "soffice.bin"});
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream os = p.getInputStream();
                byte[] b = new byte[256];
                while (os.read(b) > 0) {
                    baos.write(b);
                }
                String s = baos.toString();
                if (StringUtils.ordinalIndexOf(s, "soffice.bin", 3) > 0) {
                    String[] cmd = {"sh", "-c", "kill -15 `ps -ef|grep " + "soffice.bin" + "|awk 'NR==1{print $2}'`"};
                    Runtime.getRuntime().exec(cmd);
                    flag = true;
                }
            } else {
                Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "ps -ef | grep " + "soffice.bin" + " |grep -v grep | wc -l"});
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                InputStream os = p.getInputStream();
                byte[] b = new byte[256];
                while (os.read(b) > 0) {
                    baos.write(b);
                }
                String s = baos.toString();
                if (!s.startsWith("0")) {
                    String[] cmd = {"sh", "-c", "ps -ef | grep soffice.bin | grep -v grep | awk '{print \"kill -9 \"$2}' | sh"};
                    Runtime.getRuntime().exec(cmd);
                    flag = true;
                }
            }
        } catch (IOException e) {
            System.out.println("检测office进程异常");
        }
        return flag;
    }

    @Test
    void zero() throws OfficeException, IOException {
        startOfficeManager();
        File inputFile = new File("F:\\ujnProject\\ppt\\Chap04.pptx");
        File pdfFile = new File("F:\\ujnProject\\ppt\\Chap04.pdf");
        LocalConverter.Builder builder;
        builder = LocalConverter.builder();
        builder.build().convert(inputFile).to(pdfFile).execute();

        String filePassword = null;
        PDDocument doc = null;
        PdfReader pdfReader = null;
        if (!pdfFile.exists()) {
            return;
        }
        doc = PDDocument.load(pdfFile,filePassword);
        int pageCount = doc.getNumberOfPages();
        PDFRenderer pdfRenderer = new PDFRenderer(doc);

        String imageFilePath;
        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            imageFilePath = "F:\\ujnProject\\pptImage" + File.separator + pageIndex + ".jpg";
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
            ImageIOUtil.writeImage(image, imageFilePath, 105);
        }
        if (pdfReader != null) {   //关闭
            pdfReader.close();
        }
        if (doc != null) {   //关闭
            doc.close();
        }
        killProcess();
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

    @Test
    public void userDir(){
        System.out.println(System.getProperty("user.dir"));
    }

}
