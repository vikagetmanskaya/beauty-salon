package com.example.diploma.service.impl;

import com.example.diploma.entity.*;
import com.example.diploma.entity.Record;
import com.example.diploma.repository.*;
import com.example.diploma.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    @Override
    public Item getById(int id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public Item getByName(String name) {
        return itemRepository.getByName(name);
    }

    @Override
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void deleteItem(int id) {
        List<Photo> photos = photoRepository.findAll();

        for (Photo photo : photos) {
            if (photo.getMasters().contains(itemRepository.getById(id).getMasters())) {
                photoRepository.delete(photo);
            }
        }
        List<Cart> carts = cartRepository.findAll();
        List<Record> records = recordRepository.findAll();
        for (Record record : records) {
            if (record.getMaster().getItem().getId() == id) {
                for (Cart cart : carts) {
                    if (cart.getRecords().contains(record)) {
                        cart.getRecords().remove(record);
                    }
                }
                recordRepository.delete(record);
            }
        }
        List<Master> masters = masterRepository.findAll();
        for (Master master : masters) {
            if (master.getItem().getId() == id) {
                masterRepository.delete(master);

            }
        }

        itemRepository.deleteById(id);
    }
}
