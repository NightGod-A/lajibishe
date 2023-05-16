package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.StudentExam;

import java.util.List;

public interface IStudentExamService {
    List<StudentExam> queryStudentExam();
    StudentExam queryStudentExamById(int id);
    List<StudentExam> queryStudentExamByExam(int examId);
    List<StudentExam> queryStudentExamByStudentAndClass(int student,int classId);
    StudentExam queryStudentExamByStudentAndClassAndExam(int student,int classId,int examId);
    int addStudentExam(StudentExam studentExam);
    int deleteStudentExam(int id);
    int deleteStudentExamByClassAndExam(int classId,int examId);
    int updateStudentExam(StudentExam studentExam);
}
