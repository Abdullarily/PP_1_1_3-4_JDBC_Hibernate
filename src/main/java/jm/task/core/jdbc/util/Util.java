package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;

public class Util {
    private static String URL = "jdbc:mysql://localhost:3306/mysql";
    private static String USER = "root";
    private static String PASSWORD = "root";

    public Connection dbConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    private static final SessionFactory SESSION_FACTORY;
    static {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .applySetting("hibernate.connection.url", "jdbc:mysql://localhost:3306/mysql")
                .applySetting("hibernate.connection.username", "root")
                .applySetting("hibernate.connection.password", "root")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .build();

        Metadata metadata = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata();

        SESSION_FACTORY = metadata.buildSessionFactory();
    }
    public static Session getSessionFactory() {
        return SESSION_FACTORY.openSession();
    }
}
