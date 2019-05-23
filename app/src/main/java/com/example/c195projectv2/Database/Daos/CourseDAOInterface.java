package com.example.c195projectv2.Database.Daos;

import com.example.c195projectv2.Models.Course;

import java.util.List;

public interface CourseDAOInterface {

    boolean addCourse(Course course);

    Course getCourseById(int courseId);

    List<Course> getCoursesByTerm(int termId);

    int getCourseCount();

    List<Course> getCourses();

    boolean removeCourse(Course course);
    boolean updateCourse(Course course);
}
