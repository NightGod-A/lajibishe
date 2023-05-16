package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.ClassExercise;

import java.util.List;

public interface IClassExerciseService {
    List<ClassExercise> queryClassExerciseList();
    List<ClassExercise> queryClassExerciseListByClass(int classId);
    List<ClassExercise> queryClassExerciseListByExercise(int exercise);
    ClassExercise queryClassExerciseByClassAndExercise(int classId,int exerciseId);
    ClassExercise queryClassExerciseById(int id);
    int addClassExercise(ClassExercise classExercise);
    int deleteClassExercise(int id);
    int updateClassExercise(ClassExercise classExercise);
}
