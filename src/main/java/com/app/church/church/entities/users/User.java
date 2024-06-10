package com.app.church.church.entities.users;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

import com.app.church.church.entities.articles.Article;
import com.app.church.church.entities.articles.Comment;
import com.app.church.church.entities.sections.Course;
import com.app.church.church.entities.sections.Event;
import com.app.church.church.entities.sections.Inscription;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = false,
    mappedBy = "organizer")
    private Event event;

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;
    @NotBlank
    @Size(min = 3, max = 50)
    private String surname;
    @NotBlank
    @Size(min = 4, max = 20)
    private String address;
    @NotBlank
    @Size(min = 5, max = 20)
    private String phone;
    @NotBlank
    @Size(min = 5, max = 50)
    private String email;

    private Blob photo;

    @Column(columnDefinition = "boolean default false")
    private boolean notification;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Message> senders;

    @OneToMany(mappedBy = "receiver",cascade = CascadeType.ALL, orphanRemoval = false)
    private Set<Message> receivers;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true,
    mappedBy = "user")
    private Login login; 

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST},orphanRemoval = false,
    mappedBy = "tutor")
    private Course userTutor;


    

    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "staff_course",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_course"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_course"})}
    )
    private Set<Course> staffCourse;


    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true,
    mappedBy = "user")
    private Inscription inscription;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;

    @OneToMany(mappedBy = "userA",cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true) 
    private Set<Article> articlesU;


    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
        name = "event_assistent",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name= "id_event"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id_user", "id_event"})}
    )
    private Set<Event> events;


    public User(){
        this.receivers = new HashSet<>();
        this.senders = new HashSet<>();
        this.articlesU = new HashSet<>();
        this.events = new HashSet<>();
        this.comments = new HashSet<>();
        this.staffCourse = new HashSet<>();
       
        
    }

    public User(String name, String surname, String address, String phone, String email) {
        this();
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Blob getPhoto() {
        return photo;
    }

    public void setPhoto(Blob photo) {
        this.photo = photo;
    }



    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public Login getLogin() {
        return login;
    }

    public void setLogin(Login login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Event getEvent(){
        return this.event;
    }

    public void setEvent(Event event){
       this.event = event;
    }


    
    public Set<Message> getSenders() {
        return senders;
    }

    public void setSenders(Set<Message> senders) {
        this.senders = senders;
    }

    public Set<Message> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Message> receivers) {
        this.receivers = receivers;
    }

    public Course getUserTutor() {
        return userTutor;
    }

    public void setUserTutor(Course userTutor) {
        this.userTutor = userTutor;
    }

    public Set<Course> getStaffCourse() {
        return staffCourse;
    }

    public void setStaffCourse(Set<Course> staffCourse) {
        this.staffCourse = staffCourse;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Article> getArticlesU() {
        return articlesU;
    }

    public void setArticlesU(Set<Article> ArticlesU) {
        this.articlesU = ArticlesU;
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    

   

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((surname == null) ? 0 : surname.hashCode());
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((phone == null) ? 0 : phone.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
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
        User other = (User) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (surname == null) {
            if (other.surname != null)
                return false;
        } else if (!surname.equals(other.surname))
            return false;
        if (address == null) {
            if (other.address != null)
                return false;
        } else if (!address.equals(other.address))
            return false;
        if (phone == null) {
            if (other.phone != null)
                return false;
        } else if (!phone.equals(other.phone))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", surname=" + surname + ", address=" + address + ", phone="
                + phone + ", email=" + email + ", photo=" + photo + ", notification=" + notification + ", senders="
                + senders + ", receivers=" + receivers + ", login=" + login +  ", userTutor="
                + userTutor + ", comments=" + comments + ", articleU=" + articlesU
                + "}";
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }


}
