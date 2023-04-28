package com.example.diploma.controller;

import com.example.diploma.entity.User;
import com.example.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping()
    public String createNewUser(@Valid @ModelAttribute("user") User user,
                                BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.addUser(user);
            model.addAttribute("email", user.getEmail());
            return "successful_registration";
        }
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token") String token) {
        Boolean result = userService.confirmEmail(token);

        if (result == true) {
            return "account_verified";
        } else {
            return "error";
        }
    }

}
