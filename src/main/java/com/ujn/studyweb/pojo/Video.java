package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    private int id;
    private int isEnable;
    private int teacherId;
    private int courseId;
    private String name;
    private String video;
    private String picture;
    private Date creatTime;
}
