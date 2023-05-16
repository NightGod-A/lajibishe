package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.StudentExam;
import com.ujn.studyweb.pojo.StudentExercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface StudentExamMapper {
    List<StudentExam> queryStudentExam();
    StudentExam queryStudentExamById(int id);
    List<StudentExam> queryStudentExamByStudentAndClass(int student,int classId);
    StudentExam queryStudentExamByStudentAndClassAndExam(int student,int classId,int examId);
    List<StudentExam> queryStudentExamByExam(int examId);
    int addStudentExam(StudentExam studentExam);
    int deleteStudentExam(int id);
    int deleteStudentExamByClassAndExam(int classId,int examId);
    int updateStudentExam(StudentExam studentExam);
}
