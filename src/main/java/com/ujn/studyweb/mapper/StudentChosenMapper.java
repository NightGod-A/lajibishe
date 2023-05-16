package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.StudentChosen;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentChosenMapper {
    List<StudentChosen> queryStudentChosen();
    StudentChosen queryStudentChosenById(int id);
    List<StudentChosen> queryStudentChosenByStudentAndClass(int student,int classId);
    List<StudentChosen> queryStudentChosenByExerciseAndStudentAndClass(int exercise,int student,int classId);
    List<StudentChosen> queryStudentChosenByExamAndStudentAndClass(int exam,int student,int classId);
    List<StudentChosen> queryStudentChosenByExercise(int exercise);
    List<StudentChosen> queryStudentChosenByExam(int exam);
    List<StudentChosen> queryStudentChosenByStudent(int student);
    int addStudentChosen(StudentChosen studentChosen);
    int deleteStudentChosen(int id);
    int deleteStudentChosenByClassAndExercise(int classId,int exerciseId);
    int deleteStudentChosenByClassAndExam(int classId,int examId);
    int updateStudentChosen(StudentChosen studentChosen);
}
