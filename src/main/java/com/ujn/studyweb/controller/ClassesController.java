package com.ujn.studyweb.controller;

import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.*;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ClassesController {

    @Autowired
    private IClassesService classesService;
    @Autowired
    private IClassStudentService classStudentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassVideoService classVideoService;
    @Autowired
    private IClassExerciseService classExerciseService;
    @Autowired
    private IClassExamService classExamService;
    @Autowired
    private IClassPPTService classPPTService;
    @Autowired
    private IStudentVideoService studentVideoService;
    @Autowired
    private IStudentExerciseService studentExerciseService;
    @Autowired
    private IStudentExamService studentExamService;
    @Autowired
    private IStudentChosenService studentChosenService;
    @Autowired
    private IStudentJudgementService studentJudgementService;


    public void addStudent(int classId,int studentId){
        ClassStudent classStudent = new ClassStudent();
        List<ClassVideo> classVideos = classVideoService.queryClassVideoListByClass(classId);
        List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByClass(classId);
        List<ClassExam> classExams = classExamService.queryClassExamListByClass(classId);
        for (int m = 0; m < classVideos.size(); m++){
            StudentVideo studentVideo = new StudentVideo();
            studentVideo.setVideoId(classVideos.get(m).getVideoId());
            studentVideo.setStatus(0);
            studentVideo.setClassId(classId);
            studentVideo.setStudentId(studentId);
            studentVideoService.addStudentVideo(studentVideo);
        }
        for (int m = 0; m < classExercises.size(); m++){
            StudentExercise studentExercise = new StudentExercise();
            studentExercise.setExerciseId(classExercises.get(m).getExerciseId());
            studentExercise.setStatus(0);
            studentExercise.setClassId(classId);
            studentExercise.setStudentId(studentId);
            studentExerciseService.addStudentExercise(studentExercise);
        }
        for (int m = 0; m < classExams.size(); m++){
            StudentExam studentExam = new StudentExam();
            studentExam.setExamId(classExams.get(m).getExamId());
            studentExam.setStatus(0);
            studentExam.setClassId(classId);
            studentExam.setStudentId(studentId);
            studentExam.setScore(0);
            studentExamService.addStudentExam(studentExam);
        }
        classStudent.setStudentId(studentId);
        classStudent.setClassId(classId);
        classStudentService.addClassStudent(classStudent);
    }

    @RequestMapping("/classManage")
    public String classManage (@RequestParam("course") String courseId, Model model){
        List<Classes> classesList = classesService.queryClassesListByCourse(Integer.parseInt(courseId));
        model.addAttribute("classList",classesList);
        model.addAttribute("course",courseId);
        return "classManage";
    }

    @RequestMapping("/addClass")
    public ResponseEntity<String> addClass(@RequestParam("course") String course, @RequestParam("className") String className){
        Classes classes = new Classes();
        classes.setName(className);
        classes.setCourseId(Integer.parseInt(course));
        classes.setCreatTime(new Date());
        classesService.addClasses(classes);
        // 返回上传成功的消息
        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

    @RequestMapping("/addClassStudent")
    public ResponseEntity<String> addClassStudent(@RequestParam("file") MultipartFile file,@RequestParam("classId") String classId){
        try {
            //将上传到的MultipartFile转为输入流，然后交给POI去解析即可。可以分为如下四个步骤：
            //1.创建HSSFWorkbook对象
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            //2.获取一共有多少sheet，然后遍历
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                //3.获取sheet中一共有多少行，遍历行（注意第一行是标题）
                int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();
                for (int j = 0; j < physicalNumberOfRows; j++) {
                    if (j == 0) {
                        continue;//标题行
                    }
                    //4.获取每一行有多少单元格，遍历单元格
                    int physicalNumberOfCells = sheet.getRow(j).getPhysicalNumberOfCells();
                    int studentId;
                    for (int k = 0; k < physicalNumberOfCells; k++) {

                        //获取单元格
                        XSSFCell cell = sheet.getRow(j).getCell(k);
                        //设置单元格类型
                        cell.setCellType(CellType.STRING);
                        //获取单元格数据
                        studentId = Integer.parseInt(cell.getStringCellValue());
                        if (userService.queryUserById(studentId) == null){
                            continue;
                        }
                        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByStudent(studentId);
                        int flag = 0;
                        for (int m = 0; m < classStudents.size(); m++){
                            if (classStudents.get(m).getClassId() == Integer.parseInt(classId)){
                                flag = 1;
                            }
                        }
                        if (flag == 1){
                            continue;
                        }
                        addStudent(Integer.parseInt(classId),studentId);
                    }
                }
            }
            // 返回上传成功的消息
            return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // 返回上传失败的消息
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/studentManage")
    public String studentManage(@RequestParam("classId") String classId, Model model){

        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        List<User> users = new ArrayList<>();
        List<User> searchUsers = new ArrayList<>();
        if (classStudents != null) {
            for (int i = 0; i < classStudents.size(); i++) {
                int studentId = classStudents.get(i).getStudentId();
                users.add(userService.queryUserById(studentId));
            }
        }
        model.addAttribute("classId",classId);
        model.addAttribute("users",users);
        model.addAttribute("searchUsers",searchUsers);
        return "studentManage";
    }

    @RequestMapping("/addSingleClassStudent")
    public ResponseEntity<String> addSingleClassStudent(@RequestParam("classId") String classId,@RequestParam("userId") String userId){
        addStudent(Integer.parseInt(classId),Integer.parseInt(userId));
        // 返回上传成功的消息
        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

    @RequestMapping("/deleteClass")
    public ResponseEntity<String> deleteClass(@RequestParam("classId") String classId){
        List<ClassVideo> classVideos = classVideoService.queryClassVideoListByClass(Integer.parseInt(classId));
        List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByClass(Integer.parseInt(classId));
        List<ClassExam> classExams = classExamService.queryClassExamListByClass(Integer.parseInt(classId));
        List<ClassPPT> classPPTS = classPPTService.queryClassPPTListByClass(Integer.parseInt(classId));
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        for (int i = 0; i < classVideos.size(); i++){
            studentVideoService.deleteStudentVideoByClassAndVideo(Integer.parseInt(classId),classVideos.get(i).getVideoId());
            classVideoService.deleteClassVideo(classVideos.get(i).getId());
        }
        for (int i = 0; i < classExercises.size(); i++){
            studentChosenService.deleteStudentChosenByClassAndExercise(Integer.parseInt(classId),classExercises.get(i).getExerciseId());
            studentJudgementService.deleteStudentJudgementByClassAndExercise(Integer.parseInt(classId),classExercises.get(i).getExerciseId());
            studentExerciseService.deleteStudentExerciseByClassAndExercise(Integer.parseInt(classId),classExercises.get(i).getExerciseId());
            classExerciseService.deleteClassExercise(classExercises.get(i).getId());
        }
        for (int i = 0; i < classExams.size(); i++){
            studentJudgementService.deleteStudentJudgementByClassAndExam(Integer.parseInt(classId),classExams.get(i).getExamId());
            studentChosenService.deleteStudentChosenByClassAndExam(Integer.parseInt(classId),classExams.get(i).getExamId());
            studentExamService.deleteStudentExamByClassAndExam(Integer.parseInt(classId),classExams.get(i).getExamId());
            classExamService.deleteClassExam(classExams.get(i).getId());
        }
        for (int i = 0; i < classPPTS.size(); i++){
            classPPTService.deleteClassPPT(classPPTS.get(i).getId());
        }
        for (int i = 0; i < classStudents.size(); i++){
            classStudentService.deleteClassStudent(classStudents.get(i).getId());
        }
        classesService.deleteClasses(Integer.parseInt(classId));


        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

    @RequestMapping("/searchClassStudent")
    public String searchClassStudent(@RequestParam("classId") String classId,@RequestParam("nameId") String nameId,Model model){
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        List<User> users = new ArrayList<>();

        if (classStudents != null) {
            for (int i = 0; i < classStudents.size(); i++) {
                int studentId = classStudents.get(i).getStudentId();
                users.add(userService.queryUserById(studentId));
            }
        }
        List<User> searchUsers = userService.queryUserListByLike(nameId);
        List<User> tempUsers = new ArrayList<>(searchUsers);
        tempUsers.retainAll(users);

        searchUsers.removeAll(tempUsers);
        for (int i = 0; i < tempUsers.size(); i++) {
            if (tempUsers.size() == 0){
                break;
            }
            tempUsers.get(i).setClassStatus(1);
        }
        for (int i = 0; i < searchUsers.size(); i++) {
            if (searchUsers.size() == 0){
                break;
            }
            searchUsers.get(i).setClassStatus(0);
        }
        searchUsers.addAll(tempUsers);
        model.addAttribute("classId",classId);
        model.addAttribute("users",users);
        model.addAttribute("searchUsers",searchUsers);
        return "studentManage";
    }

    @RequestMapping("/deleteStudent")
    public ResponseEntity<String> deleteStudent(@RequestParam("classId") String classId,@RequestParam("userId") String userId){
        int idClass = Integer.parseInt(classId);
        int idStudent = Integer.parseInt(userId);
        List<StudentExam> studentExams = studentExamService.queryStudentExamByStudentAndClass(idStudent,idClass);
        for (int i = 0; i < studentExams.size(); i++){
            studentExamService.deleteStudentExam(studentExams.get(i).getId());
        }
        List<StudentExercise> studentExercises = studentExerciseService.queryStudentExerciseByStudentAndClass(idStudent,idClass);
        for (int i = 0; i < studentExercises.size(); i++){
            studentExerciseService.deleteStudentExercise(studentExercises.get(i).getId());
        }
        List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByStudentAndClass(idStudent,idClass);
        for (int i = 0; i < studentChosens.size(); i++){
            studentChosenService.deleteStudentChosen(studentChosens.get(i).getId());
        }
        List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByStudentAndClass(idStudent,idClass);
        for (int i = 0; i < studentJudgements.size(); i++){
            studentJudgementService.deleteStudentJudgement(studentJudgements.get(i).getId());
        }
        List<StudentVideo> studentVideos = studentVideoService.queryStudentVideoByStudentAndClass(idStudent,idClass);
        for (int i = 0; i < studentVideos.size(); i++){
            studentVideoService.deleteStudentVideo(studentVideos.get(i).getId());
        }
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByStudent(idStudent);
        for (int i = 0; i < classStudents.size(); i++){
            if (classStudents.get(i).getClassId() == idClass){
                classStudentService.deleteClassStudent(classStudents.get(i).getId());
                break;
            }
        }
        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }
}
