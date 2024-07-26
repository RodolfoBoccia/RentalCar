package dao;

import model.Cliente;
import model.Contratto;

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
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, contratto.getID());
            pstmt.setInt(2, contratto.getIdAuto());
            pstmt.setString(3, contratto.getCfCliente());
            pstmt.setString(4, contratto.getCfProprietario());
            pstmt.setString(5, contratto.getDataInizio());
            pstmt.setString(6, contratto.getDataFine());
            pstmt.setFloat(7, contratto.getTotale());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Contratto select(int id) {
        String sql = "SELECT id, idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale FROM contratto WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idAuto = rs.getInt("idAuto");
                    String cfCliente = rs.getString("cfCliente");
                    String cfProprietario = rs.getString("cfProprietario");
                    String dataInizio = rs.getString("dataInizio");
                    String dataFine = rs.getString("dataFine");
                    float totale = rs.getFloat("totale");

                    return new Contratto(idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale, id);
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
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int idAuto = rs.getInt("idAuto");
                String cfCliente = rs.getString("cfCliente");
                String cfProprietario = rs.getString("cfProprietario");
                String dataInizio = rs.getString("dataInizio");
                String dataFine = rs.getString("dataFine");
                float totale = rs.getFloat("totale");

                Contratto contratto = new Contratto(idAuto, cfCliente, cfProprietario, dataInizio, dataFine, totale, id);
                contrattoList.add(contratto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return contrattoList;
    }

    public boolean update(int id, Contratto entità) {
        String sql = "UPDATE contratto SET idAuto = ?, cfCliente = ?, cfProprietario = ?, dataInizio = ?, dataFine = ?, totale= ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, entità.getIdAuto());
            pstmt.setString(2, entità.getCfCliente());
            pstmt.setString(3, entità.getCfProprietario());
            pstmt.setString(4, entità.getDataInizio());
            pstmt.setString(5, entità.getDataFine());
            pstmt.setFloat(6, entità.getTotale());
            pstmt.setInt(7, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM contratto WHERE id = ?";

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
