package com.udacity.jwdnd.course1.cloudstorage.services.impl;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final HashService hashService;


    public int createUser(User user, Model model) {
        if (userMapper.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("signUpError", "A user with this username already exist!");
            return -1;
        }

        int createdUser = userMapper.createUser(hashUserPassword(user));
        if (createdUser > 0) {
            model.addAttribute("signUpSuccess", true);
            log.info("User created successfully with the following values: {} ", user);
            return createdUser;
        }

        model.addAttribute("signUpError", "Something went wrong, please try again!");
        log.error("Fail in creating credential with the following values: {} ", user);

        return -1;
    }

    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }

    private User hashUserPassword(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);

        user.setPassword(hashedPassword);
        user.setSalt(encodedSalt);
        return user;
    }
}
