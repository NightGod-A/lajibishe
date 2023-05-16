package com.ujn.studyweb.mapper;

import com.ujn.studyweb.pojo.Course;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CourseMapper {
    List<Course> queryCourseList();
    Course queryCourseById(int id);
    List<Course> queryCourseByTeacher(int teacher);
    int addCourse(Course course);
    int deleteCourse(int id);
    int updateCourse(Course course);
    int updateCourseEnable(Course course);
}
