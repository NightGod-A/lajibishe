package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.StudentExercise;
import com.ujn.studyweb.pojo.StudentVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface StudentVideoMapper {
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
