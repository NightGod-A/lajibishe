package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.StudentExamMapper;
import com.ujn.studyweb.pojo.StudentExam;
import com.ujn.studyweb.service.IStudentExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentExamServiceImpl implements IStudentExamService {

    @Autowired
    private StudentExamMapper studentExamMapper;

    @Override
    public List<StudentExam> queryStudentExam() {
        return studentExamMapper.queryStudentExam();
    }

    @Override
    public StudentExam queryStudentExamById(int id) {
        return studentExamMapper.queryStudentExamById(id);
    }

    @Override
    public List<StudentExam> queryStudentExamByExam(int examId) {
        return studentExamMapper.queryStudentExamByExam(examId);
    }

    @Override
    public List<StudentExam> queryStudentExamByStudentAndClass(int student, int classId) {
        return studentExamMapper.queryStudentExamByStudentAndClass(student,classId);
    }

    @Override
    public StudentExam queryStudentExamByStudentAndClassAndExam(int student, int classId, int examId) {
        return studentExamMapper.queryStudentExamByStudentAndClassAndExam(student,classId,examId);
    }

    @Override
    public int addStudentExam(StudentExam studentExam) {
        return studentExamMapper.addStudentExam(studentExam);
    }

    @Override
    public int deleteStudentExam(int id) {
        return studentExamMapper.deleteStudentExam(id);
    }

    @Override
    public int deleteStudentExamByClassAndExam(int classId, int examId) {
        return studentExamMapper.deleteStudentExamByClassAndExam(classId,examId);
    }

    @Override
    public int updateStudentExam(StudentExam studentExam) {
        return studentExamMapper.updateStudentExam(studentExam);
    }
}
