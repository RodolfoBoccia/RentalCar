package dao;

import model.Contratto;
import model.ContrattoBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContrattoDAO extends BaseDAO<Contratto> {
    public ContrattoDAO() {
        createTabella();
    }

    protected void createTabella() {
    }

    public boolean insert(Contratto contratto) {
        String sql = "INSERT INTO contratto (id, idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, contratto.getID());
            statement.setInt(2, contratto.getIdAuto());
            statement.setString(3, contratto.getCfCliente());
            statement.setString(4, contratto.getCfProprietario());
            statement.setString(5, contratto.getDataInizio());
            statement.setString(6, contratto.getDataFine());
            statement.setFloat(7, contratto.getTotale());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Contratto select(int id) {
        String sql = "SELECT id, idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale FROM contratto WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    int idAuto = rs.getInt("idAuto");
                    String cfCliente = rs.getString("cfCliente");
                    String cfProprietario = rs.getString("cfProprietario");
                    String dataInizio = rs.getString("dataInizio");
                    String dataFine = rs.getString("dataFine");
                    float totale = rs.getFloat("totale");

                    ContrattoBuilder builder = new ContrattoBuilder();
                    builder.idAuto(idAuto)
                            .cfCliente(cfCliente)
                            .cfProprietario(cfProprietario)
                            .dataInizio(dataInizio)
                            .dataFine(dataFine)
                            .totale(totale)
                            .id(id);
                    return builder.build();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Contratto> selectAll() {
        String sql = "SELECT id, idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale FROM contratto";
        List<Contratto> contrattoList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idAuto = rs.getInt("idAuto");
                String cfCliente = rs.getString("cfCliente");
                String cfProprietario = rs.getString("cfProprietario");
                String dataInizio = rs.getString("dataInizio");
                String dataFine = rs.getString("dataFine");
                float totale = rs.getFloat("totale");

                ContrattoBuilder builder = new ContrattoBuilder();
                builder.idAuto(idAuto)
                        .cfCliente(cfCliente)
                        .cfProprietario(cfProprietario)
                        .dataInizio(dataInizio)
                        .dataFine(dataFine)
                        .totale(totale)
                        .id(id);
                contrattoList.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contrattoList;
    }

    public List<Contratto> selectAll(String cfCliente) {
        String sql = "SELECT id, idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale FROM contratto WHERE cfCliente = ?";
        List<Contratto> contrattoList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, cfCliente);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int idAuto = rs.getInt("idAuto");
                    String cfProprietario = rs.getString("cfProprietario");
                    String dataInizio = rs.getString("dataInizio");
                    String dataFine = rs.getString("dataFine");
                    float totale = rs.getFloat("totale");

                    ContrattoBuilder builder = new ContrattoBuilder();
                    builder.idAuto(idAuto).cfCliente(cfCliente).cfProprietario(cfProprietario)
                            .dataInizio(dataInizio).dataFine(dataFine).totale(totale).id(id);

                    contrattoList.add(builder.build());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contrattoList;
    }

    public void update(int id, Contratto nuovoContratto) {
        String sql = "UPDATE contratto SET idAuto = ?, cfCliente = ?, cfProprietario = ?, dataInizio = ?, dataFine = ?, totale= ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, nuovoContratto.getIdAuto());
            statement.setString(2, nuovoContratto.getCfCliente());
            statement.setString(3, nuovoContratto.getCfProprietario());
            statement.setString(4, nuovoContratto.getDataInizio());
            statement.setString(5, nuovoContratto.getDataFine());
            statement.setFloat(6, nuovoContratto.getTotale());
            statement.setInt(7, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM contratto WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
