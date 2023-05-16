package com.ujn.studyweb.mapper;


import com.ujn.studyweb.pojo.StudentJudgement;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentJudgementMapper {
    List<StudentJudgement> queryStudentJudgement();
    StudentJudgement queryStudentJudgementById(int id);
    List<StudentJudgement> queryStudentJudgementByStudentAndClass(int student,int classId);
    List<StudentJudgement> queryStudentJudgementByExerciseAndStudentAndClass(int exercise,int student,int classId);
    List<StudentJudgement> queryStudentJudgementByExamAndStudentAndClass(int exam,int student,int classId);
    List<StudentJudgement> queryStudentJudgementByExercise(int exercise);
    List<StudentJudgement> queryStudentJudgementByExam(int exam);
    List<StudentJudgement> queryStudentJudgementByStudent(int student);
    int addStudentJudgement(StudentJudgement studentJudgement);
    int deleteStudentJudgement(int id);
    int deleteStudentJudgementByClassAndExercise(int classId,int exerciseId);
    int deleteStudentJudgementByClassAndExam(int classId,int examId);
    int updateStudentJudgement(StudentJudgement studentJudgement);
}
