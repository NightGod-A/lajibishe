package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.StudentChosenMapper;
import com.ujn.studyweb.pojo.StudentChosen;
import com.ujn.studyweb.service.IStudentChosenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StudentChosenServiceImpl implements IStudentChosenService {

    @Autowired
    private StudentChosenMapper studentChosenMapper;

    @Override
    public List<StudentChosen> queryStudentChosen() {
        return studentChosenMapper.queryStudentChosen();
    }

    @Override
    public StudentChosen queryStudentChosenById(int id) {
        return studentChosenMapper.queryStudentChosenById(id);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByStudentAndClass(int student,int classId) {
        return studentChosenMapper.queryStudentChosenByStudentAndClass(student,classId);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByExercise(int exercise) {
        return studentChosenMapper.queryStudentChosenByExercise(exercise);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByExam(int exam) {
        return studentChosenMapper.queryStudentChosenByExam(exam);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByExerciseAndStudentAndClass(int exercise, int student, int classId) {
        return studentChosenMapper.queryStudentChosenByExerciseAndStudentAndClass(exercise,student,classId);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByExamAndStudentAndClass(int exam, int student, int classId) {
        return studentChosenMapper.queryStudentChosenByExamAndStudentAndClass(exam,student,classId);
    }

    @Override
    public List<StudentChosen> queryStudentChosenByStudent(int student) {
        return studentChosenMapper.queryStudentChosenByStudent(student);
    }

    @Override
    public int addStudentChosen(StudentChosen studentChosen) {
        return studentChosenMapper.addStudentChosen(studentChosen);
    }

    @Override
    public int deleteStudentChosen(int id) {
        return studentChosenMapper.deleteStudentChosen(id);
    }

    @Override
    public int deleteStudentChosenByClassAndExercise(int classId, int exerciseId) {
        return studentChosenMapper.deleteStudentChosenByClassAndExercise(classId,exerciseId);
    }

    @Override
    public int deleteStudentChosenByClassAndExam(int classId, int examId) {
        return studentChosenMapper.deleteStudentChosenByClassAndExam(classId,examId);
    }

    @Override
    public int updateStudentChosen(StudentChosen studentChosen) {
        return studentChosenMapper.updateStudentChosen(studentChosen);
    }
}
