package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ALL_USER_QUERY = "SELECT * FROM users";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";

    /**
     * Hashuje hasło użytkownika za pomocą algorytmu bcrypt.
     *
     * @param password Hasło użytkownika
     * @return Zhashowane hasło użytkownika
     */
    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Wstawia nowego użytkownika do bazy danych.
     *
     * @param user Obiekt użytkownika zawierający dane, które mają zostać zapisane w bazie.
     * @return Obiekt użytkownika z przypisanym ID, jeśli operacja zakończyła się sukcesem.
     * Zwraca null, jeśli wystąpił błąd podczas zapisu.
     */
    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Pobiera dane użytkownika z bazy danych.
     *
     * @param userId ID użytkownika.
     * @return Obiekt użytkownika z przypisanym ID, nazwą użytkownika, adresem email oraz hasłem.
     * Zwraca NULL, jeśli wystąpił błąd.
     */
    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(GET_USER_QUERY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Zapisuje dane użytkownika w bazie danych.
     *
     * @param user Obiekt użytkownika
     */
    public void update(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Usuwa użytkownika z bazy danych.
     *
     * @param userId ID użytkownika
     */
    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda pomocnicza do zwiększania rozmiaru tablicy obiektów User.
     *
     * @param u Obiekt użytkownika, który zostanie dodany do tablicy
     * @param users Tablica obiektów użytkowników, która zostanie zwiększona o nowego użytkownika
     * @return Nowa tablica obiektów użytkowników
     */
    private User[] addToArray(User u, User[] users) {
        User[] tmpUsers = Arrays.copyOf(users, users.length + 1);
        tmpUsers[users.length] = u;
        return tmpUsers;
    }

    /**
     * Metoda zwracająca tablicę wszystkich użytkowników z bazy danych.
     * @return Tablica obiektów wszystkich użytkowników
     */
    public User[] findAll() {
        try (Connection conn = DbUtil.getConnection())
        {
            PreparedStatement statement = conn.prepareStatement(GET_ALL_USER_QUERY);
            ResultSet rs = statement.executeQuery();
            User[] users = new User[0];
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                users = addToArray(user, users);
            }
            return users;
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
