package pl.coderslab.entity;

/**
 * Klasa User do przechowywania informacji o użytkowniku
 */
public class User {
    private int id = 0;
    private String userName;
    private String password;
    private String email;

    /**
     * Zwraca nazwę użytkownika.
     *
     * @return Nazwa użytkownika
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Ustawia nazwę użytkownika.
     *
     * @param userName Nowa nazwa użytkownika
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Zwraca hasło użytkownika.
     *
     * @return Hash hasła
     */
    public String getPassword() {
        return password;
    }

    /**
     * Ustawia hasło użytkownika.
     *
     * @param password Hasło użytkownika
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Zwraca adres email użytkownika.
     *
     * @return Adres email użytkownika
     */
    public String getEmail() {
        return email;
    }

    /**
     * Ustawia adres email użytkownika.
     *
     * @param email Nowy adres email użytkownika
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Zwraca ID użytkownika.
     *
     * @return ID użytkownika
     */
    public int getId() {
        return id;
    }

    /**
     * Ustawia ID użytkownika.
     *
     * @param id Nowe id użytkownika
     */
    public void setId(int id) {
        this.id = id;
    }
}
