
package com.app.church.church.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.management.InvalidAttributeValueException;
import javax.naming.NameNotFoundException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.app.church.church.dto.UserDTO;

import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Message;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.ArticleRepository;
import com.app.church.church.repository.LoginRepository;
import com.app.church.church.repository.MessageRepository;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.TopicRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional(readOnly = true)
    @Override
    public User getUserWithArticles(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findByIdWithArticles(id);

        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public User getEventsByUserID(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findByIdWithEvents(id);
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public User getCoursesAsTeacherbyUserID(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findByIdWithCourses(id);
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public User getMessagesSenderByUserID(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        Set<Message> messages = this.messageRepository.findNonDeletedSendMessages(id);
        if(user.isPresent()){
            user.get().setSenders(messages);
        }
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public User getMessagesReceiverByUserID(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        Set<Message> messages = this.messageRepository.findNonDeletedReceiveMessages(id);
        if(user.isPresent()){
            user.get().setReceivers(messages);
        }
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> getAllUsers(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findAll(pageable);
    }

     
    @Transactional(readOnly = true)
    @Override
    public Page<User> getUsersByType(String type, int page, int size) throws NameNotFoundException {
        Pageable pageable = PageRequest.of(page, size);
        Page<Login> login = this.loginRepository.findAllByRolesPage(type, pageable);
        List<User> u = new ArrayList<>();
        login.forEach(element ->{
           u.add(element.getUser());
        });
        Page<User> users = new PageImpl<>(u);
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User findUserById(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public User updateUser(User user, Long id) throws InvalidAttributeValueException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            userOptional.get().setName(user.getName());
            userOptional.get().setSurname(user.getSurname());
            userOptional.get().setAddress(user.getAddress());
            userOptional.get().setEmail(user.getEmail());
            userOptional.get().setPhone(user.getPhone());
            this.userRepository.save(userOptional.get());

        }
        return userOptional.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public User addUser(UserDTO userDTO) throws NameNotFoundException {
        Optional<Role> role = this.roleRepository.findByRole("ROLE_USER");
        User user = userDTO.getUser();
       
        Login login = userDTO.getLogin();
        String encodedPass = passwordEncoder.encode(login.getPassword());
        login.setPassword(encodedPass);
        if (role.isPresent()) {

            login.getRoles().add(role.get());
            this.userRepository.save(user);
        }else{
            throw new NameNotFoundException();
        }
        
        login.setUser(user);
        this.loginRepository.save(login);
        return user;
    }

    @Transactional
    @Override
    public boolean deleteUser(Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            this.userRepository.delete(user.get());
            return true;
        } else {
            return false;
        }

    }

    @Transactional
    @Override
    public User updatePhoto(MultipartFile multipart, Long id)
            throws InvalidAttributeValueException, SerialException, SQLException, IOException {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            Blob mediablob = new SerialBlob(multipart.getBytes());
            user.get().setPhoto(mediablob);
            this.userRepository.save(user.get());
        }
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Override
    public User updateLogin(Login login, Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent()) {
            Login loginUser = user.get().getLogin();
            String encodedPass = passwordEncoder.encode(loginUser.getPassword());
            loginUser.setUsername(login.getUsername());
            loginUser.setPassword(encodedPass);
            this.loginRepository.save(loginUser);
        }
        return user.orElseThrow(InvalidAttributeValueException::new);
    }

    @Override
    public boolean existByUsername(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'existByUsername'");
    }

    @Transactional
    @Override
    public User updateRole(Role role, Long id) throws InvalidAttributeValueException {
        Optional<Login> loginO = this.loginRepository.findByIdWithRoles(id);
        if (!loginO.isPresent()) {
            throw new InvalidAttributeValueException();
        }
        
        Login login = loginO.get();
        
        Set<Role> roles = login.getRoles();
        boolean t = roles.contains(role)? login.getRoles().remove(role) : login.getRoles().add(role);
        this.loginRepository.save(login);
        return login.getUser();

    }

}
