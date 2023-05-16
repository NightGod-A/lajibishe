package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.ClassExercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassExerciseMapper {
    List<ClassExercise> queryClassExerciseList();
    List<ClassExercise> queryClassExerciseListByClass(int classId);
    List<ClassExercise> queryClassExerciseListByExercise(int exercise);
    ClassExercise queryClassExerciseById(int id);
    ClassExercise queryClassExerciseByClassAndExercise(int classId,int exerciseId);
    int addClassExercise(ClassExercise classExercise);
    int deleteClassExercise(int id);
    int updateClassExercise(ClassExercise classExercise);
}
