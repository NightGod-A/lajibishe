package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.ClassStudent;
import com.ujn.studyweb.pojo.ClassVideo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassStudentMapper {
    List<ClassStudent> queryClassStudentList();
    List<ClassStudent> queryClassStudentListByClass(int classId);
    List<ClassStudent> queryClassStudentListByStudent(int student);
    ClassStudent queryClassStudentById(int id);
    int addClassStudent(ClassStudent classStudent);
    int deleteClassStudent(int id);
    int updateClassStudent(ClassStudent classStudent);
}
