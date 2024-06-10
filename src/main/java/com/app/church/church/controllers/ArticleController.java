package com.app.church.church.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.InvalidAttributeValueException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.dto.ArticleDTO;
import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.articles.Content;
import com.app.church.church.services.ArticleService;


import jakarta.validation.Valid;

/*
 * Los comentarios se borran desde el mismo repositorio comentario y los Id pasados son siempre del comentario, no del artículo
 */
@RestController
@RequestMapping("/api/article/v1/")
public class ArticleController {

    @Autowired
    private ArticleService articleService;


    /**
     * Returns all the articles
     * 
     * @param page
     * @return: List<Articles>
     */
    @GetMapping("")
    public Page<Article> mainView(@RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {

        return this.articleService.findAllArticlesWithVerification(true, page, size);
    }


    @PostMapping("add-article/{idUser}")
    public ResponseEntity<?> addArticle(@Valid @RequestBody ArticleDTO article,BindingResult binding, 
    @PathVariable(name = "idUser") Long idUser){
        if(binding.hasFieldErrors()){
            return this.validation(binding);
        }
        Article articleReturn = null;
        try {
            articleReturn = this.articleService.addArticleWithType(article, idUser);
            return ResponseEntity.ok(articleReturn);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("un-checked")
    public Page<Article> getArticlesByCheck(@RequestParam(name = "page", defaultValue = "0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.articleService.findAllArticlesWithVerification(false, page, size);
    }

    @GetMapping("{topic}")
    public Page<Article> getArticlesByTopic(@PathVariable String topic, @RequestParam(name = "page", defaultValue="0") int page,
    @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.articleService.findAllByTopics(topic, page, size);
    }

    @GetMapping("{idArticle}")
    public ResponseEntity<?> getArticleById(@PathVariable Long idArticle) {
        Article article = null;
        try {
            article = this.articleService.findById(idArticle);
            return ResponseEntity.ok(article);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("video/{idArticle}/{idUser}")
    public ResponseEntity<?> setArticleVideo(@RequestParam(name = "video") String video,
            @PathVariable(name = "idArticle") Integer id, @PathVariable Long idUser) {
        Article article = null;
        try {
            article = this.articleService.updateArticleVideo(video, id, idUser);
            return ResponseEntity.ok(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("addMultimedia/{idContent}/{idUser}/{tipe}")
    public ResponseEntity<?> setPhotoContent(@RequestParam(name = "media") MultipartFile media,
            @PathVariable(name = "idContent") Long id, @PathVariable(name = "tipe") String tipe,
            @PathVariable Long idUser) {

        Content content = null;

        try {
            content = this.articleService.saveContentMultimedia(media, id, tipe, idUser);
            return ResponseEntity.ok(content);
        } catch (InvalidAttributeValueException | SQLException | IOException e) {
            return ResponseEntity.badRequest().build();
        }

       
    }


    @PutMapping("update-content/{idContent}/{idUser}")
    public ResponseEntity<?> updateContent(@Valid @RequestBody Content content, BindingResult 
    result, @PathVariable(name = "idContent") Long id, @PathVariable Long idUser ){

        if(result.hasFieldErrors()){
            return this.validation(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(this.articleService.updateContentText(content, id, idUser));
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();

        }
    }


    @PostMapping("add-content/{idArticle}/{idUser}")
    public ResponseEntity<?> addContent(@Valid @RequestBody Content content, BindingResult 
    result, @PathVariable(name = "idArticle") Long id, @PathVariable Long idUser){

        if(result.hasFieldErrors()){
            return this.validation(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(this.articleService.addContent(content, id, idUser));
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();

        }
    }


    @PutMapping("update-article/{idArticle}/{idUser}")
    public ResponseEntity<?> updateArticle(@Valid @RequestBody ArticleDTO articleDTO, BindingResult 
    result, @PathVariable(name = "idArticle") Long id, @PathVariable Long idUser){

        if(result.hasFieldErrors()){
            return this.validation(result);
        }
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(this.articleService.updateArticleText(articleDTO, id, idUser));
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete-article/{idArticle}/{idUser}")
    public ResponseEntity<?> deleteArticle(@PathVariable(name = "idArticle") Long id,
    @PathVariable Long idUser){
        Article article = null;

        try {
            article = this.articleService.deleteArticle(id, idUser);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete-content/{idArticle}/{idContent}/{idUser}")
    public ResponseEntity<?> deleteContent(@PathVariable Long idArticle,
    @PathVariable Long idContent, @PathVariable Long idUser){
        Article article = null;

        try {
            article = this.articleService.deleteContent(idArticle, idContent, idUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("add-comment/{idArticle}/{idUser}")
    public ResponseEntity<?> addComment(@RequestBody Comment comment, 
    @PathVariable(name = "idArticle") Long idArticle, @PathVariable(name = "idUser")Long idUser){
        Article article = null;

        try {
            article = this.articleService.addComment(idArticle, idUser, comment);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update-comment/{idComment}/{idUser}")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment, @PathVariable(name = "idComment") Long id,
    @PathVariable Long idUser){
        Article article = null;

        try {
            article = this.articleService.updateComment(id, comment, idUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete-comment/{idComment}/{idUser}")
    public ResponseEntity<?> deleteComment(@PathVariable(name = "idComment") Long id,
    @PathVariable Long idUser){
        Article article = null;

        try {
            article = this.articleService.deleteComment(id, idUser);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
            .body(article);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " no puede estar vacío.");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
