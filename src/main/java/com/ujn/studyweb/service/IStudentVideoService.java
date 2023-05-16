package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.StudentVideo;

import java.util.List;

public interface IStudentVideoService {
    List<StudentVideo> queryStudentVideo();
    StudentVideo queryStudentVideoById(int id);
    List<StudentVideo> queryStudentVideoByStudentAndClass(int student,int classId);
    List<StudentVideo> queryStudentVideoByVideo(int videoId);
    StudentVideo queryStudentVideoByStudentAndClassAndVideo(int student,int classId,int videoId);
    int addStudentVideo(StudentVideo studentVideo);
    int deleteStudentVideo(int id);
    int deleteStudentVideoByClassAndVideo(int classId,int videoId);
    int updateStudentVideo(StudentVideo studentVideo);
}
