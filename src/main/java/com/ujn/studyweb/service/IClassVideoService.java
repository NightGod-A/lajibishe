package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.ClassVideo;

import java.util.List;

public interface IClassVideoService {
    List<ClassVideo> queryClassVideoList();
    List<ClassVideo> queryClassVideoListByClass(int classId);
    ClassVideo queryClassVideoByClassAndVideo(int classId,int videoId);
    List<ClassVideo> queryClassVideoListByVideo(int video);
    ClassVideo queryClassVideoById(int id);
    int addClassVideo(ClassVideo classVideo);
    int deleteClassVideo(int id);
    int updateClassVideo(ClassVideo classVideo);
}
