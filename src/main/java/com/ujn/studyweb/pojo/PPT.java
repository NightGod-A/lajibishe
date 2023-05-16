package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PPT {
    private int id;
    private int courseId;
    private String pptName;
    private String ppt;
    private int pageNum;
    private Date creatTime;
}
