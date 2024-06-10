package com.app.church.church.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.articles.Topic;
import java.util.Optional;


public interface TopicRepository extends CrudRepository<Topic, Long> {

    Optional<Topic> findByType(String type);
}
