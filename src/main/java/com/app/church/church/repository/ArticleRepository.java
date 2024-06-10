package com.app.church.church.repository;



import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.church.church.entities.articles.Article;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long>, CrudRepository<Article, Long> {

    @Query("select a from Article a left join fetch a.topics left join fetch a.content left join fetch a.comments where a.id=?1")
    Optional<Article> findByIdWithAll(Long id);

    @Query("select a from Article a left join fetch a.topics where a.verification=?1")
    Page<Article> findAllWithVerification(boolean verification, Pageable pageable);

    @Query("select a from Article a left join fetch a.topics")
    Page<Article> findAllWithTopics(Pageable pageable);

    @Query("select a from Article a join a.topics t where t.type=?1")
    Page<Article> findAllByRolPage(String topic, Pageable pageable);
 

}
