package dao;

import model.Contratto;

import java.util.List;

public class ContrattoDAO extends BaseDAO<Contratto> {
    public ContrattoDAO() {
        createTabella();
    }

    protected void createTabella() {
    }

    public boolean insert(Contratto entità) {
        return false;
    }

    public Contratto select(int id, String cf) {
        return null;
    }

    public List<Contratto> selectAll() {
        return List.of();
    }

    public boolean update(int id, Contratto entità, String cf) {
        return false;
    }

    public boolean delete(int id, String cf) {
        return false;
    }
}
