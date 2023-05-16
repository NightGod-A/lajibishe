package com.ujn.studyweb.controller;


import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
public class CourseController {
    private final ResourceLoader resourceLoader;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVideoService videoService;
    @Autowired
    private IExerciseService exerciseService;
    @Autowired
    private IClassExerciseService classExerciseService;
    @Autowired
    private IClassVideoService classVideoService;
    @Autowired
    private IStudentVideoService studentVideoService;
    @Autowired
    private IStudentExerciseService studentExerciseService;
    @Autowired
    private IStudentChosenService studentChosenService;
    @Autowired
    private IStudentJudgementService studentJudgementService;
    @Autowired
    private IExamService examService;
    @Autowired
    private IClassExamService classExamService;
    @Autowired
    private IStudentExamService studentExamService;
    @Autowired
    private IClassStudentService classStudentService;
    @Autowired
    private IClassPPTService classPPTService;
    @Autowired
    private IPPTService pptService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IChosenService chosenService;
    @Autowired
    private IJudgementService judgementService;


    @Autowired
    public CourseController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/searchCourseTeacher")
    public String searchCourseTeacher(@RequestParam("teacherId") String teacherId){
        User user = userService.queryUserById(Integer.parseInt(teacherId));
        return user.getName();
    }

    @RequestMapping("/addCourse")
    public ResponseEntity<String> addCourse(@RequestParam("file") MultipartFile file, @RequestParam("name") String courseName, @RequestParam("description") String courseDescription,@RequestParam("userId") String userId,@RequestParam("isEnable") String isEnable){
        try {
            // 保存文件到磁盘
            String fileNameOrigin = file.getOriginalFilename();
            int index = fileNameOrigin.indexOf(".");
            String fileName = UUID.randomUUID().toString()+fileNameOrigin.substring(index);
            Path path = Paths.get("F:\\ujnProject\\image\\" + fileName);
            Files.write(path, file.getBytes());
            Course course = new Course();
            course.setName(courseName);
            course.setDescription(courseDescription);
            course.setPicture(fileName);
            course.setTeacherId(Integer.parseInt(userId));
            course.setIsEnable(Integer.parseInt(isEnable));
            course.setCreatTime(new Date());
            courseService.addCourse(course);
            // 返回上传成功的消息
            return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/manageCourse")
    public ResponseEntity<String> manageCourse(@RequestParam(name = "file", required = false) MultipartFile file, @RequestParam("name") String courseName, @RequestParam("description") String courseDescription,@RequestParam("userId") String userId,@RequestParam("isEnable") String isEnable,@RequestParam("courseId") String courseId){
        try {
            // 保存文件到磁盘
            Course course = courseService.queryCourseById(Integer.parseInt(courseId));
            if (file != null)
            {
                String fileNameOrigin = file.getOriginalFilename();
                int index = fileNameOrigin.indexOf(".");
                String fileName = UUID.randomUUID().toString()+fileNameOrigin.substring(index);
                Path path = Paths.get("F:\\ujnProject\\image\\" + fileName);
                Files.write(path, file.getBytes());
                File file1 = new File("F:\\ujnProject\\image\\"+course.getPicture());
                if (file1.exists()){
                    file1.delete();
                }
                course.setPicture(fileName);
            }
            course.setName(courseName);
            course.setDescription(courseDescription);
            course.setIsEnable(Integer.parseInt(isEnable));
            courseService.updateCourse(course);
            // 返回上传成功的消息
            return new ResponseEntity<>("修改成功", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/viewCourse")
    public String viewCourse(@RequestParam("student") String userId,@RequestParam("course") String courseId,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(userId));
        Course course = courseService.queryCourseById(Integer.parseInt(courseId));
        List<ClassVideo> classVideos = classVideoService.queryClassVideoListByClass(Integer.parseInt(classId));
        List<StudentVideo> studentVideos = new ArrayList<>();
        for (int i = 0; i < classVideos.size(); i++){
            StudentVideo studentVideo = studentVideoService.queryStudentVideoByStudentAndClassAndVideo(Integer.parseInt(userId),Integer.parseInt(classId),classVideos.get(i).getVideoId());
            studentVideo.setVideo(videoService.queryVideoById(classVideos.get(i).getVideoId()));
            studentVideos.add(studentVideo);
        }
        List<StudentExercise> studentExercises = new ArrayList<>();
        List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByClass(Integer.parseInt(classId));
        for (int i = 0; i < classExercises.size(); i++){
            StudentExercise studentExercise = studentExerciseService.queryStudentExerciseByStudentAndClassAndExercise(Integer.parseInt(userId),Integer.parseInt(classId),classExercises.get(i).getExerciseId());
            studentExercise.setExercise(exerciseService.queryExerciseById(classExercises.get(i).getExerciseId()));
            studentExercises.add(studentExercise);
        }
        List<StudentExam> studentExams = new ArrayList<>();
        List<ClassExam> classExams = classExamService.queryClassExamListByClass(Integer.parseInt(classId));
        for (int i = 0; i < classExams.size(); i++){
            StudentExam studentExam = studentExamService.queryStudentExamByStudentAndClassAndExam(Integer.parseInt(userId),Integer.parseInt(classId),classExams.get(i).getExamId());
            studentExam.setExam(examService.queryExamById(classExams.get(i).getExamId()));
            if (studentExam.getStatus() == 1){
                LocalDateTime localDateTime = LocalDateTime.now();
                //计算出两个时间的差值
                Duration differenceValue = Duration.between(studentExam.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),localDateTime);
                // 获取的是两个时间相差的分钟数,如果想要相差小时数就调用toHours()
                Long minutesTime = Math.abs(differenceValue .toMinutes());
                if (minutesTime >= studentExam.getExam().getMinute()){
                    studentExam.setStatus(-1);
                    studentExamService.updateStudentExam(studentExam);
                }
            }
            studentExams.add(studentExam);
        }
        List<ClassPPT> classPPTS = classPPTService.queryClassPPTListByClass(Integer.parseInt(classId));
        for (int i = 0; i < classPPTS.size(); i++){
            classPPTS.get(i).setPpt(pptService.queryPPTById(classPPTS.get(i).getPptId()));
        }
        model.addAttribute("classId",classId);
        model.addAttribute("user",user);
        model.addAttribute("course",course);
        model.addAttribute("classPPTS",classPPTS);
        model.addAttribute("studentVideos",studentVideos);
        model.addAttribute("studentExercises",studentExercises);
        model.addAttribute("studentExams",studentExams);
        return "study";
    }

    @RequestMapping("show")
    public ResponseEntity showPhotos(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + "F:\\ujnProject\\image\\" + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("showPPT")
    public ResponseEntity showPPT(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + "F:\\ujnProject\\pptImage\\" + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("showVideo")
    public ResponseEntity showVideos(String fileName){
        try {
            // 由于是读取本机的文件，file是一定要加上的， path是在application配置文件中的路径
            return ResponseEntity.ok(resourceLoader.getResource("file:" + "F:\\ujnProject\\video\\" + fileName));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping("/studentProgress")
    public String studentProgress(@RequestParam("userId") String userId,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(userId));
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        List<StudentExam> studentExams = studentExamService.queryStudentExamByStudentAndClass(Integer.parseInt(userId),Integer.parseInt(classId));
        List<StudentVideo> studentVideos = studentVideoService.queryStudentVideoByStudentAndClass(Integer.parseInt(userId),Integer.parseInt(classId));
        List<StudentExercise> studentExercises = studentExerciseService.queryStudentExerciseByStudentAndClass(Integer.parseInt(userId),Integer.parseInt(classId));
        int exam1 = 0,exercise1 = 0,video1 = 0;
        for (int i = 0; i < studentExams.size(); i++){
            if (studentExams.get(i).getStatus() == 2){
                exam1++;
            }
        }
        for (int i = 0; i < studentVideos.size(); i++){
            if (studentVideos.get(i).getStatus() == 1){
                video1++;
            }
        }
        for (int i = 0; i < studentExercises.size(); i++){
            if (studentExercises.get(i).getStatus() == 1){
                exercise1++;
            }
        }
        double examProgress = 0.0;
        double exerciseProgress = 0.0;
        double videoProgress = 0.0;
        double progress = 0.0;
        if (studentExams.size() != 0){
            examProgress = Double.valueOf(exam1)/Double.valueOf(studentExams.size());
        }
        if (studentVideos.size() != 0){
            videoProgress = Double.valueOf(video1)/Double.valueOf(studentVideos.size());
        }
        if (studentExercises.size() != 0){
            exerciseProgress = Double.valueOf(exercise1)/Double.valueOf(studentExercises.size());
        }
        if ((studentExams.size()+studentVideos.size()+studentExercises.size()) != 0){
            progress = Double.valueOf(exam1+video1+exercise1) / Double.valueOf(studentExams.size()+studentVideos.size()+studentExercises.size());
            System.out.println(progress);
        }
        model.addAttribute("examProgress",examProgress);
        model.addAttribute("exerciseProgress",exerciseProgress);
        model.addAttribute("videoProgress",videoProgress);
        model.addAttribute("progress",progress);

        int twenty = 0;
        int forty = 0;
        int sixty = 0;
        int eighty = 0;
        int hundred = 0;
        for (int j = 0; j < classStudents.size(); j++){

            List<StudentExam> studentExamAll = studentExamService.queryStudentExamByStudentAndClass(classStudents.get(j).getStudentId(),Integer.parseInt(classId));
            List<StudentVideo> studentVideoAll = studentVideoService.queryStudentVideoByStudentAndClass(classStudents.get(j).getStudentId(),Integer.parseInt(classId));
            List<StudentExercise> studentExerciseAll = studentExerciseService.queryStudentExerciseByStudentAndClass(classStudents.get(j).getStudentId(),Integer.parseInt(classId));
            int examALl = 0,exerciseAll = 0,videoAll = 0;
            for (int i = 0; i < studentExamAll.size(); i++){
                if (studentExamAll.get(i).getStatus() == 2){
                    examALl++;
                }
            }
            for (int i = 0; i < studentVideoAll.size(); i++){
                if (studentVideoAll.get(i).getStatus() == 1){
                    videoAll++;
                }
            }
            for (int i = 0; i < studentExerciseAll.size(); i++){
                if (studentExerciseAll.get(i).getStatus() == 1){
                    exerciseAll++;
                }
            }

            double progressAll = 0.0;
            if ((studentExamAll.size()+studentVideoAll.size()+studentExerciseAll.size()) != 0){
                progressAll = Double.valueOf(examALl+videoAll+exerciseAll) / Double.valueOf(studentExamAll.size()+studentVideoAll.size()+studentExerciseAll.size());
            }
            if (progressAll >= 0 && progressAll <= 0.2){
                twenty++;
            }
            if (progressAll > 0.2 && progressAll <= 0.4){
                forty++;
            }
            if (progressAll > 0.4 && progressAll <= 0.6){
                sixty++;
            }
            if (progressAll > 0.6 && progressAll <= 0.8){
                eighty++;
            }
            if (progressAll > 0.8 && progressAll <= 1){
                hundred++;
            }
        }
        model.addAttribute("twenty",twenty);
        model.addAttribute("forty",forty);
        model.addAttribute("sixty",sixty);
        model.addAttribute("eighty",eighty);
        model.addAttribute("hundred",hundred);
        model.addAttribute("size",classStudents.size());
        model.addAttribute("user",user);
        return "studentProgress";
    }

    @RequestMapping("/deleteCourse")
    public ResponseEntity<String> deleteCourse(@RequestParam("course") String course){
        int courseId = Integer.parseInt(course);
        List<PPT> ppts = pptService.queryPPTByCourse(courseId);
        List<Exam> exams = examService.queryExamByCourse(courseId);
        List<Exercise> exercises = exerciseService.queryExerciseByCourse(courseId);
        List<Video> videos = videoService.queryVideoByCourse(courseId);
        List<Classes> classes = classesService.queryClassesListByCourse(courseId);
        for (int j = 0; j < ppts.size(); j++){
            List<ClassPPT> classPPTs = classPPTService.queryClassPPTListByPPT(ppts.get(j).getId());
            for (int i = 0; i < classPPTs.size(); i++){
                classPPTService.deleteClassPPT(classPPTs.get(i).getId());
            }
            PPT ppt = pptService.queryPPTById(ppts.get(j).getId());
            File file = new File("F:\\ujnProject\\ppt\\" + ppt.getPpt());
            if (file.exists()){
                file.delete();
            }
            for (int i = 0; i < ppt.getPageNum(); i++){
                File jpgFile = new File("F:\\ujnProject\\pptImage\\" + ppts.get(j).getId() + "Slide" + (i + 1) + ".jpg");
                if (jpgFile.exists()){
                    jpgFile.delete();
                }
            }
            pptService.deletePPT(ppts.get(j).getId());
        }

        for (int j = 0; j < exams.size(); j++){
            List<ClassExam> classExams = classExamService.queryClassExamListByExam(exams.get(j).getId());
            for (int i = 0; i < classExams.size(); i++){
                classExamService.deleteClassExam(classExams.get(i).getId());
            }
            List<StudentExam> studentExams = studentExamService.queryStudentExamByExam(exams.get(j).getId());
            for (int i = 0; i < studentExams.size(); i++){
                studentExamService.deleteStudentExam(studentExams.get(i).getId());
            }
            List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExam(exams.get(j).getId());
            for (int i = 0; i < studentChosens.size(); i++){
                studentChosenService.deleteStudentChosen(studentChosens.get(i).getId());
            }
            List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExam(exams.get(j).getId());
            for (int i = 0; i < studentJudgements.size(); i++){
                studentJudgementService.deleteStudentJudgement(studentJudgements.get(i).getId());
            }
            List<Chosen> chosens = chosenService.queryChosenByExam(exams.get(j).getId());
            for (int i = 0; i < chosens.size(); i++){
                chosenService.deleteChosen(chosens.get(i).getId());
            }
            List<Judgement> judgements = judgementService.queryJudgementByExam(exams.get(j).getId());
            for (int i = 0; i < judgements.size(); i++){
                judgementService.deleteJudgement(judgements.get(i).getId());
            }
            examService.deleteExam(exams.get(j).getId());
        }

        for (int j = 0; j < exercises.size(); j++){
            List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByExercise(exercises.get(j).getId());
            for (int i = 0; i < classExercises.size(); i++){
                classExerciseService.deleteClassExercise(classExercises.get(i).getId());
            }
            List<StudentExercise> studentExercises = studentExerciseService.queryStudentExerciseByExercise(exercises.get(j).getId());
            for (int i = 0; i < studentExercises.size(); i++){
                studentExerciseService.deleteStudentExercise(studentExercises.get(i).getId());
            }
            List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExercise(exercises.get(j).getId());
            for (int i = 0; i < studentChosens.size(); i++){
                studentChosenService.deleteStudentChosen(studentChosens.get(i).getId());
            }
            List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExercise(exercises.get(j).getId());
            for (int i = 0; i < studentJudgements.size(); i++){
                studentJudgementService.deleteStudentJudgement(studentJudgements.get(i).getId());
            }
            List<Chosen> chosens = chosenService.queryChosenByExercise(exercises.get(j).getId());
            for (int i = 0; i < chosens.size(); i++){
                chosenService.deleteChosen(chosens.get(i).getId());
            }
            List<Judgement> judgements = judgementService.queryJudgementByExercise(exercises.get(j).getId());
            for (int i = 0; i < judgements.size(); i++){
                judgementService.deleteJudgement(judgements.get(i).getId());
            }
            exerciseService.deleteExercise(exercises.get(j).getId());
        }

        for (int j = 0; j < videos.size(); j++){
            List<ClassVideo> classVideos = classVideoService.queryClassVideoListByVideo(videos.get(j).getId());
            for (int i = 0; i < classVideos.size(); i++){
                classVideoService.deleteClassVideo(classVideos.get(i).getId());
            }
            List<StudentVideo> studentVideos = studentVideoService.queryStudentVideoByVideo(videos.get(j).getId());
            for (int i = 0; i < studentVideos.size(); i++){
                studentVideoService.deleteStudentVideo(studentVideos.get(i).getId());
            }
            Video video = videoService.queryVideoById(videos.get(j).getId());
            File file = new File("F:\\ujnProject\\image\\" + video.getPicture());
            if (file.exists()){
                file.delete();
            }
            File file1 = new File("F:\\ujnProject\\video\\" + video.getVideo());
            if (file1.exists()){
                file1.delete();
            }
            videoService.deleteVideo(videos.get(j).getId());
        }

        for (int j = 0; j < classes.size(); j++){
            List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(classes.get(j).getId());
            for (int i = 0; i < classStudents.size(); i++){
                classStudentService.deleteClassStudent(classStudents.get(i).getId());
            }
            classesService.deleteClasses(classes.get(j).getId());
        }

        courseService.deleteCourse(courseId);

        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

}
