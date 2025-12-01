package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_USERS_TABLE = """
            CREATE TABLE IF NOT EXISTS users (
                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(100) NOT NULL,
                lastName VARCHAR(100) NOT NULL,
                age TINYINT(255) NOT NULL        
            )
            """;
    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS users";
    private static final String DEL_USER = """
                DELETE FROM users
                WHERE id = ?
            """;
    private static final String INSERT_USER = """
                INSERT INTO users (name, lastName, age)
                VALUES (?, ?, ?)
            """;
    private static final String ALL_USERS = """
            SELECT name, lastName, age FROM users""";
    private static final String CLEAR_TABLE = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection con = Util.getConnect(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(CREATE_USERS_TABLE);
            //System.out.println("Таблица users создана (если её не было).");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection con = Util.getConnect(); Statement stmt = con.createStatement()) {
            stmt.executeUpdate(DROP_USERS_TABLE);
            // System.out.println("Таблица users удалена (если она существовала).");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(INSERT_USER)) {
            prStm.setString(1, name);
            prStm.setString(2, lastName);
            prStm.setInt(3, age);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
            int rows = prStm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void removeUserById(long id) {
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(DEL_USER)) {
            prStm.setInt(1, (int) id);
            // System.out.println("User c id - " + id + " удален.");
            int rows = prStm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection con = Util.getConnect(); PreparedStatement prStm = con.prepareStatement(ALL_USERS)) {
            ResultSet rSet = prStm.executeQuery();
            while (rSet.next()) {
                User user = new User(
                        rSet.getString("name"),
                        rSet.getString("lastName"),
                        rSet.getByte("age")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection con = Util.getConnect(); Statement stm = con.createStatement()) {
            stm.executeUpdate(CLEAR_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
