package com.example.diploma.service;

import com.example.diploma.entity.Master;

import java.util.List;

public interface MasterService {
    List<Master> getAll(String service);

    Master getById(int id);

    Master getByName(String name);

    void saveMaster(Master master);

    void deleteMaster(int id);
}
