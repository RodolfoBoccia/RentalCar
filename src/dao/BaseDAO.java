package dao;

import java.sql.Connection;
import java.util.List;

public abstract class BaseDAO<T> {
    private static String url = "jdbc:mysql://localhost/rental_car";
    private static String  user = "root";
    private static String password = "";

    static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = java.sql.DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Errore di connessione al database --> " + e.getMessage());
        }
        return connection;
    }

    protected abstract void createTabella();

    public abstract boolean insert(T elemento);

    public abstract T select(int id);

    public abstract List<T> selectAll();

    public abstract void update(int id, T elemento);

    public abstract void delete(int id);
}