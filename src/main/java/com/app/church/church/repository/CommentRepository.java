package com.app.church.church.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.church.church.entities.articles.Comment;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long>, CrudRepository<Comment, Long> {

}
