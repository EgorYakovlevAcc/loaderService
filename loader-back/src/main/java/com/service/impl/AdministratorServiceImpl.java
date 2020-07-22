package com.service.impl;

import com.model.user.Administrator;
import com.model.user.BotUser;
import com.repo.AdministratorRepository;
import com.service.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public List<Administrator> findAll() {
        return administratorRepository.findAll();
    }

    @Override
    public void deleteAdminById(Integer adminId) {
        administratorRepository.deleteAdministratorById(adminId);
    }

    @Override
    public void createAdministratorByAdministratorPojo(com.pojo.Administrator administratorPojo) {
        Administrator administrator = new Administrator();
        administrator.setTelegramId(administrator.getTelegramId());
        administratorRepository.save(administrator);
    }

    @Override
    public List<Administrator> getAdministrators() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator findAdministratorByEmail(String email) {
        return administratorRepository.findAdministratorByEmail(email);
    }

    @Override
    public void createAdmin(Integer id, Long chatId) {
        Administrator administrator = new Administrator();
        administrator.setChatId(chatId);
        administrator.setTelegramId(id);
        administratorRepository.save(administrator);
    }

    @Override
    public BotUser findAdministratorByTelegramId(Integer id) {
        return administratorRepository.findAdministratorByTelegramId(id);
    }
}
