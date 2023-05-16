package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.StudentJudgementMapper;
import com.ujn.studyweb.pojo.StudentJudgement;
import com.ujn.studyweb.service.IStudentJudgementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentJudgementServiceImpl implements IStudentJudgementService {

    @Autowired
    private StudentJudgementMapper studentJudgementMapper;

    @Override
    public List<StudentJudgement> queryStudentJudgement() {
        return studentJudgementMapper.queryStudentJudgement();
    }

    @Override
    public StudentJudgement queryStudentJudgementById(int id) {
        return studentJudgementMapper.queryStudentJudgementById(id);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByStudentAndClass(int student,int classId) {
        return studentJudgementMapper.queryStudentJudgementByStudentAndClass(student,classId);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByExerciseAndStudentAndClass(int exercise, int student, int classId) {
        return studentJudgementMapper.queryStudentJudgementByExerciseAndStudentAndClass(exercise,student,classId);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByExamAndStudentAndClass(int exam, int student, int classId) {
        return studentJudgementMapper.queryStudentJudgementByExamAndStudentAndClass(exam,student,classId);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByExercise(int exercise) {
        return studentJudgementMapper.queryStudentJudgementByExercise(exercise);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByExam(int exam) {
        return studentJudgementMapper.queryStudentJudgementByExam(exam);
    }

    @Override
    public List<StudentJudgement> queryStudentJudgementByStudent(int student) {
        return studentJudgementMapper.queryStudentJudgementByStudent(student);
    }

    @Override
    public int addStudentJudgement(StudentJudgement studentJudgement) {
        return studentJudgementMapper.addStudentJudgement(studentJudgement);
    }

    @Override
    public int deleteStudentJudgement(int id) {
        return studentJudgementMapper.deleteStudentJudgement(id);
    }

    @Override
    public int deleteStudentJudgementByClassAndExercise(int classId, int exerciseId) {
        return studentJudgementMapper.deleteStudentJudgementByClassAndExercise(classId,exerciseId);
    }

    @Override
    public int deleteStudentJudgementByClassAndExam(int classId, int examId) {
        return studentJudgementMapper.deleteStudentJudgementByClassAndExam(classId,examId);
    }

    @Override
    public int updateStudentJudgement(StudentJudgement studentJudgement) {
        return studentJudgementMapper.updateStudentJudgement(studentJudgement);
    }
}
