package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.User;
import com.dbms.project.moovi.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class UserService extends Utils {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user")
    public Iterable<User> findAllUsers(@RequestParam(name = "username", required = false) String username) {
        if (username != null)
            return userRepository.findUserByUsername(username);
        return userRepository.findAll();
    }
}
