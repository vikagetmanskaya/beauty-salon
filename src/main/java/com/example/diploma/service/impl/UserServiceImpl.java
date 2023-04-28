package com.example.diploma.service.impl;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.ConfirmationToken;
import com.example.diploma.entity.User;
import com.example.diploma.repository.CartRepository;
import com.example.diploma.repository.ConfirmationTokenRepository;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailServiceImpl emailService;

    @Override
    public List<User> getAll(String username) {
        List<User> users = userRepository.findAll();
        if (username == null) {
            return users;
        } else if (username != null) {
            users = users.stream()
                    .filter(user -> user.getUsername().equals(username))
                    .collect(Collectors.toList());
        }
        return users;
    }

    @Override
    public User getById(int id) {
        return userRepository.getById(id);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public boolean addUser(User user) {
        User existingUser = userRepository.findByEmailIgnoreCase(user.getEmail());
        if (getByUsername(user.getUsername()) != null | user.getUsername().contains(" ")
                | existingUser != null) {
            return false;
        }

        user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getPassword()));
        user.setRole(roleRepository.getByName("ROLE_USER"));
        userRepository.save(user);
        Cart cart = new Cart();
        cart.setUser(getByUsername(user.getUsername()));
        cartRepository.save(cart);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenRepository.save(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("getvikcom@gmail.com");
        mailMessage.setText("To confirm your account, please click here : "
                + "http://localhost:8080/registration/confirm-account?token=" + confirmationToken.getConfirmToken());

        emailService.sendEmail(mailMessage);

        return true;
    }

    @Override
    public boolean confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmToken(confirmationToken);

        if (token != null) {
            User user = userRepository.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        }
        return false;

    }
}
