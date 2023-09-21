package com.example.diploma.service.impl;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.Item;
import com.example.diploma.entity.Master;
import com.example.diploma.entity.Photo;
import com.example.diploma.entity.Record;
import com.example.diploma.repository.CartRepository;
import com.example.diploma.repository.MasterRepository;
import com.example.diploma.repository.PhotoRepository;
import com.example.diploma.repository.RecordRepository;
import com.example.diploma.service.CartService;
import com.example.diploma.service.ItemService;
import com.example.diploma.service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterServiceImpl implements MasterService {
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ItemService itemService;

    @Override
    public List<Master> getAll(String service) {
        List<Master> masters = masterRepository.findAll();
        if (service == null) {
            return masters;
        } else if (service != null) {
            masters = masters.stream()
                    .filter(master -> master.getItem().getName().equals(service))
                    .collect(Collectors.toList());
        }
        return masters;
    }

    @Override
    public Master getById(int id) {
        return masterRepository.findById(id).get();
    }

    @Override
    public Master getByName(String name) {
        return masterRepository.getByName(name);
    }

    @Override
    public void saveMaster(Master master) {
        if (master.getItem() != null) {
            Item item = master.getItem();
            if (itemService.getByName(item.getName()) == null) {
                itemService.saveItem(item);
            }
            master.setItem(itemService.getByName(item.getName()));
            masterRepository.save(master);
        }

    }

    @Override
    public void deleteMaster(int id) {
        List<Photo> photos = photoRepository.findAll();

        for (Photo photo : photos) {
            if (photo.getMasters().contains(getById(id))) {
                photoRepository.delete(photo);
            }
        }
        List<Cart> carts = cartRepository.findAll();
        List<Record> records = recordRepository.findAll();
        for (Record record : records) {
            if (record.getMaster().getId() == id) {
                for (Cart cart : carts) {
                    if (cart.getRecords().contains(record)) {
                        cart.getRecords().remove(record);
                    }
                }
                recordRepository.delete(record);
            }
        }
        masterRepository.deleteById(id);

    }
}
