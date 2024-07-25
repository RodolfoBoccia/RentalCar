package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class BaseDAO<T> {

    protected Connection connection = null;

    public void connect() {
        try{
            // Verifico che i Driver siano nelle External Libraries
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Creo la connessione
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://localhost/gestionale_affitti", "root","");

        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Errore di connessione al database --> " + e.getMessage()); //e.printStackTrace();
        }
    }


    protected abstract void createTabella();

    public abstract boolean insert(T entità);

    public abstract T select(int id, String cf);

    public abstract List<T> selectAll();

    public abstract boolean update(int id, T entità, String cf);

    public abstract boolean delete(int id, String cf);

}