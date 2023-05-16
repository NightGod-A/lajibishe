package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Judgement {
    private int id;
    private int exerciseId;
    private int examId;
    private String question;
    private String answer;
    private int seq;
}
