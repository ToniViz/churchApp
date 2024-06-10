package com.app.church.church.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.church.church.entities.sections.Course;
import com.app.church.church.entities.sections.Inscription;
import com.app.church.church.entities.users.Login;
import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.CourseRepository;
import com.app.church.church.repository.InscriptionRepository;
import com.app.church.church.repository.LoginRepository;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class InscriptionServiceImpl implements InscriptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Inscription> findAllByCourse(String name) throws InvalidAttributeValueException {

        List<Inscription> inscriptions = this.inscriptionRepository.findAllByCourse(name);
        if (inscriptions == null || inscriptions.isEmpty()) {
            throw new InvalidAttributeValueException();
        }
        return inscriptions;

    }

    @Transactional
    @Override
    public Inscription addInscription(Inscription inscription, Long idUser, Long idCourse)
            throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(idUser);
        Optional<Course> course = this.courseRepository.findById(idCourse);
        if (user.isPresent() && course.isPresent()) {
            inscription.setUser(user.get());
            inscription.setCourse(course.get());
            this.inscriptionRepository.save(inscription);
        } else {
            throw new InvalidAttributeValueException();
        }
        return inscription;
    }

    @Transactional
    @Override
    public Inscription updateInscription(Boolean value, Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Inscription> inscription = this.inscriptionRepository.findById(id);
        if (inscription.isPresent()) {
            if (isEnabled(idUser, inscription.get().getUser().getId())) {
                inscription.get().setValidate(value);
                this.inscriptionRepository.save(inscription.get());
            }

        }
        return inscription.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Inscription deleteInscription(Long id, Long idUser) throws InvalidAttributeValueException {
        Optional<Inscription> inscription = this.inscriptionRepository.findById(id);
        if (inscription.isPresent()) {
            User user = inscription.get().getUser();
            if (isEnabled(idUser, user.getId())) {
                user.setInscription(null);
                this.userRepository.save(this.userRepository.findById(user.getId()).get());
            }
        }
        return inscription.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Inscription getInscriptionById(Long idUser, Long idStudent) throws InvalidAttributeValueException {
        if (isEnabled(idUser, idStudent)) {
            return this.inscriptionRepository.findById(idUser).get();
        }
        throw new InvalidAttributeValueException();

    }

    private boolean isEnabled(Long idUser, Long idStudent) {
        Optional<User> user = this.userRepository.findById(idUser);
        Optional<User> student = this.userRepository.findById(idStudent);
        Optional<Login> login = this.loginRepository.findById(idUser);
        if (user.isPresent() && student.isPresent()) {
            Set<Role> permissions = login.get().getRoles();
            if (permissions.contains(roleRepository.findByRole("ROLE_ADMIN").get())
                    || permissions.contains(roleRepository.findByRole("ROLE_PREACHER").get())) {
                return true;
            } else if (user.get().equals(student.get())) {
                return true;
            } else {
                throw new AccessDeniedException("You dont have enough permissions.");
            }
        }
        return false;
    }

}
