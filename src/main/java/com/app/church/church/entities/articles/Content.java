package com.app.church.church.entities.articles;

import java.sql.Blob;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "content")
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 100)
    private String subtitle;

    @NotBlank
    private String text;

    private Blob image;

    private Blob document;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article articleC;



    public Content() {
    }

    

    

    public Content(@NotBlank @Size(min = 5, max = 100) String subtitle, @NotBlank String text) {
        this.subtitle = subtitle;
        this.text = text;
    }





    public Content(@NotBlank @Size(min = 5, max = 100) String subtitle, @NotBlank String text, Blob image,
            Blob document) {
        this.subtitle = subtitle;
        this.text = text;
        this.image = image;
        this.document = document;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Blob getDocument() {
        return document;
    }

    public void setDocument(Blob document) {
        this.document = document;
    }

    

    
   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((subtitle == null) ? 0 : subtitle.hashCode());
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
        Content other = (Content) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (subtitle == null) {
            if (other.subtitle != null)
                return false;
        } else if (!subtitle.equals(other.subtitle))
            return false;
        return true;
    }

    

    @Override
    public String toString() {
        return "{id=" + id + ", subtitle=" + subtitle + ", text=" + text + ", image=" + image + ", document="
                + document + "}";
    }





    public Article getArticleC() {
        return articleC;
    }





    public void setArticleC(Article articleC) {
        this.articleC = articleC;
    }

   
    

    

}
