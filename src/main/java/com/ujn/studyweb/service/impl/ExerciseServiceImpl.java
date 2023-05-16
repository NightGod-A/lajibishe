package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ExerciseMapper;
import com.ujn.studyweb.pojo.Exercise;
import com.ujn.studyweb.service.IExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements IExerciseService {

    @Autowired
    ExerciseMapper exerciseMapper;


    @Override
    public List<Exercise> queryExerciseList() {
        return exerciseMapper.queryExerciseList();
    }

    @Override
    public Exercise queryExerciseById(int id) {
        return exerciseMapper.queryExerciseById(id);
    }

    @Override
    public List<Exercise> queryExerciseByTeacher(int teacher) {
        return exerciseMapper.queryExerciseByTeacher(teacher);
    }

    @Override
    public List<Exercise> queryExerciseByCourse(int course) {
        return exerciseMapper.queryExerciseByCourse(course);
    }

    @Override
    public int addExercise(Exercise exercise) {
        return exerciseMapper.addExercise(exercise);
    }

    @Override
    public int deleteExercise(int id) {
        return exerciseMapper.deleteExercise(id);
    }

    @Override
    public int updateExercise(Exercise exercise) {
        return exerciseMapper.updateExercise(exercise);
    }
}
