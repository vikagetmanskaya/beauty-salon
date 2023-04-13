package com.example.diploma.repository;

import com.example.diploma.entity.Master;
import com.example.diploma.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MasterRepository extends JpaRepository<Master, Integer> {
    Master getByName(String name);

}
