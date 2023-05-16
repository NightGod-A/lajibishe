package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassExerciseMapper;
import com.ujn.studyweb.pojo.ClassExercise;
import com.ujn.studyweb.service.IClassExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassExerciseServiceImpl implements IClassExerciseService {

    @Autowired
    private ClassExerciseMapper classExerciseMapper;

    @Override
    public List<ClassExercise> queryClassExerciseList() {
        return classExerciseMapper.queryClassExerciseList();
    }

    @Override
    public List<ClassExercise> queryClassExerciseListByClass(int classId) {
        return classExerciseMapper.queryClassExerciseListByClass(classId);
    }

    @Override
    public List<ClassExercise> queryClassExerciseListByExercise(int exercise) {
        return classExerciseMapper.queryClassExerciseListByExercise(exercise);
    }

    @Override
    public ClassExercise queryClassExerciseByClassAndExercise(int classId, int exerciseId) {
        return classExerciseMapper.queryClassExerciseByClassAndExercise(classId,exerciseId);
    }

    @Override
    public ClassExercise queryClassExerciseById(int id) {
        return classExerciseMapper.queryClassExerciseById(id);
    }

    @Override
    public int addClassExercise(ClassExercise classExercise) {
        return classExerciseMapper.addClassExercise(classExercise);
    }

    @Override
    public int deleteClassExercise(int id) {
        return classExerciseMapper.deleteClassExercise(id);
    }

    @Override
    public int updateClassExercise(ClassExercise classExercise) {
        return classExerciseMapper.updateClassExercise(classExercise);
    }
}
