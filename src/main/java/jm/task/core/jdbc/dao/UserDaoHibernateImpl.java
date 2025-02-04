package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users (id int NOT NULL AUTO_INCREMENT, " +
                    "name VARCHAR(45) NOT NULL, lastName VARCHAR(45) NOT NULL, " +
                    "age TINYINT NOT NULL, PRIMARY KEY (id))").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory()) {


            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users").executeUpdate();
            session.getTransaction().commit();

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = Util.getSessionFactory()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory()) {
            session.beginTransaction();
            session.createSQLQuery(String.format("DELETE FROM Users WHERE id = '%d'", id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> users = query.list();
            session.getTransaction().commit();
            return users;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
