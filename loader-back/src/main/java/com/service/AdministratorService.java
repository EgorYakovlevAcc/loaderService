package com.service;

import com.model.user.Administrator;
import com.model.user.BotUser;

import java.util.List;

public interface AdministratorService {
    List<Administrator> findAll();

    void deleteAdminById(Integer adminId);

    void createAdministratorByAdministratorPojo(com.pojo.Administrator administrator);

    List<Administrator> getAdministrators();

    Administrator findAdministratorByEmail(String email);

    void createAdmin(Integer id, Long chatId);

    BotUser findAdministratorByTelegramId(Integer id);
}
