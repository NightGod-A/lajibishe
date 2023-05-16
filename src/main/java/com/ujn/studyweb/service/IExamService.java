package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Exam;

import java.util.List;

public interface IExamService {
    List<Exam> queryExamList();
    Exam queryExamById(int id);
    List<Exam> queryExamByTeacher(int teacher);
    List<Exam> queryExamByCourse(int course);
    int addExam(Exam exam);
    int deleteExam(int id);
    int updateExam(Exam exam);
}
