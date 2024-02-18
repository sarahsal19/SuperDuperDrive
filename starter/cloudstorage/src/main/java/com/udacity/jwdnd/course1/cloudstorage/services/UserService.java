package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import org.springframework.ui.Model;

public interface UserService {

    int createUser(User user, Model model);
    User getUser(String username);
}
