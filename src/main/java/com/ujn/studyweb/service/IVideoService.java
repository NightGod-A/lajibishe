package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Video;

import java.util.List;

public interface IVideoService {
    List<Video> queryVideoList();
    Video queryVideoById(int id);
    List<Video> queryVideoByTeacher(int teacher);
    List<Video> queryVideoByCourse(int course);
    int addVideo(Video video);
    int deleteVideo(int id);
    int updateVideo(Video video);
}
