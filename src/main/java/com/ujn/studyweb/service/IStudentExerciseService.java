package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.StudentExercise;

import java.util.List;

public interface IStudentExerciseService {
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
