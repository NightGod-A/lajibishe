package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ExamMapper;
import com.ujn.studyweb.pojo.Exam;
import com.ujn.studyweb.service.IExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ExamServiceImpl implements IExamService {

    @Autowired
    private ExamMapper examMapper;

    @Override
    public List<Exam> queryExamList() {
        return examMapper.queryExamList();
    }

    @Override
    public Exam queryExamById(int id) {
        return examMapper.queryExamById(id);
    }

    @Override
    public List<Exam> queryExamByTeacher(int teacher) {
        return examMapper.queryExamByTeacher(teacher);
    }

    @Override
    public List<Exam> queryExamByCourse(int course) {
        return examMapper.queryExamByCourse(course);
    }

    @Override
    public int addExam(Exam exam) {
        return examMapper.addExam(exam);
    }

    @Override
    public int deleteExam(int id) {
        return examMapper.deleteExam(id);
    }

    @Override
    public int updateExam(Exam exam) {
        return examMapper.updateExam(exam);
    }
}
