package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");

        } catch (SQLException e) {
            System.out.println("Tu się wywaliło");
            e.printStackTrace();
        }
        return connection;
    }
}
