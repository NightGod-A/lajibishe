package com.ujn.studyweb.service;

import com.ujn.studyweb.pojo.Course;

import java.util.List;

public interface ICourseService {
    List<Course> queryCourseList();
    Course queryCourseById(int id);
    List<Course> queryCourseByTeacher(int teacher);
    int addCourse(Course course);
    int deleteCourse(int id);
    int updateCourse(Course course);
    int updateCourseEnable(Course course);
}
