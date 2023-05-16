package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chosen {
    private int id;
    private int exerciseId;
    private int examId;
    private String question;
    private String answer;
    private String chosenA;
    private String chosenB;
    private String chosenC;
    private String chosenD;
    private int seq;
}
