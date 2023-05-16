package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
    private int id;
    private int courseId;
    private Course course;
    private String name;
    private Date creatTime;
}
