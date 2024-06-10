package com.app.church.church.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.users.Mapping;

public interface MappingRepository extends CrudRepository<Mapping, Long> {

    Optional<Mapping> findByUrl(String url);

}
