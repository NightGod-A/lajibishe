package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.StudentChosen;
import com.ujn.studyweb.pojo.StudentExercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentExerciseMapper {
    List<StudentExercise> queryStudentExercise();
    StudentExercise queryStudentExerciseById(int id);
    List<StudentExercise> queryStudentExerciseByStudentAndClass(int student,int classId);
    StudentExercise queryStudentExerciseByStudentAndClassAndExercise(int student,int classId,int exerciseId);
    List<StudentExercise> queryStudentExerciseByExercise(int exerciseId);

    int addStudentExercise(StudentExercise studentExercise);
    int deleteStudentExercise(int id);
    int deleteStudentExerciseByClassAndExercise(int classId,int exerciseId);
    int updateStudentExercise(StudentExercise studentExercise);
}
