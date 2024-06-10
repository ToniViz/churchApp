package com.app.church.church.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.InvalidAttributeValueException;
import javax.naming.NameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.dto.UserDTO;
import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/user/v1/")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserArticles(@PathVariable(name = "id") Long id) {

        User user = null;
        try {
            user = this.userService.getUserWithArticles(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("add-user")
    public ResponseEntity<?> addUserwithLogin(@Valid @RequestBody UserDTO userDTO,
            BindingResult result) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        User user = null;

        try {
            user = this.userService.addUser(userDTO);
            return ResponseEntity.ok(user);
        } catch (NameNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
        
    }

    @GetMapping("events/{id}")
    public ResponseEntity<?> getEventsByUserID(@PathVariable(name = "id") Long id) {
        User user = null;
        try {
            user = this.userService.getEventsByUserID(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("message-sender/{id}")
    public ResponseEntity<?> getMessagesSenderByUserID(@PathVariable(name = "id") Long id) {
        User user = null;
        try {
            user = this.userService.getMessagesSenderByUserID(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("message-receiver/{id}")
    public ResponseEntity<?> getMessagesReceiverByUserID(@PathVariable(name = "id") Long id) {
        User user = null;
        try {
            user = this.userService.getMessagesReceiverByUserID(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("all-users")
    public Page<User> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return this.getAllUsers(page, size);
    }

    @GetMapping("all-users-type/{type}")
    public Page<User> getUsersByType(@PathVariable(name = "type") String type,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        return this.getUsersByType(type, page, size);
    }

    @GetMapping("teacher/{id}")
    public ResponseEntity<?> getCoursesAsTeacher(@PathVariable(name = "id") Long id) {
        User user = null;
        try {
            user = this.userService.getCoursesAsTeacherbyUserID(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<?> getUserbyID(@PathVariable(name = "id") Long id) {
        User user = null;

        try {
            user = this.userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update-user/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result,
            @PathVariable(name = "id") Long id) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        User userResponse = null;
        try {
            userResponse = this.userService.updateUser(userResponse, id);
            return ResponseEntity.ok(userResponse);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) {

        try {
            this.userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("update-photo/{id}")
    public ResponseEntity<?> updatePhoto(@RequestParam(name = "photo") MultipartFile photo,
            @PathVariable(name = "id") Long id) {
        User user = null;

        try {
            user = this.userService.updatePhoto(photo, id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException | SQLException | IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 
     * @param login
     * @param result
     * @param id user
     * @return
     */
    @PutMapping("update-login/{id}")
    public ResponseEntity<?> updateLogin(@Valid @RequestBody Login login,
    BindingResult result, @PathVariable(name = "id")Long id ){
        if(result.hasFieldErrors()){
            return this.validation(result);
        }
        User user = null;
        try {
            user = this.userService.updateLogin(login, id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
           return ResponseEntity.notFound().build();
        }
    }

    /**
     * Si el rol existe se elimina; si no existe se añade
     * @param roles
     * @param id
     * @return
     */
    @PutMapping("update-role/{id}")
    public ResponseEntity<?> updateRole(@RequestBody Role roles,
    @PathVariable(name = "id") Long id){
        User user = null;
        try {
            user = this.userService.updateRole(roles, id);
            return ResponseEntity.ok(user);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " no puede estar vacío.");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
