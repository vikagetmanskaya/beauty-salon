package com.example.diploma.service;

import com.example.diploma.entity.Item;

import java.util.List;

public interface ItemService {
    List<Item> getAll();

    Item getById(int id);

    Item getByName(String name);

    void saveItem(Item item);

    void deleteItem(int id);

}
