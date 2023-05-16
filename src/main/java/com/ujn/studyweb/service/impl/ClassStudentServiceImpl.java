package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassStudentMapper;
import com.ujn.studyweb.pojo.ClassStudent;
import com.ujn.studyweb.service.IClassStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClassStudentServiceImpl implements IClassStudentService {

    @Autowired
    private ClassStudentMapper classStudentMapper;

    @Override
    public List<ClassStudent> queryClassStudentList() {
        return classStudentMapper.queryClassStudentList();
    }

    @Override
    public List<ClassStudent> queryClassStudentListByClass(int classId) {
        return classStudentMapper.queryClassStudentListByClass(classId);
    }

    @Override
    public List<ClassStudent> queryClassStudentListByStudent(int student) {
        return classStudentMapper.queryClassStudentListByStudent(student);
    }

    @Override
    public ClassStudent queryClassStudentById(int id) {
        return classStudentMapper.queryClassStudentById(id);
    }

    @Override
    public int addClassStudent(ClassStudent classStudent) {
        return classStudentMapper.addClassStudent(classStudent);
    }

    @Override
    public int deleteClassStudent(int id) {
        return classStudentMapper.deleteClassStudent(id);
    }

    @Override
    public int updateClassStudent(ClassStudent classStudent) {
        return classStudentMapper.updateClassStudent(classStudent);
    }
}
