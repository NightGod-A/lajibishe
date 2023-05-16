package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentChosen {
    private int id;
    private int exerciseId;
    private int examId;
    private int chosenId;
    private int userId;
    private int answer;
    private int studentAnswer;
    private int correct;
    private int classId;
    private Chosen chosen;
}
