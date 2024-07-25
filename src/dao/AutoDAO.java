package dao;

import model.Auto;

import java.util.List;

public class AutoDAO extends BaseDAO<Auto>{
    public AutoDAO() {
        createTabella();
    }

    protected void createTabella() {

    }

    public boolean insert(Auto entità) {
        return false;
    }

    public Auto select(int id, String cf) {
        return null;
    }

    public List<Auto> selectAll() {
        return List.of();
    }

    public boolean update(int id, Auto entità, String cf) {
        return false;
    }

    public boolean delete(int id, String cf) {
        return false;
    }
}
