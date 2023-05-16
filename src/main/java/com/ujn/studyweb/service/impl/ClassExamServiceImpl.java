package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassExamMapper;
import com.ujn.studyweb.pojo.ClassExam;
import com.ujn.studyweb.service.IClassExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassExamServiceImpl implements IClassExamService {

    @Autowired
    private ClassExamMapper classExamMapper;

    @Override
    public List<ClassExam> queryClassExamList() {
        return classExamMapper.queryClassExamList();
    }

    @Override
    public List<ClassExam> queryClassExamListByClass(int classId) {
        return classExamMapper.queryClassExamListByClass(classId);
    }

    @Override
    public List<ClassExam> queryClassExamListByExam(int exam) {
        return classExamMapper.queryClassExamListByExam(exam);
    }

    @Override
    public ClassExam queryClassExamById(int id) {
        return classExamMapper.queryClassExamById(id);
    }

    @Override
    public ClassExam queryClassExamByClassAndExam(int classId, int examId) {
        return classExamMapper.queryClassExamByClassAndExam(classId,examId);
    }

    @Override
    public int addClassExam(ClassExam classExam) {
        return classExamMapper.addClassExam(classExam);
    }

    @Override
    public int deleteClassExam(int id) {
        return classExamMapper.deleteClassExam(id);
    }

    @Override
    public int updateClassExam(ClassExam classExam) {
        return classExamMapper.updateClassExam(classExam);
    }
}
