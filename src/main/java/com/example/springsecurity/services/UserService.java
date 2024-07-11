package com.example.springsecurity.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.springsecurity.DTO.CreateUserDTO;
import com.example.springsecurity.entities.Role;
import com.example.springsecurity.entities.User;
import com.example.springsecurity.repositories.RoleRepository;
import com.example.springsecurity.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void AddNewUserService(CreateUserDTO userDTO) {
        var basicRole = roleRepository.findByName(Role.Values.basic.name());

        var userExists = userRepository.findByUserName(userDTO.username());

        if (userExists.isPresent())
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);

        var user = new User();

        user.setUserName(userDTO.username());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);
    }

    public List<User> listUserService() {
        var users = this.userRepository.findAll();

        return users;
    }
}
