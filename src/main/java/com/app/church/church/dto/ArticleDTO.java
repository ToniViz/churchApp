package com.app.church.church.dto;



import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArticleDTO {

    private Article article;
    @NotNull
    @NotBlank
    private Topic topic;

    
    


    


    public ArticleDTO(Article article, @NotNull @NotBlank Topic topic) {
        this.article = article;
        this.topic = topic;
    }


    public Article getArticle() {
        return article;
    }


    public void setArticle(Article article) {
        this.article = article;
    }



    public Topic getTopic() {
        return topic;
    }


    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    

}
