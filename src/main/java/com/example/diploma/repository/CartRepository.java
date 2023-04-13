package com.example.diploma.repository;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    public Cart getByUser(User user);

}
