package com.app.church.church.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.church.church.entities.sections.Course;
import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.CourseRepository;
import com.app.church.church.repository.LoginRepository;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Course> findAllCourses() {
        return (List<Course>) this.courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Course findById(Long id) throws InvalidAttributeValueException {
        return this.courseRepository.findById(id).orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Course addCourse(Course course) {
        return this.courseRepository.save(course);
    }

    @Transactional
    @Override
    public Course updateCourse(Course course, Long idCourse, Long idUser) throws InvalidAttributeValueException {
        Optional<Course> courOptional = this.courseRepository.findByIdWithAll(idCourse);
        if(isEnabled(idCourse, idUser)){

            Course c = courOptional.get();
            c.setDateEnd(course.getDateEnd());
            c.setDateInit(course.getDateInit());
            c.setName(course.getName());
            this.courseRepository.save(c);
        }
        return courOptional.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    //Probar si al borrar el curso, se elimina la inscripción
    @Override
    public Course deleteCourse(Long id) throws InvalidAttributeValueException {
        Optional<Course> course = this.courseRepository.findById(id);
        if(course.isPresent()){
           course.get().getStaff().forEach(user -> {
                user.getStaffCourse().remove(course.get());
           });
           this.courseRepository.delete(course.get());
        }
        return course.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Course addProfessor(Long idTeacher, Long idCourse, Long idUser) throws InvalidAttributeValueException {
        Optional<Course> course = this.courseRepository.findByIdWithAll(idCourse);
        Optional<User> user = this.userRepository.findByIdWithCourses(idTeacher);
        if(isEnabled(idCourse, idUser)){
            user.get().getStaffCourse().add(course.get());
            this.userRepository.save(user.get());
        }
        return course.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Course deleteProfessor(Long idTeacher, Long idCourse, Long idUser) throws InvalidAttributeValueException {
        Optional<Course> course = this.courseRepository.findByIdWithAll(idCourse);
        Optional<User> user = this.userRepository.findByIdWithCourses(idTeacher);
        if(isEnabled(idCourse, idUser)){
            user.get().getStaffCourse().remove(course.get());
            this.userRepository.save(user.get());
        }
        return course.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Course updateTutor(Long idUser, Long idCourse) throws InvalidAttributeValueException {
        Optional<Course> course = this.courseRepository.findByIdWithAll(idCourse);
        Optional<User> user = this.userRepository.findByIdWithCourses(idUser);
        if(course.isPresent() && user.isPresent()){
            course.get().setTutor(user.get());
            this.courseRepository.save(course.get());
        }
        return course.orElseThrow(InvalidAttributeValueException::new);
    }

    private boolean isEnabled(Long idCourse, Long idUser){
        Optional<Course> course = this.courseRepository.findById(idCourse);
        Optional<User> user = this.userRepository.findById(idUser);
        Optional<Login> login = this.loginRepository.findById(idUser);
        if(course.isPresent() && user.isPresent()){
            Set<Role> permissions = login.get().getRoles();
            if(permissions.contains(roleRepository.findByRole("ROLE_ADMIN").get())
            || permissions.contains(roleRepository.findByRole("ROLE_PREACHER").get())){
                return true;
            }else if(permissions.contains(roleRepository.findByRole("ROLE_TEACHER").get()) &&
            user.get().getUserTutor().equals(course.get())){
                return true;
            }else{
                throw new AccessDeniedException("You dont have enough permissions.");
            }
        }
        return false;
    }

}
