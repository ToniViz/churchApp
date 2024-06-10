package com.app.church.church.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.church.church.entities.sections.Event;

public interface EventRepository extends PagingAndSortingRepository<Event, Long>, CrudRepository<Event, Long> {

    @Query("select e from Event e left join fetch e.assistants where e.id=?1")
    Optional<Event> findByIdWithAssistants(Long id);
}
