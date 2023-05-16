package com.ujn.studyweb.controller;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class ExerciseController {

    @Autowired
    private IExerciseService exerciseService;
    @Autowired
    private IJudgementService judgementService;
    @Autowired
    private IChosenService chosenService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IClassExerciseService classExerciseService;
    @Autowired
    private IClassStudentService classStudentService;
    @Autowired
    private IStudentExerciseService studentExerciseService;
    @Autowired
    private IStudentJudgementService studentJudgementService;
    @Autowired
    private IStudentChosenService studentChosenService;
    @Autowired
    private ICourseService courseService;

    @RequestMapping("/exerciseManage")
    public String exerciseManage(@RequestParam("teacher") String teacher, @RequestParam("course") String course,Model model){
        List<Exercise> exercises = exerciseService.queryExerciseByCourse(Integer.parseInt(course));
        model.addAttribute("exercises",exercises);
        model.addAttribute("teacher",teacher);
        model.addAttribute("course",course);
        return "exerciseManage";
    }


    @RequestMapping("/exerciseCreat")
    public String exerciseCreat(@RequestParam("teacher") String teacher, @RequestParam("course") String course,Model model){
        model.addAttribute("teacher",teacher);
        model.addAttribute("course",course);
        return "exerciseCreat";
    }

    @RequestMapping("/addExercise")
    public ResponseEntity<String> addExercise(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject().parse(jsonString);
        String exerciseName = jsonObject.getString("exerciseName");
        String course = jsonObject.getString("course");
        String teacher = jsonObject.getString("teacher");
        JSONArray singleQuestionName = jsonObject.getJSONArray("singleQuestionName");
        JSONArray singleQuestionOptionA = jsonObject.getJSONArray("singleQuestionOptionA");
        JSONArray singleQuestionOptionB = jsonObject.getJSONArray("singleQuestionOptionB");
        JSONArray singleQuestionOptionC = jsonObject.getJSONArray("singleQuestionOptionC");
        JSONArray singleQuestionOptionD = jsonObject.getJSONArray("singleQuestionOptionD");
        JSONArray singleQuestionCheck = jsonObject.getJSONArray("singleQuestionCheck");
        JSONArray judgmentQuestionName = jsonObject.getJSONArray("judgmentQuestionName");
        JSONArray judgmentQuestionCheck = jsonObject.getJSONArray("judgmentQuestionCheck");

        Exercise exercise = new Exercise();
        exercise.setName(exerciseName);
        exercise.setCreatTime(new Date());
        exercise.setCourseId(Integer.parseInt(course));
        exercise.setTeacherId(Integer.parseInt(teacher));
        exerciseService.addExercise(exercise);
        int exerciseId = exercise.getId();
        for (int i = 0; i < singleQuestionName.size(); i++) {
            Chosen chosen = new Chosen();
            chosen.setExerciseId(exerciseId);
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
            judgement.setExerciseId(exerciseId);
            judgement.setSeq(i+1);
            judgement.setQuestion(judgmentQuestionName.get(i).toString());
            judgement.setAnswer(judgmentQuestionCheck.get(i).toString());
            judgementService.addJudgement(judgement);
        }

        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @RequestMapping("/viewExerciseResult")
    public String viewExerciseResult(@RequestParam("user") String id,@RequestParam("exercise") String exercise,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(id));
        StudentExercise studentExercise = studentExerciseService.queryStudentExerciseByStudentAndClassAndExercise(user.getId(),Integer.parseInt(classId),Integer.parseInt(exercise));
        model.addAttribute("studentExercise",studentExercise);
        model.addAttribute("user",user);
        model.addAttribute("classId",classId);
        Exercise exercise1 = exerciseService.queryExerciseById(Integer.parseInt(exercise));
        List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExerciseAndStudentAndClass(Integer.parseInt(exercise),Integer.parseInt(id),Integer.parseInt(classId));
        List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExerciseAndStudentAndClass(Integer.parseInt(exercise),Integer.parseInt(id),Integer.parseInt(classId));

        for (int i = 0; i < studentChosens.size(); i++){
            Chosen chosen = chosenService.queryChosenById(studentChosens.get(i).getChosenId());
            studentChosens.get(i).setChosen(chosen);
        }
        for (int i = 0; i < studentJudgements.size(); i++){
            Judgement judgement = judgementService.queryJudgementById(studentJudgements.get(i).getJudgementId());
            studentJudgements.get(i).setJudgement(judgement);
        }
        model.addAttribute("exerciseId",exercise);
        model.addAttribute("studentChosens",studentChosens);
        model.addAttribute("studentJudgements",studentJudgements);
        model.addAttribute("course",exercise1.getCourseId());
        Course course = courseService.queryCourseById(exercise1.getCourseId());
        model.addAttribute("teacher",course.getTeacherId());
        return "exerciseResult";
    }

    @RequestMapping("/viewExercisePage")
    public String viewExercisePage(@RequestParam("exercise") String exercise,Model model){

        Exercise exercise1 = exerciseService.queryExerciseById(Integer.parseInt(exercise));
        List<Chosen> chosens = chosenService.queryChosenByExercise(Integer.parseInt(exercise));
        List<Judgement> judgements = judgementService.queryJudgementByExercise(Integer.parseInt(exercise));

        model.addAttribute("exerciseId",exercise);
        model.addAttribute("chosens",chosens);
        model.addAttribute("judgements",judgements);
        model.addAttribute("course",exercise1.getCourseId());
        Course course = courseService.queryCourseById(exercise1.getCourseId());
        model.addAttribute("teacher",course.getTeacherId());
        return "exerciseView";
    }


    @RequestMapping("/viewExercise")
    public String viewExercise(@RequestParam("user") String id,@RequestParam("exercise") String exercise,@RequestParam("classId") String classId,Model model){
        User user = userService.queryUserById(Integer.parseInt(id));
        model.addAttribute("user",user);
        model.addAttribute("classId",classId);
        Exercise exercise1 = exerciseService.queryExerciseById(Integer.parseInt(exercise));
        List<Chosen> chosens = chosenService.queryChosenByExercise(Integer.parseInt(exercise));
        List<Judgement> judgements = judgementService.queryJudgementByExercise(Integer.parseInt(exercise));
        model.addAttribute("exerciseId",exercise);
        model.addAttribute("chosens",chosens);
        model.addAttribute("judgements",judgements);
        model.addAttribute("course",exercise1.getCourseId());
        return "exercise";
    }

    @RequestMapping ("/exerciseDistribute")
    public String exerciseDistribute(@RequestParam("exerciseId") String exerciseId,Model model){
        Exercise exercise = exerciseService.queryExerciseById(Integer.parseInt(exerciseId));
        List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByExercise(Integer.parseInt(exerciseId));
        List<Classes> classList = new ArrayList<>();
        for (int i = 0; i <classExercises.size(); i++){
            classList.add(classesService.queryClassesById(classExercises.get(i).getClassId()));
        }
        List<Classes> classesListAll = classesService.queryClassesListByCourse(exercise.getCourseId());
        classesListAll.removeAll(classList);
        model.addAttribute("classesListAll",classesListAll);
        model.addAttribute("classList",classList);
        model.addAttribute("exerciseId",exerciseId);
        return "exerciseClass";
    }

    @RequestMapping("/addExerciseClass")
    public String addExerciseClass(@RequestParam("classId") String classId,@RequestParam("exerciseId") String exerciseId){
        ClassExercise classExercise = new ClassExercise();
        classExercise.setClassId(Integer.parseInt(classId));
        classExercise.setExerciseId(Integer.parseInt(exerciseId));
        classExerciseService.addClassExercise(classExercise);
        List<ClassStudent> classStudent = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        for (int i = 0; i <classStudent.size(); i++){
            StudentExercise studentExercise = new StudentExercise();
            studentExercise.setExerciseId(Integer.parseInt(exerciseId));
            studentExercise.setStudentId(classStudent.get(i).getStudentId());
            studentExercise.setStatus(0);
            studentExercise.setClassId(Integer.parseInt(classId));
            studentExerciseService.addStudentExercise(studentExercise);
        }
        return "redirect:/exerciseDistribute?exerciseId="+exerciseId;
    }

    @RequestMapping("/deleteExerciseClass")
    public ResponseEntity<String> deleteExerciseClass(@RequestParam("classId") String classId,@RequestParam("exerciseId") String exerciseId){
        ClassExercise classExercise = classExerciseService.queryClassExerciseByClassAndExercise(Integer.parseInt(classId),Integer.parseInt(exerciseId));
        classExerciseService.deleteClassExercise(classExercise.getId());
        studentExerciseService.deleteStudentExerciseByClassAndExercise(Integer.parseInt(classId),Integer.parseInt(exerciseId));
        studentJudgementService.deleteStudentJudgementByClassAndExercise(Integer.parseInt(classId),Integer.parseInt(exerciseId));
        studentChosenService.deleteStudentChosenByClassAndExercise(Integer.parseInt(classId),Integer.parseInt(exerciseId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/deleteExercise")
    public ResponseEntity<String> deleteExercise(@RequestParam("exerciseId") String exerciseId){
        List<ClassExercise> classExercises = classExerciseService.queryClassExerciseListByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < classExercises.size(); i++){
            classExerciseService.deleteClassExercise(classExercises.get(i).getId());
        }
        List<StudentExercise> studentExercises = studentExerciseService.queryStudentExerciseByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < studentExercises.size(); i++){
            studentExerciseService.deleteStudentExercise(studentExercises.get(i).getId());
        }
        List<StudentChosen> studentChosens = studentChosenService.queryStudentChosenByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < studentChosens.size(); i++){
            studentChosenService.deleteStudentChosen(studentChosens.get(i).getId());
        }
        List<StudentJudgement> studentJudgements = studentJudgementService.queryStudentJudgementByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < studentJudgements.size(); i++){
            studentJudgementService.deleteStudentJudgement(studentJudgements.get(i).getId());
        }
        List<Chosen> chosens = chosenService.queryChosenByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < chosens.size(); i++){
            chosenService.deleteChosen(chosens.get(i).getId());
        }
        List<Judgement> judgements = judgementService.queryJudgementByExercise(Integer.parseInt(exerciseId));
        for (int i = 0; i < judgements.size(); i++){
            judgementService.deleteJudgement(judgements.get(i).getId());
        }
        exerciseService.deleteExercise(Integer.parseInt(exerciseId));
        return new ResponseEntity<>("删除成功", HttpStatus.OK);
    }

    @RequestMapping("/doExercise")
    public ResponseEntity<String> doExercise(@RequestBody String jsonString) {

        JSONObject jsonObject = new JSONObject().parse(jsonString);
        int classId = jsonObject.getInteger("classId");
        int exerciseId = jsonObject.getInteger("exerciseId");
        int userId = jsonObject.getInteger("userId");
        JSONArray singleQuestionId = jsonObject.getJSONArray("singleQuestionId");
        JSONArray singleQuestionAnswer = jsonObject.getJSONArray("singleQuestionAnswer");
        JSONArray judgmentQuestionId = jsonObject.getJSONArray("judgmentQuestionId");
        JSONArray judgmentQuestionAnswer = jsonObject.getJSONArray("judgmentQuestionAnswer");
        StudentExercise studentExercise = studentExerciseService.queryStudentExerciseByStudentAndClassAndExercise(userId,classId,exerciseId);
        studentExercise.setStatus(1);
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
            studentChosen.setExerciseId(exerciseId);
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
            studentJudgement.setExerciseId(exerciseId);
            studentJudgement.setJudgementId(Integer.parseInt(judgmentQuestionId.get(i).toString()));
            studentJudgement.setAnswer(answer);
            studentJudgement.setStudentAnswer(studentAnswer);
            studentJudgement.setCorrect(correct);
            studentJudgement.setClassId(classId);
            studentJudgement.setUserId(userId);
            studentJudgementService.addStudentJudgement(studentJudgement);
        }
        int score = 100*(questionCount-incorrectCount)/questionCount;
        studentExercise.setScore(score);
        studentExerciseService.updateStudentExercise(studentExercise);
        return new ResponseEntity<>("修改成功", HttpStatus.OK);
    }

    @RequestMapping("/studentExercise")
    public String studentExercise(@RequestParam("classId") String classId,@RequestParam("exerciseId") String exerciseId,Model model){
        List<ClassStudent> classStudents = classStudentService.queryClassStudentListByClass(Integer.parseInt(classId));
        List<User> did = new ArrayList<>();
        List<User> notDid = new ArrayList<>();
        for (int i = 0; i < classStudents.size(); i++){
            StudentExercise studentExercise = studentExerciseService.queryStudentExerciseByStudentAndClassAndExercise(classStudents.get(i).getStudentId(),Integer.parseInt(classId),Integer.parseInt(exerciseId));
            if (studentExercise.getStatus()==1){
                did.add(userService.queryUserById(studentExercise.getStudentId()));
            } else {
                notDid.add(userService.queryUserById(studentExercise.getStudentId()));
            }

        }
        model.addAttribute("did",did);
        model.addAttribute("notDid",notDid);
        model.addAttribute("classId",classId);
        model.addAttribute("exerciseId",exerciseId);
        return "studentExercise";
    }

    @RequestMapping("/jumpToExerciseManage")
    public String jumpToExerciseManage(@RequestParam("exerciseId") String exerciseId){
        Exercise exercise = exerciseService.queryExerciseById(Integer.parseInt(exerciseId));
        Course course = courseService.queryCourseById(exercise.getCourseId());
        return "redirect:/exerciseManage?teacher="+course.getTeacherId()+"&course="+course.getId();
    }

    @RequestMapping("/reviseExercise")
    ResponseEntity<String> reviseExercise(@RequestParam("exerciseId") String exerciseId,@RequestParam("exerciseName") String exerciseName){
        Exercise exercise = exerciseService.queryExerciseById(Integer.parseInt(exerciseId));
        exercise.setName(exerciseName);
        exerciseService.updateExercise(exercise);
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }

}
