package pl.coderslab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Klasa DbUtil do łączenia z bazą danych
 */
public class DbUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/workshop2";
    private static final String USER = "root";
    private static final String PASSWORD = "coderslab";

    /**
     * Connector z bazą danych
     * @return zwraca DriverManager
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
