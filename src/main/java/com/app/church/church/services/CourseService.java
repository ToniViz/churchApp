package com.app.church.church.services;

import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.app.church.church.entities.sections.Course;

public interface CourseService {

    public List<Course> findAllCourses();

    public Course findById(Long id) throws InvalidAttributeValueException;

    public Course addCourse(Course course);

    public Course updateCourse(Course course, Long idCourse, Long idUser) throws InvalidAttributeValueException;

    public Course deleteCourse(Long id) throws InvalidAttributeValueException;

    public Course addProfessor(Long idTeacher, Long idCourse, Long idUser) throws InvalidAttributeValueException;

    public Course deleteProfessor(Long idTeacher, Long idCourse, Long idUser) throws InvalidAttributeValueException;

    public Course updateTutor(Long idUser, Long idCourse) throws InvalidAttributeValueException;

}
