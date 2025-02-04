package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Util util = new Util();
    private void executeUpdate(String query) {
        try (Connection conn = util.dbConnect(); Statement statement = conn.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public UserDaoJDBCImpl() {
    }

    @Override
    public void createUsersTable() {
        executeUpdate("create table users.users (\n" +
                "\tid int auto_increment primary key,\n" +
                "    name varchar(50),\n" +
                "    lastName varchar(100),\n" +
                "    age int\n" +
                ");");
    }

    public void dropUsersTable() {
        executeUpdate("drop table if exists users.users");
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users.users (name, lastName, age) VALUES (?, ?, ?)";

        try (Connection conn = util.dbConnect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setInt(3, age);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = String.format("delete from users.users where id = '%d'", id);
        executeUpdate(query);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users.users";

        try (Connection conn = util.dbConnect();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(users);
        return users;
    }

    public void cleanUsersTable() {
        executeUpdate("delete from users.users");
    }
}
