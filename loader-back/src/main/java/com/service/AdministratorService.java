package com.service;

import com.model.user.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> findAll();

    void deleteAdminById(Integer adminId);

    void createAdministratorByAdministratorPojo(com.pojo.Administrator administrator);

    Administrator getAdministrator();

    Administrator findAdministratorByEmail(String email);

    void createAdmin(Integer id, Long chatId);
}
