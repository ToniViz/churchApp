package com.app.church.church.services;


import java.io.IOException;
import java.sql.SQLException;


import javax.management.InvalidAttributeValueException;
import javax.naming.NameNotFoundException;
import javax.sql.rowset.serial.SerialException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.dto.UserDTO;
import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;

public interface UserService{


    public User getUserWithArticles(Long id) throws InvalidAttributeValueException;

    public User getEventsByUserID(Long id) throws InvalidAttributeValueException; 

    public User getCoursesAsTeacherbyUserID(Long id) throws InvalidAttributeValueException;

    public User getMessagesSenderByUserID(Long id) throws InvalidAttributeValueException;
    
    public User getMessagesReceiverByUserID(Long id) throws InvalidAttributeValueException;
    
    public Page<User> getAllUsers(int page, int size) throws Exception;

    public Page<User> getUsersByType(String type, int page, int size) throws NameNotFoundException;

    public User findUserById(Long id) throws InvalidAttributeValueException;

    public User updateUser(User user, Long id) throws InvalidAttributeValueException;

    public User addUser(UserDTO userDTO) throws NameNotFoundException;

    public boolean deleteUser(Long id) throws InvalidAttributeValueException;

    public User updatePhoto(MultipartFile multipart, Long id) throws InvalidAttributeValueException, SerialException, SQLException, IOException;

    public User updateLogin(Login login, Long id) throws InvalidAttributeValueException;
    
    /**
     * Todos los roles que se reciben se deben persistir en el usuario.
     * Los que no están se eliminan.
     * @param roles
     * @param id
     * @return
     * @throws InvalidAttributeValueException
     */
    public User updateRole(Role roles, Long id) throws InvalidAttributeValueException;
    
    public boolean existByUsername(String username);

}
