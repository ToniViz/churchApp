package com.app.church.church.entities.users;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String role;

    @ManyToMany(mappedBy = "roles")
    private Set<Login> logins;

    @ManyToMany
    @JoinTable(
        name = "role_mapping",
        joinColumns = @JoinColumn(name = "role_id"),
        inverseJoinColumns = @JoinColumn(name ="mapping_id"),
        uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "mapping_id"})}
    )
    private Set<Mapping> mappings;

    public Role(){
        this.mappings = new HashSet<>();
        this.logins = new HashSet<>();
    }

    public Role(String role) {
        this();
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public Set<Login> getLogins() {
        return logins;
    }

    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }

    public Set<Mapping> getMappings() {
        return mappings;
    }

    public void setMappings(Set<Mapping> mappings) {
        this.mappings = mappings;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "{id=" + id + ", role=" + role + "}";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        Role other = (Role) obj;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

   

    
    
    
    
}
