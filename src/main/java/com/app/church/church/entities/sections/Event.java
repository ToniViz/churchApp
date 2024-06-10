package com.app.church.church.entities.sections;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.app.church.church.entities.users.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    
    private String name;

    @NotBlank
    @Size(max = 350, min = 10)
    private String description;

    private Date dateInit;

    private Date dateEnd;

    @OneToOne
    @JoinColumn(name = "id_organizer")
    private User organizer;

    @ManyToMany(mappedBy = "events")
    private Set<User> assistants;

    public Event() {
        this.assistants = new HashSet<>();
        
    }

    public Event(String name, String description) {
        this.name = name;
        this.description = description;
    }

    

    public Event(@NotBlank String name, @NotBlank @Size(max = 350, min = 10) String description, Date dateInit,
            Date dateEnd) {
        this.name = name;
        this.description = description;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
    }

    

    public Event(@NotBlank String name, @NotBlank @Size(max = 350, min = 10) String description, Date dateInit,
            Date dateEnd, User organizer) {
        this.name = name;
        this.description = description;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.organizer = organizer;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOrganizer() {
        return organizer;
    }

    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public Set<User> getAssistants() {
        return assistants;
    }

    public void setAssistants(Set<User> assistants) {
        this.assistants = assistants;
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
        Event other = (Event) obj;
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
        return "{id=" + id + ", name=" + name + ", description=" + description + ", dateInit=" + dateInit
                + ", dateEnd=" + dateEnd + ", organizer=" + organizer + "}";
    }

    

    

    
    

}
