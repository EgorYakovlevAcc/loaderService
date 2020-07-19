package com.repo;

import com.model.user.Anonymous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousRepository extends JpaRepository<Anonymous, Integer> {
    Anonymous findAnonymousByTelegramId(Integer telegramId);
}
