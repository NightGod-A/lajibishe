package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.ClassesMapper;
import com.ujn.studyweb.pojo.Classes;
import com.ujn.studyweb.service.IClassesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements IClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    @Override
    public List<Classes> queryClassesList() {
        return classesMapper.queryClassesList();
    }

    @Override
    public List<Classes> queryClassesListByCourse(int course) {
        return classesMapper.queryClassesListByCourse(course);
    }

    @Override
    public Classes queryClassesById(int id) {
        return classesMapper.queryClassesById(id);
    }

    @Override
    public int addClasses(Classes classes) {
        return classesMapper.addClasses(classes);
    }

    @Override
    public int deleteClasses(int id) {
        return classesMapper.deleteClasses(id);
    }

    @Override
    public int updateClasses(Classes classes) {
        return classesMapper.updateClasses(classes);
    }
}
