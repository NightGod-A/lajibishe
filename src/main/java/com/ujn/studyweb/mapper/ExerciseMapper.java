package com.ujn.studyweb.mapper;


import com.ujn.studyweb.pojo.Exercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExerciseMapper {
    List<Exercise> queryExerciseList();
    Exercise queryExerciseById(int id);
    List<Exercise> queryExerciseByTeacher(int teacher);
    List<Exercise> queryExerciseByCourse(int course);
    int addExercise(Exercise exercise);
    int deleteExercise(int id);
    int updateExercise(Exercise exercise);
}
