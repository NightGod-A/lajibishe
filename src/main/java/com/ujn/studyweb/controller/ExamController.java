package com.ujn.studyweb.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.*;
import javafx.beans.property.IntegerProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ExamController {
    @Autowired
    private IExamService examService;
    @Autowired
    private IJudgementService judgementService;
    @Autowired
    private IChosenService chosenService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IClassExamService classExamService;
    @Autowired
    private IClassStudentService classStudentService;
    @Autowired
    private IStudentExamService studentExamService;
    @Autowired
    private IStudentJudgementService studentJudgementService;
    @Autowired
    private IStudentChosenService studentChosenService;
    @Autowired
    private ICourseService courseService;

    @RequestMapping("/examManage")
    public String examManage(@RequestParam("teacher") String teacher, @RequestParam("course") String course, Model model){
        List<Exam> exams = examService.queryExamByCourse(Integer.parseInt(course));
        model.addAttribute("exams",exams);
        model.addAttribute("teacher",teacher);
        model.addAttribute("course",course);
        return "examManage";
    }


    @RequestMapping("/examCreat")
    public String examCreat(@RequestParam("teacher") String teacher, @RequestParam("course") String course,Model model){
        model.addAttribute("teacher",teacher);
        model.addAttribute("course",course);
        return "examCreat";
    }

    @RequestMapping("/addExam")
    public ResponseEntity<String> addExam(@RequestBody String jsonString) throws ParseException {

        JSONObject jsonObject = new JSONObject().parse(jsonString);
        String examName = jsonObject.getString("examName");
        String course = jsonObject.getString("course");
        String teacher = jsonObject.getString("teacher");
        int examTime = jsonObject.getInteger("examTime");
        String startTime = jsonObject.getString("startTime").replace("T"," ");
        String endTime = jsonObject.getString("endTime").replace("T"," ");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        start = df.parse(startTime);
        end = df.parse(endTime);
        JSONArray singleQuestionName = jsonObject.getJSONArray("singleQuestionName");
        JSONArray singleQuestionOptionA = jsonObject.getJSONArray("singleQuestionOptionA");
        JSONArray singleQuestionOptionB = jsonObject.getJSONArray("singleQuestionOptionB");
        JSONArray singleQuestionOptionC = jsonObject.getJSONArray("singleQuestionOptionC");
        JSONArray singleQuestionOptionD = jsonObject.getJSONArray("singleQuestionOptionD");
        JSONArray singleQuestionCheck = jsonObject.getJSONArray("singleQuestionCheck");
        JSONArray judgmentQuestionName = jsonObject.getJSONArray("judgmentQuestionName");
        JSONArray judgmentQuestionCheck = jsonObject.getJSONArray("judgmentQuestionCheck");

        Exam exam = new Exam();
        exam.setName(examName);
        exam.setCreatTime(new Date());
        exam.setCourseId(Integer.parseInt(course));
        exam.setTeacherId(Integer.parseInt(teacher));
        exam.setMinute(examTime);
        exam.setStartTime(start);
        exam.setEndTime(end);
        examService.addExam(exam);
        int examId = exam.getId();
        for (int i = 0; i < singleQuestionName.size(); i++) {
            Chosen chosen = new Chosen();
            chosen.setExamId(examId);
            chosen.setSeq(i+1);
            chosen.setQuestion(singleQuestionName.get(i).toString());
            chosen.setAnswer(singleQuestionCheck.get(i).toString());
            chosen.setChosenA(singleQuestionOptionA.get(i).toString());
            chosen.setChosenB(singleQuestionOptionB.get(i).toString());
            chosen.setChosenC(singleQuestionOptionC.get(i).toString());
            chosen.setChosenD(singleQuestionOptionD.get(i).toString());
            chosenService.addChosen(chosen);
        }
        for (int i = 0; i < judgmentQuestionName.size(); i++) {
            Judgement judgement = new Judgement();
            judgement.setExamId(examId);
            judgement.setSeq(i+1);
            judgement.setQuestion(judgmentQuestionName.get(i).toString());
            judgement.setAnswer(judgmentQuestionCheck.get(i).toString());
            judgementService.addJudgement(judgement);
        }

        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @RequestMapping("/viewExamResult")
    public String viewExamResult(@RequestParam("user") String id,@RequestParam("exam") String exam,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(id));
        StudentExam studentExam = studentExamService.queryStudentExamByStudentAndClassAndExam(user.getId(),Integer.parseInt(classId),Integer.parseInt(exam));
        model.addAttribute("studentExam",studentExam);
        model.addAttribute("user",user);
        model.addAttribute("classId",classId);
        Exam exam1 = examService.queryExamById(Integer.parseInt(exam));
        List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExamAndStudentAndClass(Integer.parseInt(exam),Integer.parseInt(id),Integer.parseInt(classId));
        List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExamAndStudentAndClass(Integer.parseInt(exam),Integer.parseInt(id),Integer.parseInt(classId));

        for (int i = 0; i < studentChosens.size(); i++){
            Chosen chosen = chosenService.queryChosenById(studentChosens.get(i).getChosenId());
            studentChosens.get(i).setChosen(chosen);
        }
        for (int i = 0; i < studentJudgements.size(); i++){
            Judgement judgement = judgementService.queryJudgementById(studentJudgements.get(i).getJudgementId());
            studentJudgements.get(i).setJudgement(judgement);
        }
        model.addAttribute("examId",exam);
        model.addAttribute("studentChosens",studentChosens);
        model.addAttribute("studentJudgements",studentJudgements);
        model.addAttribute("course",exam1.getCourseId());
        Course course = courseService.queryCourseById(exam1.getCourseId());
        model.addAttribute("teacher",course.getTeacherId());
        return "examResult";
    }

    @RequestMapping("/viewExamPage")
    public String viewExamPage(@RequestParam("exam") String exam,Model model){

        Exam exam1 = examService.queryExamById(Integer.parseInt(exam));
        List<Chosen> chosens = chosenService.queryChosenByExam(Integer.parseInt(exam));
        List<Judgement> judgements = judgementService.queryJudgementByExam(Integer.parseInt(exam));

        model.addAttribute("examId",exam);
        model.addAttribute("chosens",chosens);
        model.addAttribute("judgements",judgements);
        model.addAttribute("course",exam1.getCourseId());
        Course course = courseService.queryCourseById(exam1.getCourseId());
        model.addAttribute("teacher",course.getTeacherId());
        return "examView";
    }

    @RequestMapping("/viewExam")
    public String viewExam(@RequestParam("user") String id,@RequestParam("exam") String exam,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(id));
        model.addAttribute("user",user);
        model.addAttribute("classId",classId);
        Classes classes = classesService.queryClassesById(Integer.parseInt(classId));
        Exam exam1 = examService.queryExamById(Integer.parseInt(exam));
        List<Chosen> chosens = chosenService.queryChosenByExam(Integer.parseInt(exam));
        List<Judgement> judgements = judgementService.queryJudgementByExam(Integer.parseInt(exam));
        StudentExam studentExam = studentExamService.queryStudentExamByStudentAndClassAndExam(Integer.parseInt(id),Integer.parseInt(classId),Integer.parseInt(exam));
        if (studentExam.getStatus() == 0){
            studentExam.setStartTime(new Date());
            studentExam.setStatus(1);
            model.addAttribute("time",exam1.getMinute());
            System.out.println("ooo"+exam1.getMinute());
            studentExamService.updateStudentExam(studentExam);
        }
        else if (studentExam.getStatus() == 1){
            LocalDateTime localDateTime = LocalDateTime.now();
            //计算出两个时间的差值
            Duration differenceValue = Duration.between(studentExam.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(),localDateTime);
            // 获取的是两个时间相差的分钟数,如果想要相差小时数就调用toHours()
            Long minutesTime = Math.abs(differenceValue .toMinutes());
            System.out.println(minutesTime);
            if (minutesTime >= exam1.getMinute()){
                studentExam.setStatus(-1);
                studentExamService.updateStudentExam(studentExam);
                return "redirect:/viewCourse?student="+id+"&course="+classes.getCourseId()+"&classId"+classId;
            }
            model.addAttribute("time",exam1.getMinute()-minutesTime);
        }

        model.addAttribute("examId",exam);
        model.addAttribute("chosens",chosens);
        model.addAttribute("judgements",judgements);
        model.addAttribute("course",exam1.getCourseId());
        return "exam";
    }

    @RequestMapping ("/examDistribute")
    public String examDistribute(@RequestParam("examId") String examId,Model model){
        Exam exam = examService.queryExamById(Integer.parseInt(examId));
        List<ClassExam> classExams = classExamService.queryClassExamListByExam(Integer.parseInt(examId));
        List<Classes> classList = new ArrayList<>();
        for (int i = 0; i <classExams.size(); i++){
            classList.add(classesService.queryClassesById(classExams.get(i).getClassId()));
        }
        List<Classes> classesListAll = classesService.queryClassesListByCourse(exam.getCourseId());
        classesListAll.removeAll(classList);
        model.addAttribute("classesListAll",classesListAll);
        model.addAttribute("classList",classList);
        model.addAttribute("examId",examId);
        return "examClass";
    }

    @RequestMapping("/addExamClass")
    public String addExamClass(@RequestParam("classId") String classId,@RequestParam("examId") String examId){
        ClassExam classExam = new ClassExam();
        classExam.setClassId(Integer.parseInt(classId));
        classExam.setExamId(Integer.parseInt(examId));
        classExamService.addClassExam(classExam);
        List<ClassStudent> classStudent = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        for (int i = 0; i <classStudent.size(); i++){
            StudentExam studentExam = new StudentExam();
            studentExam.setExamId(Integer.parseInt(examId));
            studentExam.setStudentId(classStudent.get(i).getStudentId());
            studentExam.setStatus(0);
            studentExam.setClassId(Integer.parseInt(classId));
            studentExamService.addStudentExam(studentExam);
        }
        return "redirect:/examDistribute?examId="+examId;
    }

    @RequestMapping("/deleteExamClass")
    public ResponseEntity<String> deleteExamClass(@RequestParam("classId") String classId,@RequestParam("examId") String examId){
        ClassExam classExam = classExamService.queryClassExamByClassAndExam(Integer.parseInt(classId),Integer.parseInt(examId));
        classExamService.deleteClassExam(classExam.getId());
        studentExamService.deleteStudentExamByClassAndExam(Integer.parseInt(classId),Integer.parseInt(examId));
        studentJudgementService.deleteStudentJudgementByClassAndExam(Integer.parseInt(classId),Integer.parseInt(examId));
        studentChosenService.deleteStudentChosenByClassAndExam(Integer.parseInt(classId),Integer.parseInt(examId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/deleteExam")
    public ResponseEntity<String> deleteExam(@RequestParam("examId") String examId){
        List<ClassExam> classExams = classExamService.queryClassExamListByExam(Integer.parseInt(examId));
        for (int i = 0; i < classExams.size(); i++){
            classExamService.deleteClassExam(classExams.get(i).getId());
        }
        List<StudentExam> studentExams = studentExamService.queryStudentExamByExam(Integer.parseInt(examId));
        for (int i = 0; i < studentExams.size(); i++){
            studentExamService.deleteStudentExam(studentExams.get(i).getId());
        }
        List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExam(Integer.parseInt(examId));
        for (int i = 0; i < studentChosens.size(); i++){
            studentChosenService.deleteStudentChosen(studentChosens.get(i).getId());
        }
        List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExam(Integer.parseInt(examId));
        for (int i = 0; i < studentJudgements.size(); i++){
            studentJudgementService.deleteStudentJudgement(studentJudgements.get(i).getId());
        }
        List<Chosen> chosens = chosenService.queryChosenByExam(Integer.parseInt(examId));
        for (int i = 0; i < chosens.size(); i++){
            chosenService.deleteChosen(chosens.get(i).getId());
        }
        List<Judgement> judgements = judgementService.queryJudgementByExam(Integer.parseInt(examId));
        for (int i = 0; i < judgements.size(); i++){
            judgementService.deleteJudgement(judgements.get(i).getId());
        }
        examService.deleteExam(Integer.parseInt(examId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/doExam")
    public ResponseEntity<String> doExam(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject().parse(jsonString);
        int classId = jsonObject.getInteger("classId");
        int examId = jsonObject.getInteger("examId");
        int userId = jsonObject.getInteger("userId");
        JSONArray singleQuestionId = jsonObject.getJSONArray("singleQuestionId");
        JSONArray singleQuestionAnswer = jsonObject.getJSONArray("singleQuestionAnswer");
        JSONArray judgmentQuestionId = jsonObject.getJSONArray("judgmentQuestionId");
        JSONArray judgmentQuestionAnswer = jsonObject.getJSONArray("judgmentQuestionAnswer");
        StudentExam studentExam = studentExamService.queryStudentExamByStudentAndClassAndExam(userId,classId,examId);
        studentExam.setStatus(2);

        int questionCount = singleQuestionId.size()+judgmentQuestionId.size();
        int incorrectCount = 0;
        for (int i = 0; i < singleQuestionId.size(); i++) {
            Chosen chosen = chosenService.queryChosenById(Integer.parseInt(singleQuestionId.get(i).toString()));
            int answer = Integer.parseInt(chosen.getAnswer());
            int studentAnswer = Integer.parseInt(singleQuestionAnswer.get(i).toString());
            int correct = 1;
            if (answer != studentAnswer){
                correct = 0;
                incorrectCount++;
            }
            StudentChosen studentChosen = new StudentChosen();
            studentChosen.setExamId(examId);
            studentChosen.setChosenId(Integer.parseInt(singleQuestionId.get(i).toString()));
            studentChosen.setAnswer(answer);
            studentChosen.setStudentAnswer(studentAnswer);
            studentChosen.setCorrect(correct);
            studentChosen.setClassId(classId);
            studentChosen.setUserId(userId);
            studentChosenService.addStudentChosen(studentChosen);

        }
        for (int i = 0; i < judgmentQuestionId.size(); i++) {
            Judgement judgement =judgementService.queryJudgementById(Integer.parseInt(judgmentQuestionId.get(i).toString()));
            int answer = Integer.parseInt(judgement.getAnswer());
            int studentAnswer = Integer.parseInt(judgmentQuestionAnswer.get(i).toString());
            int correct = 1;
            if (answer != studentAnswer){
                correct = 0;
                incorrectCount++;
            }
            StudentJudgement studentJudgement = new StudentJudgement();
            studentJudgement.setExamId(examId);
            studentJudgement.setJudgementId(Integer.parseInt(judgmentQuestionId.get(i).toString()));
            studentJudgement.setAnswer(answer);
            studentJudgement.setStudentAnswer(studentAnswer);
            studentJudgement.setCorrect(correct);
            studentJudgement.setClassId(classId);
            studentJudgement.setUserId(userId);
            studentJudgementService.addStudentJudgement(studentJudgement);
        }
        int score = 100*(questionCount-incorrectCount)/questionCount;
        studentExam.setScore(score);
        studentExamService.updateStudentExam(studentExam);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @RequestMapping("/studentExam")
    public String studentExam(@RequestParam("classId") String classId,@RequestParam("examId") String examId,Model model){
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        List<User> did = new ArrayList<>();
        List<User> notDid = new ArrayList<>();
        for (int i = 0; i < classStudents.size(); i++){
            StudentExam studentExam = studentExamService.queryStudentExamByStudentAndClassAndExam(classStudents.get(i).getStudentId(),Integer.parseInt(classId),Integer.parseInt(examId));
            if (studentExam.getStatus()==2){
                did.add(userService.queryUserById(studentExam.getStudentId()));
            } else {
                notDid.add(userService.queryUserById(studentExam.getStudentId()));
            }

        }
        model.addAttribute("did",did);
        model.addAttribute("notDid",notDid);
        model.addAttribute("classId",classId);
        model.addAttribute("examId",examId);
        return "studentExam";
    }

    @RequestMapping("/jumpToExamManage")
    public String jumpToExamManage(@RequestParam("examId") String examId){
        Exam exam = examService.queryExamById(Integer.parseInt(examId));
        Course course = courseService.queryCourseById(exam.getCourseId());
        return "redirect:/examManage?teacher="+course.getTeacherId()+"&course="+course.getId();
    }

    @RequestMapping("/reviseExam")
    ResponseEntity<String> reviseExam(@RequestBody String jsonString) throws ParseException {
        JSONObject jsonObject = new JSONObject().parse(jsonString);
        String examName = jsonObject.getString("examName");
        int examTime = jsonObject.getInteger("examTime");
        int examId = jsonObject.getInteger("examId");
        String startTime = jsonObject.getString("startTime").replace("T"," ");
        String endTime = jsonObject.getString("endTime").replace("T"," ");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date start = null;
        Date end = null;
        start = df.parse(startTime);
        end = df.parse(endTime);
        Exam exam = examService.queryExamById(examId);
        exam.setName(examName);
        exam.setMinute(examTime);
        exam.setStartTime(start);
        exam.setEndTime(end);
        examService.updateExam(exam);
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }

}

