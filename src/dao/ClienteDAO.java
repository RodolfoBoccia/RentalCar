package dao;

import model.Cliente;
import model.Proprietario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends BaseDAO<Cliente> {
    public ClienteDAO() {
        createTabella();
    }

    protected void createTabella() {

    }

    public boolean insert(Cliente cliente) {
        String sql = "INSERT INTO cliente (email, password, cf, nome, cognome, id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cliente.getEmail());
            pstmt.setString(2, cliente.getPassword());
            pstmt.setString(3, cliente.getCf());
            pstmt.setString(4, cliente.getNome());
            pstmt.setString(5, cliente.getCognome());
            pstmt.setInt(6, cliente.getID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente select(int id) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM proprietario WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String cf = rs.getString("cf");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    return new Cliente(email, password, nome, cognome, cf, id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Cliente> selectAll() {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM cliente";
        List<Cliente> clienteList = new ArrayList<>();

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

                Cliente cliente = new Cliente(email, password, nome, cognome, cf, id);
                clienteList.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clienteList;
    }

    public boolean update(int id, Cliente entità) {
        String sql = "UPDATE cliente SET email = ?, password = ? , nome = ?, cognome = ?, cf = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entità.getEmail());
            pstmt.setString(2, entità.getPassword());
            pstmt.setString(3, entità.getNome());
            pstmt.setString(4, entità.getCognome());
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

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
