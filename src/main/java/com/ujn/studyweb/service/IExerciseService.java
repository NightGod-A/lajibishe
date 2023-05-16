package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Exercise;

import java.util.List;

public interface IExerciseService {
    List<Exercise> queryExerciseList();
    Exercise queryExerciseById(int id);
    List<Exercise> queryExerciseByTeacher(int teacher);
    List<Exercise> queryExerciseByCourse(int course);
    int addExercise(Exercise exercise);
    int deleteExercise(int id);
    int updateExercise(Exercise exercise);
}
