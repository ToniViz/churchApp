package com.app.church.church.entities.articles;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.app.church.church.entities.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 15, max = 250)
    private String description;


    private Date date;

   
    private String video;

    @Column(columnDefinition = "boolean default false")
    private boolean verification;

    @OneToMany(mappedBy = "articleC",cascade = CascadeType.ALL, orphanRemoval = true) 
    private Set<Content> content;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "article_topic",
        joinColumns = @JoinColumn(name = "id_article"),
        inverseJoinColumns = @JoinColumn(name = "id_topic"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_article", "id_topic"})}
    )
    private Set<Topic> topics;

    
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;


    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userA;



    public Article() {
        this.date = new Date();
        this.content = new HashSet<>();
        this.topics = new HashSet<>();
        this.comments = new HashSet<>();
        
    }


    public Article(String title, String description) {
        this();
        this.title = title;
        this.description = description;
    }


    


    /*@PrePersist
    public void prePersist(){
        this.verification = false;
    }*/


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getVideo() {
        return video;
    }


    public void setVideo(String video) {
        this.video = video;
    }


    public boolean isVerification() {
        return verification;
    }


    public void setVerification(boolean verification) {
        this.verification = verification;
    }


    public Set<Content> getContent() {
        return content;
    }


    public void setContent(Set<Content> content) {
        this.content = content;
    }


    public Set<Topic> getTopics() {
        return topics;
    }


    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }


    public Set<Comment> getComments() {
        return comments;
    }


    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    
   



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
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
        Article other = (Article) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "{id=" + id + ", title=" + title + ", description=" + description + ", verification="
                + verification + ", content=" + content + ", topics=" + topics + ", comments=" + comments +
                ", date=" + date + "}";
    }


    


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public User getUserA() {
        return userA;
    }


    public void setUserA(User userA) {
        this.userA = userA;
    }


    

    
    
}
