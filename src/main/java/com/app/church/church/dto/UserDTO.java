package com.app.church.church.dto;

import com.app.church.church.entities.users.Login;

import com.app.church.church.entities.users.User;

public class UserDTO {

    private User user;
    private Login login;



    
    
    public UserDTO() {
    }

    
    public UserDTO(User user, Login login) {
        this.user = user;
        this.login = login;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Login getLogin() {
        return login;
    }
    public void setLogin(Login login) {
        this.login = login;
    }

    
}
