package com.app.church;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.management.InvalidAttributeValueException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;

import com.app.church.church.entities.users.Role;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.RoleRepository;
import com.app.church.church.repository.UserRepository;
import com.app.church.church.services.UserService;
import com.app.church.church.services.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();


    @Test
    public void testUpdateRole() throws InvalidAttributeValueException {
        //Data Test
        //Roles APP
        List<Role> roles = Arrays.asList(new Role("ROLE_USER"), new Role("ROLE_ADMIN"), new Role("ROLE_TEACHER"),
        new Role("ROLE_CONTANT"), new Role("ROLE_COORDINATOR"), new Role("ROLE_SUPERVISOR"));
        //User from the APP
        User user = new User("Juan", "Perez", "Calle pico", "602315456", "toni@pico");
        
        Role roleOne = new Role("ROLE_USER");
        Role roleTwo = new Role("ROLE_SUPERVISOR");
        Role roleThree = new Role("ROLE_TEACHER");
        Role roleFour = new Role("ROLE_CONTANT");
        Set<Role> rolUser = Set.of(roleOne, roleTwo, roleThree);
        user.getLogin().getRoles().addAll(rolUser);
        //Roles to add
        List<Role> rolesAdd = Arrays.asList(roleOne, roleFour);

        //Mock configuration
        Mockito.when(this.userRepository.findById(01L)).thenReturn(Optional.of(user));
        Mockito.when(this.roleRepository.findAll()).thenReturn(roles);
       // Mockito.when(this.roleRepository.findByRole("ROLE_USER")).thenReturn(Optional.of(roleOne));
        Mockito.when(this.roleRepository.findByRole("ROLE_CONTANT")).thenReturn(Optional.of(roleFour));

       
         User result = this.userService.updateRole(roleOne, 01L);
        


        //check results 
        assertNotNull(result);
        assertEquals(2, result.getLogin().getRoles().size());
        List<Role> roleAssert = new ArrayList<>(result.getLogin().getRoles());  
        assertEquals("ROLE_USER", roleAssert.get(0).getRole());
        assertEquals("ROLE_CONTANT", roleAssert.get(1).getRole());


    }


    
}
