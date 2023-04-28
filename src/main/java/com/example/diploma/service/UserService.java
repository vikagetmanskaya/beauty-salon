package com.example.diploma.service;

import com.example.diploma.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll(String username);

    User getById(int id);

    User getByUsername(String username);

    boolean addUser(User user);

    boolean confirmEmail(String confirmationToken);

    User getByEmail(String email);
}
