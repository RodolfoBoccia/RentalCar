package dao;

import model.Auto;
import model.Proprietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO extends BaseDAO<Proprietario> {
    public ProprietarioDAO() {
        createTabella();
    }

    protected void createTabella() {
    }

    public boolean insert(Proprietario proprietario) {
        String sql = "INSERT INTO proprietatio (email, password, cf, nome, cognome) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, proprietario.getEmail());
            pstmt.setString(2, proprietario.getPassword());
            pstmt.setString(3, proprietario.getCf());
            pstmt.setString(4, proprietario.getNome());
            pstmt.setString(5, proprietario.getCognome());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proprietario select(int id) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM proprietario WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");

                    return new Proprietario(email, password, nome, cognome, cf, id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Proprietario> selectAll() {
        String sql = "SELECT email, password, cf, nome, cognome,id FROM proprietario";
        List<Proprietario> proprietarioList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String cf = rs.getString("cf");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                int id = rs.getInt("id");

                Proprietario proprietario = new Proprietario(email, password, nome, cognome, cf, id);
                proprietarioList.add(proprietario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proprietarioList;
    }

    public boolean update(int id, Proprietario entità) {
        String sql = "UPDATE proprietario SET email = ?, password = ? , nome = ?, cognome = ?,cf = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entità.getEmail());
            pstmt.setString(2, entità.getPassword());
            pstmt.setString(3, entità.getNome());
            pstmt.setString(4, entità.getCognome());
            pstmt.setString(5, entità.getCf());
            pstmt.setInt(6, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM proprietario WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

