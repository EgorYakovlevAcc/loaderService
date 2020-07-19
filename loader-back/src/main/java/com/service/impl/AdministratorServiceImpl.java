package com.service.impl;

import com.model.user.Administrator;
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
}
