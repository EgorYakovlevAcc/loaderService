package com.repo;

import com.model.user.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {
    void deleteAdministratorById(Integer id);
    Administrator findAdministratorByEmail(String email);

    Administrator findAdministratorByTelegramId(Integer id);
}
