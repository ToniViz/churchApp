package com.app.church.church.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.sections.Inscription;

public interface InscriptionRepository extends CrudRepository<Inscription, Long> {

    @Query("select i from Inscription i join i.course c where c.name=?1")
    List<Inscription> findAllByCourse(String name);
}
