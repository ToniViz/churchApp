package com.app.church.church.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.users.Login;





public interface LoginRepository extends CrudRepository<Login, Long> {

    Optional<Login> findByUsername(String username);

    @Query("select u from Login u left join fetch u.roles where u.id=?1")
    Optional<Login> findByIdWithRoles(Long id);

    @Query("select l From Login l join l.roles r where r.role=?1")
    Page<Login> findAllByRolesPage (String type, Pageable pageable);

}
