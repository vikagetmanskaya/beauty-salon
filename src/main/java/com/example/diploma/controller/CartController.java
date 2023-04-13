package com.example.diploma.controller;

import com.example.diploma.entity.Record;
import com.example.diploma.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping()
    public String getCartRecords(Model model, Principal principal) {
        List<Record> recordsInCart = cartService.getCartRecords(principal);
        model.addAttribute("recordsInCart", recordsInCart);
        return "/shopping_cart";


    }

    @DeleteMapping("/{id}")
    public String deleteFromCart(@PathVariable int id, Principal principal) {
        cartService.deleteFromCart(id, principal);
        return "redirect:/cart";

    }

    @DeleteMapping()
    public String deleteAllFromCart(Principal principal) {
        cartService.deleteAllFromCart(principal);
        return "redirect:/cart";
    }

}
