package com.ujn.studyweb.service.impl;

import com.ujn.studyweb.mapper.CourseMapper;
import com.ujn.studyweb.pojo.Course;
import com.ujn.studyweb.service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public List<Course> queryCourseList() {
        return courseMapper.queryCourseList();
    }

    @Override
    public Course queryCourseById(int id) {
        return courseMapper.queryCourseById(id);
    }

    @Override
    public List<Course> queryCourseByTeacher(int teacher) {
        return courseMapper.queryCourseByTeacher(teacher);
    }

    @Override
    public int addCourse(Course course) {
        return courseMapper.addCourse(course);
    }

    @Override
    public int deleteCourse(int id) {
        return courseMapper.deleteCourse(id);
    }

    @Override
    public int updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public int updateCourseEnable(Course course) {
        return courseMapper.updateCourseEnable(course);
    }
}
