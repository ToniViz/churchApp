package com.app.church.church.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.sections.Course;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Query("select c from Course c left join fetch c.staff where c.id=?1")
    Optional<Course> findByIdWithAll(Long id);

}
