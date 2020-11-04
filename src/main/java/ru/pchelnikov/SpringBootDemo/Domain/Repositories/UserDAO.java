package ru.pchelnikov.SpringBootDemo.Domain.Repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;
import ru.pchelnikov.SpringBootDemo.ServicesInterfaces.IUserDAO;

import java.sql.*;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
@ConditionalOnProperty(value = "useMockUserDAO", havingValue = "false")
public class UserDAO implements IUserDAO {

    private final DBConnectionFactory dbConnectionFactory;

    @Override
    public void create(User user) {
        String query = "INSERT INTO tg_user " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = dbConnectionFactory.getConnection();
            PreparedStatement statement = dbConnectionFactory.createPreparedStatement(connection, query)
        ) {
            statement.setLong(1, user.getChatId());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            Date birthdate = user.getBirthDate() == null ? null : new Date(user.getBirthDate().getTime());
            statement.setDate(5, birthdate);
            statement.setString(6, user.getPhone());
            statement.executeUpdate();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User read(Long chatId) {
        String query = "SELECT * FROM tg_user " +
                "WHERE chat_id='" + chatId + "'";

        try (Connection connection = dbConnectionFactory.getConnection();
             Statement statement = dbConnectionFactory.createStatement(connection);
             ResultSet resultSet = dbConnectionFactory.executeStatement(statement, query)
        ) {
            return dbConnectionFactory.processResultSet(resultSet).get(0);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        String query = "UPDATE tg_user " +
                "SET user_name = ?, " +
                "first_name = ?, " +
                "last_name = ?, " +
                "birth_date = ?, " +
                "phone = ?" +
                "WHERE chat_id = ?";
        try (Connection connection = dbConnectionFactory.getConnection();
             PreparedStatement statement = dbConnectionFactory.createPreparedStatement(connection, query)
        ) {
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            Date birthdate = user.getBirthDate() == null ? null : new Date(user.getBirthDate().getTime());
            statement.setDate(4, birthdate);
            statement.setString(5, user.getPhone());
            statement.setLong(6, user.getChatId());
            statement.executeUpdate();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long chatId) {
        String query = "DELETE FROM tg_user " +
                "WHERE chat_id = " + chatId;
        try (Connection connection = dbConnectionFactory.getConnection();
             Statement statement = dbConnectionFactory.createStatement(connection)
        ) {
            statement.execute(query);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean hasUser(Long chatId) {
        String query = "SELECT * FROM tg_user " +
                "WHERE chat_id=" + chatId;
        try (Connection connection = dbConnectionFactory.getConnection();
             Statement statement = dbConnectionFactory.createStatement(connection);
             ResultSet resultSet = dbConnectionFactory.executeStatement(statement, query)
        ) {
            return !dbConnectionFactory.processResultSet(resultSet).isEmpty();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM tg_user";

        try (Connection connection = dbConnectionFactory.getConnection();
            Statement statement = dbConnectionFactory.createStatement(connection);
            ResultSet resultSet = dbConnectionFactory.executeStatement(statement, query)
        ) {
            return dbConnectionFactory.processResultSet(resultSet);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
