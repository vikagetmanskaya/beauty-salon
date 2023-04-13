package com.example.diploma.service.impl;

import com.example.diploma.entity.Role;
import com.example.diploma.repository.RoleRepository;
import com.example.diploma.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(name);
    }
}
