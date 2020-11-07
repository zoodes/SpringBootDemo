package ru.pchelnikov.SpringBootDemo.Domain.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;

@Repository
public interface UserCrudRepository extends CrudRepository<User, Long> {
}
