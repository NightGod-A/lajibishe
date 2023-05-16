package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    private int id;
    private int isEnable;
    private int teacherId;
    private String description;
    private String name;
    private String picture;
    private User teacher;
    private Date creatTime;
}
