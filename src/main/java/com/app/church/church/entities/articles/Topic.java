package com.app.church.church.entities.articles;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    private boolean isCourse;

    @ManyToMany(mappedBy = "topics")
    private Set<Article> articles;

    public Topic() {
        this.articles = new HashSet<>();
    }

    public Topic(String type, boolean isCourse) {
        this();
        this.type = type;
        this.isCourse = isCourse;
    }



    public Long getId() {
        return id;
    }

    

    public boolean isCourse() {
        return isCourse;
    }

    public void setCourse(boolean isCourse) {
        this.isCourse = isCourse;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + (isCourse ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Topic other = (Topic) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        if (isCourse != other.isCourse)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", type=" + type + ", isCourse=" + isCourse + "}";
    }

   

    
    
    

}
