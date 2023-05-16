package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Video;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VideoMapper {
    List<Video> queryVideoList();
    Video queryVideoById(int id);
    List<Video> queryVideoByTeacher(int teacher);
    List<Video> queryVideoByCourse(int course);
    int addVideo(Video video);
    int deleteVideo(int id);
    int updateVideo(Video video);
}
