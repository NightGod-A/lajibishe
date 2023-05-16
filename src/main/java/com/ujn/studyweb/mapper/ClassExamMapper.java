package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.ClassExam;
import com.ujn.studyweb.pojo.ClassExercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassExamMapper {
    List<ClassExam> queryClassExamList();
    List<ClassExam> queryClassExamListByClass(int classId);
    List<ClassExam> queryClassExamListByExam(int exam);
    ClassExam queryClassExamById(int id);
    ClassExam queryClassExamByClassAndExam(int classId,int examId);
    int addClassExam(ClassExam classExam);
    int deleteClassExam(int id);
    int updateClassExam(ClassExam classExam);
}
