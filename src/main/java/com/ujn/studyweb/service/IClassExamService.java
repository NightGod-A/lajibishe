package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.ClassExam;

import java.util.List;

public interface IClassExamService {
    List<ClassExam> queryClassExamList();
    List<ClassExam> queryClassExamListByClass(int classId);
    List<ClassExam> queryClassExamListByExam(int exam);
    ClassExam queryClassExamById(int id);
    ClassExam queryClassExamByClassAndExam(int classId,int examId);
    int addClassExam(ClassExam classExam);
    int deleteClassExam(int id);
    int updateClassExam(ClassExam classExam);
}
