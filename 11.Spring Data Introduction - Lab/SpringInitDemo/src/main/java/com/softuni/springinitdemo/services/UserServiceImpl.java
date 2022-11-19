package com.softuni.springinitdemo.services;

import com.softuni.springinitdemo.models.Account;
import com.softuni.springinitdemo.models.User;
import com.softuni.springinitdemo.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void Register(String username, int age) {
        if(username.isBlank() || age < 18) {
            throw new RuntimeException("Validation failed!");
        }

        //check username unique
       User byUsername = this.userRepository.findByUsername(username);
        if (byUsername != null) {
            throw new RuntimeException("User already in use!");
        }
        //validate username + age

        //set default account
        Account account = new Account();
        User user = new User(username, age, account);

        //save user
        this.userRepository.save(user);
    }
}
