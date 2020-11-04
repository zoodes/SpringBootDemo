package ru.pchelnikov.SpringBootDemo.Domain.Repositories;

import ru.pchelnikov.SpringBootDemo.Domain.DTOs.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnectionFactory {
//    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://ec2-3-210-23-22.compute-1.amazonaws.com:5432/dp3r1igkksr32";
    static final String USER = "wcwlrvcyrcewtv";
    static final String PASSWORD = "90baf5f7622452da1845870af3979f6620f484a219913e49c79afd8655d1e49c";

    public Connection getConnection() {
        try {
//            Class.forName(JDBC_DRIVER);
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

    public ResultSet executeStatement(Statement statement, String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error while executing statement!");
        }
    }

    public List<UserDTO> process() throws SQLException {
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

    private List<UserDTO> processResultSet(ResultSet resultSet) throws SQLException {
        List<UserDTO> result = new ArrayList<>();
        while (resultSet.next()) {
            UserDTO user = new UserDTO();
            user.chatId = resultSet.getLong("chat_id");
            user.userName = resultSet.getString("user_name");
            user.firstName = resultSet.getString("first_name");
            user.lastName = resultSet.getString("last_name");
            user.birthDate = resultSet.getDate("birth_date");
            user.phone = resultSet.getString("phone");
            result.add(user);
        }
        return result;
    }
}
