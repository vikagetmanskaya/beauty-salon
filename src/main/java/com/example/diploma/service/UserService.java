package com.example.diploma.service;

import com.example.diploma.entity.User;

public interface UserService {
    User getById(int id);

    User getByUsername(String username);

    boolean addUser(User user);

    boolean confirmEmail(String confirmationToken);

    boolean addAdmin(User user);

    User getByEmail(String email);
}
