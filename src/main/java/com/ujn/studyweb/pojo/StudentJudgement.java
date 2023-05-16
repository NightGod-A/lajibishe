package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentJudgement {
    private int id;
    private int exerciseId;
    private int examId;
    private int judgementId;
    private int userId;
    private int answer;
    private int studentAnswer;
    private int correct;
    private int classId;
    private Judgement judgement;
}
