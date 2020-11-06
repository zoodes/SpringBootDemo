package ru.pchelnikov.SpringBootDemo.Domain.Repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.pchelnikov.SpringBootDemo.Domain.Services.Entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DBConnectionFactory {
    @Value("${database.URL}")
    private String DATABASE_URL;
    @Value("${database.user}")
    private String USER;
    @Value("${database.password}")
    private String PASSWORD;

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (Throwable e) {
            throw new RuntimeException("Error while obtaining connection!");
        }
    }

    public Statement createStatement(Connection connection) {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating statement!");
        }
    }

    public PreparedStatement createPreparedStatement(Connection connection, String query) {
        try {
            return connection.prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating statement!");
        }
    }

    public ResultSet executeStatement(Statement statement, String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing statement!");
        }
    }

    public ResultSet executeStatement(PreparedStatement statement) {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing statement!");
        }
    }

    public List<User> process() throws SQLException {
        String query = getQuery();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            try {
                statement = createStatement(connection);
                try {
                    resultSet = executeStatement(statement, query);
                    return processResultSet(resultSet);
                } finally {
                    assert resultSet != null;
                    resultSet.close();
                }
            } finally {
                assert statement != null;
                statement.close();
            }
        } finally {
            try {
                assert connection != null;
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("All is very BAD!");
            }
        }
    }

    private String getQuery() {
        return "SELECT * FROM tg_user";
    }

    public List<User> processResultSet(ResultSet resultSet) throws SQLException {
        List<User> result = new ArrayList<>();
        while (resultSet.next()) {
            User user = User.builder()
                    .chatId(resultSet.getLong("chat_id"))
                    .userName(resultSet.getString("user_name"))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .birthDate(resultSet.getDate("birth_date"))
                    .phone(resultSet.getString("phone"))
                    .build();
            result.add(user);
        }
        return result;
    }
}
