package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExercise {
    private int id;
    private int exerciseId;
    private int classId;
    private int studentId;
    private int status;
    private Exercise exercise;
    private int score;
}
