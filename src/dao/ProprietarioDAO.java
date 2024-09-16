package dao;

import model.Proprietario;
import model.ProprietarioBuilder;

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
        String sql = "INSERT INTO proprietario (email, password, cf, nome, cognome) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, proprietario.getEmail());
            statement.setString(2, proprietario.getPassword());
            statement.setString(3, proprietario.getCf());
            statement.setString(4, proprietario.getNome());
            statement.setString(5, proprietario.getCognome());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Proprietario select(int id) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM proprietario WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");

                    ProprietarioBuilder builder = new ProprietarioBuilder();
                    builder.email(email).password(password).nome(nome).cognome(cognome).cf(cf).id(id);

                    return builder.build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Proprietario select(String email, String password) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM proprietario WHERE email = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");
                    int id = rs.getInt("id");

                    ProprietarioBuilder builder = new ProprietarioBuilder();
                    builder.email(email).password(password).nome(nome).cognome(cognome).cf(cf).id(id);

                    return builder.build();
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
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String cf = rs.getString("cf");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                int id = rs.getInt("id");

                ProprietarioBuilder builder = new ProprietarioBuilder();
                builder.email(email).password(password).nome(nome).cognome(cognome).cf(cf).id(id);
                proprietarioList.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proprietarioList;
    }

    public boolean update(int id, Proprietario nuovoProprietario) {
        String sql = "UPDATE proprietario SET email = ?, password = ? , nome = ?, cognome = ?,cf = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuovoProprietario.getEmail());
            statement.setString(2, nuovoProprietario.getPassword());
            statement.setString(3, nuovoProprietario.getNome());
            statement.setString(4, nuovoProprietario.getCognome());
            statement.setString(5, nuovoProprietario.getCf());
            statement.setInt(6, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM proprietario WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}