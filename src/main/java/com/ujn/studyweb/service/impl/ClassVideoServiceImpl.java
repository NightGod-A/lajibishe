package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassVideoMapper;
import com.ujn.studyweb.pojo.ClassVideo;
import com.ujn.studyweb.service.IClassVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassVideoServiceImpl implements IClassVideoService {

    @Autowired
    private ClassVideoMapper classVideoMapper;

    @Override
    public List<ClassVideo> queryClassVideoList() {
        return classVideoMapper.queryClassVideoList();
    }

    @Override
    public List<ClassVideo> queryClassVideoListByClass(int classId) {
        return classVideoMapper.queryClassVideoListByClass(classId);
    }

    @Override
    public ClassVideo queryClassVideoByClassAndVideo(int classId, int videoId) {
        return classVideoMapper.queryClassVideoByClassAndVideo(classId,videoId);
    }

    @Override
    public List<ClassVideo> queryClassVideoListByVideo(int video) {
        return classVideoMapper.queryClassVideoListByVideo(video);
    }

    @Override
    public ClassVideo queryClassVideoById(int id) {
        return classVideoMapper.queryClassVideoById(id);
    }

    @Override
    public int addClassVideo(ClassVideo classVideo) {
        return classVideoMapper.addClassVideo(classVideo);
    }

    @Override
    public int deleteClassVideo(int id) {
        return classVideoMapper.deleteClassVideo(id);
    }

    @Override
    public int updateClassVideo(ClassVideo classVideo) {
        return classVideoMapper.updateClassVideo(classVideo);
    }
}
