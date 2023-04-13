package com.example.diploma.service.impl;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.Record;
import com.example.diploma.entity.User;
import com.example.diploma.repository.CartRepository;
import com.example.diploma.service.CartService;
import com.example.diploma.service.RecordService;
import com.example.diploma.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    RecordService recordService;

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public List<Record> getCartRecords(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Cart cart = getByUser(user);
        if (cart.getRecords() == null) {
            return new ArrayList<>();
        } else {
            return cart.getRecords().stream().filter(record -> !record.getRecordingDate().isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Cart getByUser(User user) {
        return cartRepository.getByUser(user);
    }

    @Override
    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public boolean addToCart(Principal principal, int id) {
        User user = userService.getByUsername(principal.getName());
        Cart cart = getByUser(user);
        Record record = recordService.getById(id);
        if (cart.getRecords() == null) {
            List<Record> records = new ArrayList<>();
            records.add(record);
            cart.setRecords(records);
            recordService.updateRecordToBusy(record);
        } else if (cart.getRecords().contains(record)
                || (record.isFree() == false)) {
            return false;
        } else {
            cart.getRecords().add(record);
            recordService.updateRecordToBusy(record);
        }

        save(cart);
        return true;
    }

    @Override
    public boolean deleteFromCart(int id, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Cart cart = getByUser(user);
        Record record = recordService.getById(id);

        if (cart.getRecords() != null && cart.getRecords().contains(record)) {
            cart.getRecords().remove(record);
            recordService.saveRecord(record);
            save(cart);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteAllFromCart(Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Cart cart = getByUser(user);
        List<Record> recordList = cart.getRecords();
        if (recordList != null) {
            for (Record record : recordList) {
                recordService.saveRecord(record);

            }
            cart.getRecords().removeAll(cart.getRecords());
            save(cart);
            return true;
        } else {
            return false;
        }
    }
}
