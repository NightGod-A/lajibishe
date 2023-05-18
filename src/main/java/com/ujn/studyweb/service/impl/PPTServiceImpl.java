package com.ujn.studyweb.service.impl;

import com.itextpdf.text.pdf.PdfReader;
import com.ujn.studyweb.mapper.PPTMapper;
import com.ujn.studyweb.pojo.PPT;
import com.ujn.studyweb.service.IPPTService;
import com.ujn.studyweb.utils.LocalOfficeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.jodconverter.core.office.InstalledOfficeManagerHolder;
import org.jodconverter.core.office.OfficeException;
import org.jodconverter.core.util.OSUtils;
import org.jodconverter.local.LocalConverter;
import org.jodconverter.local.office.LocalOfficeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class PPTServiceImpl implements IPPTService {

    @Autowired
    private PPTMapper pptMapper;
    @Value("${office.plugin.server.ports:2001,2002}")
    private String serverPorts;
    @Value("${office.plugin.task.timeout:5m}")
    private String timeOut;
    private LocalOfficeManager officeManager;

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

    @Override
    public int generatePPT(String inputPath, String pdfPath, int id) throws OfficeException, IOException {
        startOfficeManager();
        File inputFile = new File(inputPath);
        File pdfFile = new File(pdfPath);
        LocalConverter.Builder builder;
        builder = LocalConverter.builder();
        builder.build().convert(inputFile).to(pdfFile).execute();

        String filePassword = null;
        PDDocument doc = null;
        PdfReader pdfReader = null;
        if (!pdfFile.exists()) {
            return 0;
        }
        doc = PDDocument.load(pdfFile,filePassword);
        int pageCount = doc.getNumberOfPages();
        PDFRenderer pdfRenderer = new PDFRenderer(doc);

        String imageFilePath;
        for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
            imageFilePath = "F:\\ujnProject\\pptImage" + File.separator + id + "Slide" + pageIndex + ".jpg";
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
        return pageCount;
    }

    @Override
    public List<PPT> queryPPTList() {
        return pptMapper.queryPPTList();
    }

    @Override
    public PPT queryPPTById(int id) {
        return pptMapper.queryPPTById(id);
    }

    @Override
    public List<PPT> queryPPTByCourse(int course) {
        return pptMapper.queryPPTByCourse(course);
    }

    @Override
    public int addPPT(PPT ppt) {
        return pptMapper.addPPT(ppt);
    }

    @Override
    public int deletePPT(int id) {
        return pptMapper.deletePPT(id);
    }

    @Override
    public int updatePPT(PPT ppt) {
        return pptMapper.updatePPT(ppt);
    }
}
