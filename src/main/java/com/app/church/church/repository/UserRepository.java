package com.app.church.church.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.church.church.entities.users.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long> {

   

    @Query("select u from User u left join fetch u.articlesU where u.id=?1")
    Optional<User> findByIdWithArticles(Long id);

    @Query("select u from User u left join fetch u.senders where u.id=?1")
    Optional<User> findByIdWithMessageSender(Long id);

    @Query("select u from User u left join fetch u.receivers where u.id=?1")
    Optional<User> findByIdWithMessageReceivers(Long id);

    @Query("select u from User u left join fetch u.comments where u.id=?1")
    Optional<User> findByIdWithComments(Long id);

    @Query("select u from User u left join fetch u.staffCourse where u.id=?1")
    Optional<User> findByIdWithCourses(Long id);

    @Query("select u from User u left join fetch u.events where u.id=?1")
    Optional<User> findByIdWithEvents(Long id);


}
