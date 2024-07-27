// package com.g4stly.restApi.user;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Map;
// import java.util.Optional;
// import java.util.Set;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.dao.DataIntegrityViolationException;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import jakarta.validation.ConstraintViolation;
// import jakarta.validation.Validation;
// import jakarta.validation.Validator;

// class UserResourceTest {

//     @Mock
//     private UserRepository userRepository;

//     @Mock
//     private PasswordEncoder passwordEncoder;

//     @InjectMocks
//     private UserResource userResource;

//     private Validator validator;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         validator = Validation.buildDefaultValidatorFactory().getValidator();
//     }

//     @Test
//     void testGetAllUsers() {
//         List<User> users = new ArrayList<>();
//         users.add(new User());
//         when(userRepository.findAll()).thenReturn(users);

//         List<User> result = userResource.getAllUsers();

//         assertThat(result).hasSize(1);
//     }

//     @Test
//     void testGetUserById() {
//         User user = new User();
//         when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

//         User result = userResource.getUserById("testuser");

//         assertThat(result).isEqualTo(user);
//     }

//     @Test
//     void testCreateUser_ValidUser() {
//         User user = new User();
//         user.setPassword("validpassword");
//         user.setUsername("testuser");
        
//         when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
//         when(userRepository.save(any(User.class))).thenReturn(user);

//         ResponseEntity<?> response = userResource.createUser(user);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//         assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashedpassword");
//     }

//     @Test
//     void testCreateUser_InvalidUser() {
//         User user = new User();
//         user.setPassword("short");
//         Set<ConstraintViolation<User>> violations = validator.validate(user);

//         ResponseEntity<?> response = userResource.createUser(user);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//     }

//     @Test
//     void testCreateUser_DataIntegrityViolation() {
//         User user = new User();
//         user.setPassword("validpassword");

//         when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
//         when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

//         ResponseEntity<?> response = userResource.createUser(user);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
//     }

//     @Test
//     void testSignup_ValidUser() {
//         User user = new User();
//         user.setPassword("validpassword");
//         user.setUsername("testuser");
        
//         when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
//         when(userRepository.save(any(User.class))).thenReturn(user);

//         ResponseEntity<?> response = userResource.signup(user);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//         assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashedpassword");
//     }

//     @Test
//     void testUpdateUser() {
//         User existingUser = new User();
//         existingUser.setUsername("testuser");
//         User userDetails = new User();
//         userDetails.setPassword("newpassword");

//         when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
//         when(passwordEncoder.encode(anyString())).thenReturn("hashednewpassword");
//         when(userRepository.save(any(User.class))).thenReturn(existingUser);

//         ResponseEntity<?> response = userResource.updateUser("testuser", userDetails);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//         assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashednewpassword");
//     }

//     @Test
//     void testDeleteById() {
//         doNothing().when(userRepository).deleteById(1);

//         ResponseEntity<?> response = userResource.deleteById(1);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//     }

//     @Test
//     void testHandleValidationExceptions() {
//         MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, null);

//         ResponseEntity<Map<String, String>> response = userResource.handleValidationExceptions(ex);

//         assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//     }
// }

package com.g4stly.restApi.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class UserResourceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserResource userResource;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userResource.getAllUsers();

        assertThat(result).hasSize(1);
    }

    @Test
    void testGetUserById() {
        User user = new User();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        User result = userResource.getUserById("testuser");

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testCreateUser_ValidUser() {
        User user = new User();
        user.setPassword("validpassword");
        user.setUsername("testuser");
        
        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userResource.createUser(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashedpassword");
    }

    @Test
    void testCreateUser_InvalidUser() {
        User user = new User();
        user.setPassword("short");
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        ResponseEntity<?> response = userResource.createUser(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateUser_DataIntegrityViolation() {
        User user = new User();
        user.setPassword("validpassword");
        user.setUsername("testuser");

        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        ResponseEntity<?> response = userResource.createUser(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void testSignup_ValidUser() {
        User user = new User();
        user.setPassword("validpassword");
        user.setUsername("testuser");
        
        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<?> response = userResource.signup(user);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashedpassword");
    }

    @Test
    void testUpdateUser() {
        User existingUser = new User();
        existingUser.setUsername("testuser");
        User userDetails = new User();
        userDetails.setPassword("newpassword");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(anyString())).thenReturn("hashednewpassword");
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        ResponseEntity<?> response = userResource.updateUser("testuser", userDetails);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(((User)response.getBody()).getPassword()).isEqualTo("hashednewpassword");
    }

    @Test
    void testDeleteById() {
        doNothing().when(userRepository).deleteById(1);

        ResponseEntity<?> response = userResource.deleteById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    void testHandleValidationExceptions() {
        BindingResult bindingResult = new BindException(new Object(), "user");
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = userResource.handleValidationExceptions(ex);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}

