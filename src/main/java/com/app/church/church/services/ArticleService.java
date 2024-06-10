package com.app.church.church.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.management.InvalidAttributeValueException;
import javax.sql.rowset.serial.SerialException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.dto.ArticleDTO;
import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.articles.Content;

public interface ArticleService {

    public List<Article> findAllArticles();

    public Page<Article> findAllArticlesWithVerification(boolean verification, int page, int size);

    public Page<Article> findAllByTopics(String topic, int page, int size);

    public Article findById(Long id) throws Exception;

    public Article addArticleWithType(ArticleDTO articleDTO, Long id) throws InvalidAttributeValueException;

    public Article updateArticleVideo(String video, Integer id, Long idUser) throws InvalidAttributeValueException;
  
    public Article updateArticleText(ArticleDTO articleDTO, Long id, Long idUser) throws InvalidAttributeValueException;
    
    public Content saveContentMultimedia(MultipartFile media, Long id, String tipe, Long idUser ) throws InvalidAttributeValueException, SerialException, SQLException, IOException;

    public Content updateContentText(Content content, Long id, Long idUser) throws InvalidAttributeValueException;
    
    public Article deleteArticle(Long id, Long idUser) throws InvalidAttributeValueException;

    public Article deleteContent(Long idArticle, Long idContent, Long idUser) throws InvalidAttributeValueException;

    public Article addContent(Content content, Long id, Long idUser)throws InvalidAttributeValueException;
    
    public Article addComment(Long idArticle,Long idUser, Comment comment) throws InvalidAttributeValueException;

    public Article updateComment(Long id, Comment comment, Long idUser) throws InvalidAttributeValueException;

    public Article deleteComment(Long id, Long idUser) throws InvalidAttributeValueException;
}

