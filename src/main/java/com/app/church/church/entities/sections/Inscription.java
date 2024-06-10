package com.app.church.church.entities.sections;

import java.util.Date;

import com.app.church.church.entities.users.User;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inscription")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double grade;

    private Date date;

    private Date dateRequest;

    @Column(columnDefinition = "boolean default false")
    private boolean validate;
   
    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Inscription() {
        this.date = new Date();
    }

    


    public Inscription(Double grade, Date date, boolean validate) {
        this.grade = grade;
        this.date = date;
        this.validate = validate;
    }

    

    public Date getDateRequest() {
        return dateRequest;
    }




    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }



    public Long getId() {
        return id;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        
        this.date = date;
    }

    public boolean isValidate() {
        return validate;
    }

    public void setValidate(boolean validate) {
        if(validate){
            this.dateRequest = new Date();
        }
        this.validate = validate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((grade == null) ? 0 : grade.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
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
        Inscription other = (Inscription) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (grade == null) {
            if (other.grade != null)
                return false;
        } else if (!grade.equals(other.grade))
            return false;
        if (date == null) {
            if (other.date != null)
                return false;
        } else if (!date.equals(other.date))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", grade=" + grade + ", date=" + date + ", validate=" + validate + ", user="
                + user + ", course=" + course + "}";
    }


    

}
