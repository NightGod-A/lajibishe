package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.StudentVideoMapper;
import com.ujn.studyweb.pojo.StudentVideo;
import com.ujn.studyweb.service.IStudentVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentVideoServiceImpl implements IStudentVideoService {

    @Autowired
    private StudentVideoMapper studentVideoMapper;

    @Override
    public List<StudentVideo> queryStudentVideo() {
        return studentVideoMapper.queryStudentVideo();
    }

    @Override
    public StudentVideo queryStudentVideoById(int id) {
        return studentVideoMapper.queryStudentVideoById(id);
    }

    @Override
    public List<StudentVideo> queryStudentVideoByStudentAndClass(int student, int classId) {
        return studentVideoMapper.queryStudentVideoByStudentAndClass(student,classId);
    }

    @Override
    public List<StudentVideo> queryStudentVideoByVideo(int videoId) {
        return studentVideoMapper.queryStudentVideoByVideo(videoId);
    }

    @Override
    public StudentVideo queryStudentVideoByStudentAndClassAndVideo(int student, int classId, int videoId) {
        return studentVideoMapper.queryStudentVideoByStudentAndClassAndVideo(student,classId,videoId);
    }

    @Override
    public int addStudentVideo(StudentVideo studentVideo) {
        return studentVideoMapper.addStudentVideo(studentVideo);
    }

    @Override
    public int deleteStudentVideo(int id) {
        return studentVideoMapper.deleteStudentVideo(id);
    }

    @Override
    public int deleteStudentVideoByClassAndVideo(int classId, int videoId) {
        return studentVideoMapper.deleteStudentVideoByClassAndVideo(classId,videoId);
    }

    @Override
    public int updateStudentVideo(StudentVideo studentVideo) {
        return studentVideoMapper.updateStudentVideo(studentVideo);
    }
}
