package ru.pchelnikov.SpringBootDemo.domain.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pchelnikov.SpringBootDemo.domain.services.entities.User;

import java.util.Optional;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {
    Optional<User> findDistinctFirstByPhone(String phone);
    Optional<User> findDistinctByChatId(Long chatId);
    boolean existsByChatId(Long chatId);
}
