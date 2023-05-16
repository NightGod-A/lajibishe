package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.VideoMapper;
import com.ujn.studyweb.pojo.Video;
import com.ujn.studyweb.service.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    VideoMapper videoMapper;

    @Override
    public List<Video> queryVideoList() {
        return videoMapper.queryVideoList();
    }

    @Override
    public Video queryVideoById(int id) {
        return videoMapper.queryVideoById(id);
    }

    @Override
    public List<Video> queryVideoByTeacher(int teacher) {
        return videoMapper.queryVideoByTeacher(teacher);
    }

    @Override
    public List<Video> queryVideoByCourse(int course) {
        return videoMapper.queryVideoByCourse(course);
    }

    @Override
    public int addVideo(Video video) {
        return videoMapper.addVideo(video);
    }

    @Override
    public int deleteVideo(int id) {
        return videoMapper.deleteVideo(id);
    }

    @Override
    public int updateVideo(Video video) {
        return videoMapper.updateVideo(video);
    }
}
