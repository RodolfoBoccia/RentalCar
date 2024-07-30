package dao;

import model.Cliente;
import model.ClienteBuilder;

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
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, cliente.getEmail());
            statement.setString(2, cliente.getPassword());
            statement.setString(3, cliente.getCf());
            statement.setString(4, cliente.getNome());
            statement.setString(5, cliente.getCognome());
            statement.setInt(6, cliente.getId());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente select(int id) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM cliente WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ClienteBuilder builder = new ClienteBuilder();
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String cf = rs.getString("cf");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");

                    builder.email(email).password(password).cf(cf).nome(nome).cognome(cognome);
                    return builder.build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente select(String email) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM cliente WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ClienteBuilder builder = new ClienteBuilder();
                    String password = rs.getString("password");
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");
                    int id = rs.getInt("id");

                    builder.email(email).password(password).cf(cf).nome(nome).cognome(cognome).id(id);
                    return builder.build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente select(String email, String password) {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM cliente WHERE email = ? AND password = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ClienteBuilder builder = new ClienteBuilder();
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");
                    int id = rs.getInt("id");

                    builder.email(email).password(password).cf(cf).nome(nome).cognome(cognome).id(id);
                    return builder.build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Cliente selectLast() {
        String sql = "SELECT email, password, cf, nome, cognome, id FROM cliente ORDER BY id DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {


            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    ClienteBuilder builder = new ClienteBuilder();
                    String nome = rs.getString("nome");
                    String cognome = rs.getString("cognome");
                    String cf = rs.getString("cf");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    int id = rs.getInt("id");

                    builder.email(email).password(password).cf(cf).nome(nome).cognome(cognome).id(id);
                    return builder.build();
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
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                ClienteBuilder builder = new ClienteBuilder();
                String email = rs.getString("email");
                String password = rs.getString("password");
                String cf = rs.getString("cf");
                String nome = rs.getString("nome");
                String cognome = rs.getString("cognome");
                int id = rs.getInt("id");

                builder.email(email).password(password).cf(cf).nome(nome).cognome(cognome).id(id);
                clienteList.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clienteList;
    }

    public void update(int id, Cliente nuovoCliente) {
        String sql = "UPDATE cliente SET email = ?, password = ? , nome = ?, cognome = ?, cf = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuovoCliente.getEmail());
            statement.setString(2, nuovoCliente.getPassword());
            statement.setString(3, nuovoCliente.getNome());
            statement.setString(4, nuovoCliente.getCognome());
            statement.setString(5, nuovoCliente.getCf());
            statement.setInt(6, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM cliente WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}