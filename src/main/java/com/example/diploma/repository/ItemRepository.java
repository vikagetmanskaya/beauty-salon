package com.example.diploma.repository;

import com.example.diploma.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Item getByName(String name);
}
