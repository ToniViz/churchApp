package com.app.church.church.entities.sections;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.app.church.church.entities.users.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 50)
    private String name;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "id_tutor")
    private User tutor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inscription> inscription;

    private Date dateInit;

    private Date dateEnd;

    
    @ManyToMany(mappedBy = "staffCourse")
    private Set<User> staff;

    public Course() {
        this.inscription = new HashSet<>();
        this.staff = new HashSet<>();
    }

    public Course(String name) {
        this.name = name;
    }

    

    public Course(@NotBlank @Size(min = 5, max = 50) String name, Date dateInit, Date dateEnd) {
        this.name = name;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
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

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    
    


    public Date getDateInit() {
        return dateInit;
    }

    public void setDateInit(Date dateInit) {
        this.dateInit = dateInit;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Set<User> getStaff() {
        return staff;
    }

    public void setStaff(Set<User> staff) {
        this.staff = staff;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Course other = (Course) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", name=" + name + ", tutor=" + tutor + ", dateInit=" + dateInit + ", dateEnd="
                + dateEnd + ", inscription=" +inscription + "}";
    }

    public Set<Inscription> getInscription() {
        return inscription;
    }

    public void setInscription(Set<Inscription> inscription) {
        this.inscription = inscription;
    }

}
