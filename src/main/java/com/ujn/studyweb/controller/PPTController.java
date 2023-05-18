package com.ujn.studyweb.controller;

import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.IClassPPTService;
import com.ujn.studyweb.service.IClassesService;
import com.ujn.studyweb.service.ICourseService;
import com.ujn.studyweb.service.IPPTService;
import com.ujn.studyweb.utils.WebUtils;
import org.apache.poi.xslf.usermodel.*;
import org.jodconverter.core.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class PPTController {
    @Autowired
    private IPPTService pptService;
    @Autowired
    private IClassPPTService classPPTService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private ICourseService courseService;


    @RequestMapping("/pptManage")
    public String pptManage(@RequestParam("course") String course, Model model){
        List<PPT> ppts = pptService.queryPPTByCourse(Integer.parseInt(course));
        Course course1 = courseService.queryCourseById(Integer.parseInt(course));
        int teacher = course1.getTeacherId();
        model.addAttribute("course",course);
        model.addAttribute("teacher",teacher);
        model.addAttribute("ppts",ppts);
        return "PPTManage";
    }

    @RequestMapping("/addPPT")
    public ResponseEntity<String> addPPT(@RequestParam("file") MultipartFile file,  @RequestParam("name") String pptName, @RequestParam("courseId") String courseId){
        try {
            // 保存文件到磁盘
            String pptFileNameOrigin = file.getOriginalFilename();
            int index = pptFileNameOrigin.indexOf(".");
            String uuid = UUID.randomUUID().toString();
            String pptFileName = uuid+pptFileNameOrigin.substring(index);
            Path path = Paths.get("F:\\ujnProject\\ppt\\" + pptFileName);
            Files.write(path, file.getBytes());
            PPT ppt = new PPT();
            ppt.setPptName(pptName);
            ppt.setPpt(pptFileName);
            ppt.setCreatTime(new Date());
            ppt.setCourseId(Integer.parseInt(courseId));
            pptService.addPPT(ppt);
            int pptId = ppt.getId();
            int size = pptService.generatePPT("F:\\ujnProject\\ppt\\" + pptFileName,"F:\\ujnProject\\ppt\\" + uuid + ".pdf",pptId);
            PPT ppt1 = pptService.queryPPTById(pptId);
            ppt1.setPageNum(size);
            pptService.updatePPT(ppt1);
            return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (OfficeException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping ("/pptDistribute")
    public String pptDistribute(@RequestParam("pptId") String pptId,Model model){
        PPT ppt = pptService.queryPPTById(Integer.parseInt(pptId));
        List<ClassPPT> classPPTS = classPPTService.queryClassPPTListByPPT(Integer.parseInt(pptId));
        List<Classes> classList = new ArrayList<>();
        for (int i = 0; i <classPPTS.size(); i++){
            classList.add(classesService.queryClassesById(classPPTS.get(i).getClassId()));
        }
        List<Classes> classesListAll = classesService.queryClassesListByCourse(ppt.getCourseId());
        classesListAll.removeAll(classList);
        model.addAttribute("classesListAll",classesListAll);
        model.addAttribute("classList",classList);
        model.addAttribute("pptId",pptId);
        return "PPTClass";
    }

    @RequestMapping("/addPPTClass")
    public String addPPTClass(@RequestParam("classId") String classId,@RequestParam("pptId") String pptId,Model model){
        ClassPPT classPPT = new ClassPPT();
        classPPT.setClassId(Integer.parseInt(classId));
        classPPT.setPptId(Integer.parseInt(pptId));
        classPPTService.addClassPPT(classPPT);
        model.addAttribute("pptId",pptId);
        return "redirect:/pptDistribute?pptId="+pptId;
    }

    @RequestMapping("/deletePPTClass")
    public ResponseEntity<String> deletePPTClass(@RequestParam("classId") String classId,@RequestParam("pptId") String pptId){
        ClassPPT classPPT = classPPTService.queryClassPPTByClassAndPPT(Integer.parseInt(classId),Integer.parseInt(pptId));
        classPPTService.deleteClassPPT(classPPT.getId());
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/pptView")
    public String pptView(@RequestParam("pptId") String pptId,Model model){
        String realId =  WebUtils.decodeUrl(pptId);
        PPT ppt = pptService.queryPPTById(Integer.parseInt(realId));
        List<String> a = new ArrayList<>();
        for (int i = 0; i < ppt.getPageNum(); i++){
            a.add("http://localhost:8080/showPPT?fileName="+realId+"Slide"+i+".jpg");
        }

        model.addAttribute("imgurls",a);
        model.addAttribute("currentUrl", a.get(0));
        return "ppt";
//        return "PPTView";
    }

    @RequestMapping("/jumpToPPTManage")
    public String jumpToPPTManage(@RequestParam("pptId") String pptId){
        PPT ppt = pptService.queryPPTById(Integer.parseInt(pptId));
        return "redirect:/pptManage?course="+ppt.getCourseId();
    }

    @RequestMapping("/deletePPT")
    public ResponseEntity<String> deletePPT(@RequestParam("pptId") String pptId){
       List<ClassPPT> classPPTs = classPPTService.queryClassPPTListByPPT(Integer.parseInt(pptId));
       for (int i = 0; i < classPPTs.size(); i++){
           classPPTService.deleteClassPPT(classPPTs.get(i).getPptId());
       }
       PPT ppt = pptService.queryPPTById(Integer.parseInt(pptId));
       File file = new File("F:\\ujnProject\\ppt\\" + ppt.getPpt());
       if (file.exists()){
           file.delete();
       }
       for (int i = 0; i < ppt.getPageNum(); i++){
           File jpgFile = new File("F:\\ujnProject\\pptImage\\" + pptId + "Slide" + (i + 1) + ".jpg");
           if (jpgFile.exists()){
               jpgFile.delete();
           }
       }
       pptService.deletePPT(Integer.parseInt(pptId));
       return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/revisePPT")
    ResponseEntity<String> revisePPT(@RequestParam("pptId") String pptId,@RequestParam("pptName") String pptName){
        PPT ppt = pptService.queryPPTById(Integer.parseInt(pptId));
        ppt.setPptName(pptName);
        pptService.updatePPT(ppt);
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }

    @RequestMapping("/ppt")
    public String ppt(Model model,String url){
        List<String> a = new ArrayList<>();
        a.add("http://localhost:8080/showPPT?fileName=0.jpg");
        a.add("http://localhost:8080/showPPT?fileName=1.jpg");
        a.add("http://localhost:8080/showPPT?fileName=2.jpg");
        a.add("http://localhost:8080/showPPT?fileName=3.jpg");
        a.add("http://localhost:8080/showPPT?fileName=4.jpg");
        model.addAttribute("imgurls",a);
        model.addAttribute("currentUrl", a.get(0));
        return "ppt";
    }





}
