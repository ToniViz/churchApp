package com.app.church.church.repository;

import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.articles.Content;

public interface ContentRepository extends CrudRepository<Content, Long> {

}
