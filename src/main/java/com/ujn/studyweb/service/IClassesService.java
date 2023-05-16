package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Classes;

import java.util.List;

public interface IClassesService {
    List<Classes> queryClassesList();
    List<Classes> queryClassesListByCourse(int course);
    Classes queryClassesById(int id);
    int addClasses(Classes classes);
    int deleteClasses(int id);
    int updateClasses(Classes classes);
}
