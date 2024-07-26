package dao;

import java.sql.Connection;
import java.sql.SQLException;
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

    public abstract boolean insert(T entità);

    public abstract T select(int id);

    public abstract List<T> selectAll();

    public abstract boolean update(int id, T entità);

    public abstract boolean delete(int id);

}