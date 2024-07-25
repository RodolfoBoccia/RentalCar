package dao;

import model.Cliente;

import java.util.List;

public class ClienteDAO extends BaseDAO<Cliente> {
    public ClienteDAO() {
        createTabella();
    }

    protected void createTabella() {

    }

    public boolean insert(Cliente entità) {
        return false;
    }

    public Cliente select(int id, String cf) {
        return null;
    }

    public Cliente select(String email, String password) {
        return null;
    }

    public List<Cliente> selectAll() {
        return List.of();
    }

    public boolean update(int id, Cliente entità, String cf) {
        return false;
    }

    public boolean delete(int id, String cf) {
        return false;
    }

}
