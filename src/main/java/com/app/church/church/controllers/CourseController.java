package com.app.church.church.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.church.church.entities.sections.Course;
import com.app.church.church.services.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course/v1/")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public ResponseEntity<?> getAllCourses() {
        List<Course> course = this.courseService.findAllCourses();
        if (course.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(course);
    }

    @GetMapping("{idCourse}")
    public ResponseEntity<?> getById(@PathVariable(name = "idCourse") Long id) {
        Course course = null;
        try {
            course = this.courseService.findById(id);
            return ResponseEntity.ok(course);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("add")
    public ResponseEntity<?> addCourse(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        return ResponseEntity.ofNullable(this.courseService.addCourse(course));
    }

    @PutMapping("update/{idCourse}/{idUser}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course,
            BindingResult result, @PathVariable(name = "idCourse") Long id,
            @PathVariable(name = "idUser") Long idUser) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        Course courseResponse = null;
        try {
            courseResponse = this.courseService.updateCourse(courseResponse, id, idUser);
            return ResponseEntity.ok(courseResponse);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{idCourse}")
    public ResponseEntity<?> deleteCourse(@PathVariable(name = "idCourse") Long id) {

        try {
            this.courseService.deleteCourse(id);
            return ResponseEntity.accepted().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete-teacher/{idTeacher}/{idCourse}/{idUser}")
    public ResponseEntity<?> deleteTeacher(@PathVariable(name = "idTeacher") Long idTeacher,
            @PathVariable(name = "idCourse") Long idCourse, @PathVariable(name = "idUser")Long idUser) {
        Course course = null;
        try {
            course = this.courseService.deleteProfessor(idTeacher, idCourse, idUser);
            return ResponseEntity.ok(course);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("add-teacher/{idTeacher}/{idCourse}/{idUser}")
    public ResponseEntity<?> addTeacher(@PathVariable(name = "idTeacher") Long idTeacher,
    @PathVariable(name = "idCourse") Long idCourse, @PathVariable(name = "idUser")Long idUser){
        Course course = null;
        try {
            course = this.courseService.addProfessor(idTeacher, idCourse, idUser);
            return ResponseEntity.ok(course);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("update-tutor/{idUser}/{idCourse}")
    public ResponseEntity<?> updateTutor(@PathVariable(name = "idUser") Long idUser,
    @PathVariable(name = "idCourse") Long idCourse){
        Course course = null;
        try {
            course = this.courseService.updateTutor(idUser, idCourse);
            return ResponseEntity.ok(course);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " no puede estar vacío.");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
