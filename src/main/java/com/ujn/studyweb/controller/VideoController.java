package com.ujn.studyweb.controller;

import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class VideoController {


    @Autowired
    private IVideoService videoService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IClassVideoService classVideoService;
    @Autowired
    private IStudentVideoService studentVideoService;
    @Autowired
    private IClassStudentService classStudentService;
    @Autowired
    private ICourseService courseService;

    @RequestMapping("/videoManage")
    public String videoCreat(@RequestParam("teacher") String teacher, @RequestParam("course") String course, Model model){
        List<Video> videos = videoService.queryVideoByCourse(Integer.parseInt(course));
        model.addAttribute("teacher",teacher);
        model.addAttribute("course",course);
        model.addAttribute("videos",videos);
        return "videoManage";
    }

    @RequestMapping("/jumpToVideoManage")
    public String jumpToVideoManage(@RequestParam("videoId") String videoId){
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        Course course = courseService.queryCourseById(video.getCourseId());
        return "redirect:/videoManage?teacher="+course.getTeacherId()+"&course="+course.getId();
    }

    @RequestMapping("/addVideo")
    public ResponseEntity<String> addVideo(@RequestParam("file") MultipartFile file,@RequestParam("video") MultipartFile video, @RequestParam("name") String videoName, @RequestParam("teacherId") String teacherId, @RequestParam("courseId") String courseId, @RequestParam("isEnable") String isEnable){
        try {
            // 保存文件到磁盘
            String pictureFileNameOrigin = file.getOriginalFilename();
            int index = pictureFileNameOrigin.indexOf(".");
            String pictureFileName = UUID.randomUUID().toString()+pictureFileNameOrigin.substring(index);
            Path path = Paths.get("F:\\ujnProject\\image\\" + pictureFileName);
            Files.write(path, file.getBytes());
            String videoFileNameOrigin = video.getOriginalFilename();
            int index1 = videoFileNameOrigin.indexOf(".");
            String videoFileName = UUID.randomUUID().toString()+videoFileNameOrigin.substring(index);
            Path path1 = Paths.get("F:\\ujnProject\\video\\" + videoFileName);
            Files.write(path1, video.getBytes());
            Video video1 = new Video();
            video1.setName(videoName);
            video1.setVideo(videoFileName);
            video1.setIsEnable(Integer.parseInt(isEnable));
            video1.setCreatTime(new Date());
            video1.setPicture(pictureFileName);
            video1.setCourseId(Integer.parseInt(courseId));
            video1.setTeacherId(Integer.parseInt(teacherId));
            videoService.addVideo(video1);
            // 返回上传成功的消息
            return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/videoTeacher")
    public String videoTeacher(@RequestParam("videoId") String videoId,Model model){
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        model.addAttribute("video",video);
        return "videoTeacher";
    }

    @RequestMapping("/videoStudent")
    public String videoStudent(@RequestParam("user") String userId,@RequestParam("video") String videoId,@RequestParam("classId") String classId,Model model){
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        model.addAttribute("video",video);
        User user = userService.queryUserById(Integer.parseInt(userId));
        model.addAttribute("user",user);
        model.addAttribute("classId",classId);
        StudentVideo studentVideo = studentVideoService.queryStudentVideoByStudentAndClassAndVideo(Integer.parseInt(userId),Integer.parseInt(classId),Integer.parseInt(videoId));
        model.addAttribute("studentVideo",studentVideo);
        return "videoStudent";
    }

    @RequestMapping ("/videoDistribute")
    public String videoDistribute(@RequestParam("videoId") String videoId,Model model){
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        List<ClassVideo> classVideos = classVideoService.queryClassVideoListByVideo(Integer.parseInt(videoId));
        List<Classes> classList = new ArrayList<>();
        for (int i = 0; i <classVideos.size(); i++){
            classList.add(classesService.queryClassesById(classVideos.get(i).getClassId()));
        }
        List<Classes> classesListAll = classesService.queryClassesListByCourse(video.getCourseId());
        classesListAll.removeAll(classList);
        model.addAttribute("classesListAll",classesListAll);
        model.addAttribute("classList",classList);
        model.addAttribute("videoId",videoId);
        return "videoClass";
    }

    @RequestMapping("/addVideoClass")
    public String addVideoClass(@RequestParam("classId") String classId,@RequestParam("videoId") String videoId,Model model){
        ClassVideo classVideo = new ClassVideo();
        classVideo.setClassId(Integer.parseInt(classId));
        classVideo.setVideoId(Integer.parseInt(videoId));
        classVideoService.addClassVideo(classVideo);
        List<ClassStudent> classStudent = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        for (int i = 0; i <classStudent.size(); i++){
            StudentVideo studentVideo = new StudentVideo();
            studentVideo.setStudentId(classStudent.get(i).getStudentId());
            studentVideo.setClassId(Integer.parseInt(classId));
            studentVideo.setStatus(0);
            studentVideo.setVideoId(Integer.parseInt(videoId));
            studentVideoService.addStudentVideo(studentVideo);
        }
        model.addAttribute("videoId",videoId);
        return "redirect:/videoDistribute?videoId="+videoId;
    }

    @RequestMapping("/deleteVideoClass")
    public ResponseEntity<String> deleteVideoClass(@RequestParam("classId") String classId,@RequestParam("videoId") String videoId){
        ClassVideo classVideo = classVideoService.queryClassVideoByClassAndVideo(Integer.parseInt(classId),Integer.parseInt(videoId));
        classVideoService.deleteClassVideo(classVideo.getId());
        studentVideoService.deleteStudentVideoByClassAndVideo(Integer.parseInt(classId),Integer.parseInt(videoId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/deleteVideo")
    public ResponseEntity<String> deleteVideo(@RequestParam("videoId") String videoId){
        List<ClassVideo> classVideos = classVideoService.queryClassVideoListByVideo(Integer.parseInt(videoId));
        for (int i = 0; i < classVideos.size(); i++){
            classVideoService.deleteClassVideo(classVideos.get(i).getId());
        }
        List<StudentVideo> studentVideos = studentVideoService.queryStudentVideoByVideo(Integer.parseInt(videoId));
        for (int i = 0; i < studentVideos.size(); i++){
            studentVideoService.deleteStudentVideo(studentVideos.get(i).getId());
        }
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        File file = new File("F:\\ujnProject\\image\\" + video.getPicture());
        if (file.exists()){
            file.delete();
        }
        File file1 = new File("F:\\ujnProject\\video\\" + video.getVideo());
        if (file1.exists()){
            file1.delete();
        }
        videoService.deleteVideo(Integer.parseInt(videoId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/updateStudentVideo")
    public ResponseEntity<String> updateStudentVideo(@RequestParam("classId") String classId,@RequestParam("videoId") String videoId,@RequestParam("userId") String userId){
        StudentVideo studentVideo = studentVideoService.queryStudentVideoByStudentAndClassAndVideo(Integer.parseInt(userId),Integer.parseInt(classId),Integer.parseInt(videoId));
        studentVideo.setStatus(1);
        studentVideoService.updateStudentVideo(studentVideo);
        return new ResponseEntity<>("观看完成", HttpStatus.OK);
    }

    @RequestMapping("/reviseVideo")
    ResponseEntity<String> reviseExercise(@RequestParam(name = "file", required = false) MultipartFile file,@RequestParam("videoId") String videoId,@RequestParam("videoName") String videoName) throws IOException {
        Video video = videoService.queryVideoById(Integer.parseInt(videoId));
        if (file != null){
            String fileNameOrigin = file.getOriginalFilename();
            int index = fileNameOrigin.indexOf(".");
            String fileName = UUID.randomUUID().toString()+fileNameOrigin.substring(index);
            Path path = Paths.get("F:\\ujnProject\\image\\" + fileName);
            Files.write(path, file.getBytes());
            File file1 = new File("F:\\ujnProject\\image\\"+video.getPicture());
            if (file1.exists()){
                file1.delete();
            }
            video.setPicture(fileName);
        }
        video.setName(videoName);
        videoService.updateVideo(video);
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }


}
