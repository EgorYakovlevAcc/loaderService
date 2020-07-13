package com.repo;

import com.model.user.Porter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PorterRepository extends JpaRepository<Porter, Integer> {
    Porter findPorterById(Integer id);
    Porter findPorterByChatId(Long chatId);
    Porter findPorterByTelegramId(Integer telegramId);
}
