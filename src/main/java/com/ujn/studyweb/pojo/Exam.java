package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    private int id;
    private int courseId;
    private int teacherId;
    private String name;
    private Date creatTime;
    private Date startTime;
    private Date endTime;
    private int minute;
}
