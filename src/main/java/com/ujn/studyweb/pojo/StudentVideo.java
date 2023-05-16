package com.ujn.studyweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentVideo {
    private int id;
    private int videoId;
    private int classId;
    private int studentId;
    private int status;
    private Video video;
}
