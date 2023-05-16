package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Classes;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ClassesMapper {
    List<Classes> queryClassesList();
    List<Classes> queryClassesListByCourse(int course);
    Classes queryClassesById(int id);
    int addClasses(Classes classes);
    int deleteClasses(int id);
    int updateClasses(Classes classes);
}
