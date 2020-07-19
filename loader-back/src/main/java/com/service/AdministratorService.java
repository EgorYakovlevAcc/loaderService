package com.service;

import com.model.user.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> findAll();

    void deleteAdminById(Integer adminId);
}
