package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.ClassStudent;

import java.util.List;

public interface IClassStudentService {
    List<ClassStudent> queryClassStudentList();
    List<ClassStudent> queryClassStudentListByClass(int classId);
    List<ClassStudent> queryClassStudentListByStudent(int student);
    ClassStudent queryClassStudentById(int id);
    int addClassStudent(ClassStudent classStudent);
    int deleteClassStudent(int id);
    int updateClassStudent(ClassStudent classStudent);
}
