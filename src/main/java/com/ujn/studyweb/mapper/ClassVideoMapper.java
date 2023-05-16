package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.ClassExercise;
import com.ujn.studyweb.pojo.ClassVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassVideoMapper {
    List<ClassVideo> queryClassVideoList();
    List<ClassVideo> queryClassVideoListByClass(int classId);
    ClassVideo queryClassVideoByClassAndVideo(int classId,int videoId);
    List<ClassVideo> queryClassVideoListByVideo(int video);
    ClassVideo queryClassVideoById(int id);
    int addClassVideo(ClassVideo classVideo);
    int deleteClassVideo(int id);
    int updateClassVideo(ClassVideo classVideo);
}
