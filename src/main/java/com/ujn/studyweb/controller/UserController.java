package com.ujn.studyweb.controller;

import com.alibaba.fastjson2.JSONObject;
import com.ujn.studyweb.pojo.*;
import com.ujn.studyweb.service.IClassStudentService;
import com.ujn.studyweb.service.IClassesService;
import com.ujn.studyweb.service.ICourseService;
import com.ujn.studyweb.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICourseService courseService;
    @Autowired
    private IClassesService classesService;
    @Autowired
    private IClassStudentService classStudentService;


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/register")
    public String register(){
        return "register";
    }

    @RequestMapping("/jumpToTeacher")
    public String jumpToTeacher(String id,Model model){
        User user = userService.queryUserById(Integer.parseInt(id));
        model.addAttribute("user", user);
        List<Course> courses = courseService.queryCourseByTeacher(user.getId());
        model.addAttribute("courses", courses);
        return "teacher";
    }

    @RequestMapping("/jumpToTeacherByCourse")
    public String jumpToTeacherByCourse(String course,Model model){
        Course course1 = courseService.queryCourseById(Integer.parseInt(course));
        User user = userService.queryUserById(course1.getTeacherId());
        model.addAttribute("user", user);
        List<Course> courses = courseService.queryCourseByTeacher(user.getId());
        model.addAttribute("courses", courses);
        return "teacher";
    }

    @RequestMapping("/jumpToTeacherByClass")
    public String jumpToTeacherByClass(String classId,Model model){
        Classes classes = classesService.queryClassesById(Integer.parseInt(classId));
        Course course1 = courseService.queryCourseById(classes.getCourseId());
        User user = userService.queryUserById(course1.getTeacherId());
        model.addAttribute("user", user);
        List<Course> courses = courseService.queryCourseByTeacher(user.getId());
        model.addAttribute("courses", courses);
        return "teacher";
    }

    @RequestMapping("/loginCheck")
    public String loginCheck(String phone, String password, String type, Model model, HttpSession session){
        User user = userService.queryUserByPhone(phone);
        int userType;
        if (type.equals("student"))
            userType = 1;
        else
            userType = 0;
        String password1 = DigestUtils.md5Hex(password);
        if (user != null){
            if (password1.equals(user.getPassword()) && userType == user.getType()){
                session.setAttribute("loginUser",phone);
                if (userType == 0) {
                    model.addAttribute("user", user);
                    List<Course> courses = courseService.queryCourseByTeacher(user.getId());
                    model.addAttribute("courses", courses);
                    return "teacher";
                }
                else {
                    model.addAttribute("user", user);
                    List<ClassStudent> classStudents = classStudentService.queryClassStudentListByStudent(user.getId());
                    List<Classes> classesList = new ArrayList<>();
                    for (int i = 0; i < classStudents.size(); i++){
                        if (classStudents.size() == 0){
                            break;
                        }
                        classesList.add(classesService.queryClassesById(classStudents.get(i).getClassId()));
                    }

                    for (int i = 0; i<classesList.size(); i++){
                        if (classesList.size() == 0){
                            break;
                        }
                        int courseId = classesList.get(i).getCourseId();
                        Course course = courseService.queryCourseById(courseId);
                        course.setTeacher(userService.queryUserById(course.getTeacherId()));
                        classesList.get(i).setCourse(course);
                    }
                    model.addAttribute("classesList", classesList);
                    return "student";
                }
            }
        }
        model.addAttribute("msg","手机号或密码错误");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";
    }

    @RequestMapping("/reviseInformation")
    public ResponseEntity<String> reviseInformation(@RequestParam("username") String username,@RequestParam("phone") String phone,@RequestParam("userId") String userId){
        User user = userService.queryUserByPhone(phone);
        if (user != null){
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user1 = userService.queryUserById(Integer.parseInt(userId));
        user1.setName(username);
        user1.setPhone(phone);
        userService.updateUser(user1);
        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

    @RequestMapping("/revisePassword")
    public ResponseEntity<String> revisePassword(@RequestParam("newPassword") String newPassword,@RequestParam("lastPassword") String lastPassword,@RequestParam("userId") String userId){
        User user1 = userService.queryUserById(Integer.parseInt(userId));
        String password1 = DigestUtils.md5Hex(lastPassword);
        if (!user1.getPassword().equals(password1)){
            return new ResponseEntity<>("文件上传失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String password2 = DigestUtils.md5Hex(newPassword);
        user1.setPassword(password2);
        userService.updateUser(user1);
        return new ResponseEntity<>("文件上传成功", HttpStatus.OK);
    }

    @RequestMapping("/registerAccount")
    public String registerAccount(String name,String phone,String password,String user_type,Model model){
        User user = userService.queryUserByPhone(phone);
        if (user != null){
            model.addAttribute("msg","手机号已存在");
            return "register";
        }
        String password1 = DigestUtils.md5Hex(password);
        User user1 = new User();
        user1.setPassword(password1);
        user1.setGender(1);
        user1.setName(name);
        user1.setPhone(phone);
        if (user_type.equals("student")){
            user1.setType(1);
        }else {
            user1.setType(0);
        }
        userService.addUser(user1);
        return "login";
    }

    @RequestMapping("/reviseStudent")
    ResponseEntity<String> reviseStudent(@RequestBody String jsonString) throws ParseException {
        JSONObject jsonObject = new JSONObject().parse(jsonString);
        String studentPhone = jsonObject.getString("studentPhone");
        String studentName = jsonObject.getString("studentName");
        String studentPassword = jsonObject.getString("studentPassword");
        int studentId = jsonObject.getInteger("studentId");
        User user = userService.queryUserById(studentId);
        User user1 = userService.queryUserByPhone(studentPhone);
        if (user1 != null){
            return new ResponseEntity<>("修改失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        user.setPassword(studentPassword);
        user.setName(studentName);
        user.setPhone(studentPhone);
        userService.updateUser(user);
        return new ResponseEntity<>("成功", HttpStatus.OK);
    }

}
