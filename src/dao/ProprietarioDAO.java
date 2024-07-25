package dao;

import model.Proprietario;

import java.util.List;

public class ProprietarioDAO extends BaseDAO<Proprietario> {
    public ProprietarioDAO() {
        createTabella();
    }

    protected void createTabella() {
    }

    public boolean insert(Proprietario entità) {
        return false;
    }

    public Proprietario select(int id, String cf) {
        return null;
    }

    public Proprietario select(String email, String password) {
        return null;
    }

    public List<Proprietario> selectAll() {
        return List.of();
    }

    public boolean update(int id, Proprietario entità, String cf) {
        return false;
    }

    public boolean delete(int id, String cf) {
        return false;
    }
}
