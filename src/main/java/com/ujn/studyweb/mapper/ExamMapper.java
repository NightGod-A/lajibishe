package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Exam;
import com.ujn.studyweb.pojo.Exercise;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ExamMapper {
    List<Exam> queryExamList();
    Exam queryExamById(int id);
    List<Exam> queryExamByTeacher(int teacher);
    List<Exam> queryExamByCourse(int course);
    int addExam(Exam exam);
    int deleteExam(int id);
    int updateExam(Exam exam);
}
