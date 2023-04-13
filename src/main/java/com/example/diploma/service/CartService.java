package com.example.diploma.service;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.Record;
import com.example.diploma.entity.User;

import java.security.Principal;
import java.util.List;

public interface CartService {
    List<Cart> getAll();
    List<Record> getCartRecords(Principal principal);
    Cart getByUser(User user);
    void save(Cart cart);
    boolean addToCart(Principal principal, int id);
    boolean deleteFromCart(int id,Principal principal);
    boolean deleteAllFromCart(Principal principal);
}
