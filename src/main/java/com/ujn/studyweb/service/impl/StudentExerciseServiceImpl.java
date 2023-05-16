package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.StudentExerciseMapper;
import com.ujn.studyweb.pojo.StudentExercise;
import com.ujn.studyweb.service.IStudentExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentExerciseServiceImpl implements IStudentExerciseService {
    @Autowired
    private StudentExerciseMapper studentExerciseMapper;

    @Override
    public List<StudentExercise> queryStudentExercise() {
        return studentExerciseMapper.queryStudentExercise();
    }

    @Override
    public StudentExercise queryStudentExerciseById(int id) {
        return studentExerciseMapper.queryStudentExerciseById(id);
    }

    @Override
    public List<StudentExercise> queryStudentExerciseByStudentAndClass(int student, int classId) {
        return studentExerciseMapper.queryStudentExerciseByStudentAndClass(student,classId);
    }

    @Override
    public StudentExercise queryStudentExerciseByStudentAndClassAndExercise(int student, int classId, int exerciseId) {
        return studentExerciseMapper.queryStudentExerciseByStudentAndClassAndExercise(student,classId,exerciseId);
    }

    @Override
    public List<StudentExercise> queryStudentExerciseByExercise(int exerciseId) {
        return studentExerciseMapper.queryStudentExerciseByExercise(exerciseId);
    }

    @Override
    public int addStudentExercise(StudentExercise studentExercise) {
        return studentExerciseMapper.addStudentExercise(studentExercise);
    }

    @Override
    public int deleteStudentExercise(int id) {
        return studentExerciseMapper.deleteStudentExercise(id);
    }

    @Override
    public int deleteStudentExerciseByClassAndExercise(int classId, int exerciseId) {
        return studentExerciseMapper.deleteStudentExerciseByClassAndExercise(classId,exerciseId);
    }

    @Override
    public int updateStudentExercise(StudentExercise studentExercise) {
        return studentExerciseMapper.updateStudentExercise(studentExercise);
    }
}
