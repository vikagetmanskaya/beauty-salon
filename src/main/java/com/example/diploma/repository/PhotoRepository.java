package com.example.diploma.repository;

import com.example.diploma.entity.Master;
import com.example.diploma.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {
}
