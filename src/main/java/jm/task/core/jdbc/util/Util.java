package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_NAME = "newbd1_23";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "rootuser";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME;

    public static Connection getConnect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static SessionFactory getSessionFactory() {
        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySetting("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .applySetting("hibernate.connection.url", DB_URL)
                    .applySetting("hibernate.connection.username", "root")
                    .applySetting("hibernate.connection.password", "rootuser")
                    .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .applySetting("hibernate.show_sql", "true").build();
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .buildMetadata();
            return metadata.buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания SessionFactory", e);
        }
    }
}
