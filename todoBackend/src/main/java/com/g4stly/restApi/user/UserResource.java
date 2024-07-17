package com.g4stly.restApi.user;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {
    
    private UserRepository userRepository;

    public UserResource(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id){
        return userRepository.findById(id).get();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User userDetails){
        User user = userRepository.findById(id).get();
        user.setUsername(userDetails.getUsername()); //! may be removed, there is no usage of it
        user.setAdmin(userDetails.isAdmin());
        user.setPassword(userDetails.getPassword());
        return userRepository.save(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable int id){
        userRepository.deleteById(id);
    }

}
