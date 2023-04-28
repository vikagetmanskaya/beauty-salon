package com.example.diploma.controller;

import com.example.diploma.entity.Master;
import com.example.diploma.entity.User;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.repository.UserRepository;
import com.example.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserInfoController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping()
    public String showAllUsersByUsername(String username, Model model) {
        List<User> userList = userService.getAll(username);
        model.addAttribute("userList", userList);
        return "users";

    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("user", userService.getById(id));
        return "user_info";
    }
    @PatchMapping("/{id}")
    public String updateToAdmin(@PathVariable("id") int id) {
            User user = userRepository.getById(id);
            user.setRole(roleRepository.getByName("ROLE_ADMIN"));
            userRepository.save(user);

            return "redirect:/users/";
        }
    }
