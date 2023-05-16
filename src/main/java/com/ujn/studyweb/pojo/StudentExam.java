package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentExam {
    private int id;
    private int examId;
    private int classId;
    private int studentId;
    private int status;
    private int score;
    private Date startTime;
    private Exam exam;
}
