package dao;

import model.Auto;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AutoDAO extends BaseDAO<Auto>{
    public AutoDAO() {
        createTabella();
    }

    protected void createTabella() {

    }

    public boolean insert(Auto auto) {
        String sql = "INSERT INTO auto (targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, auto.getTarga());
            pstmt.setInt(2, auto.getNumeroPosti());
            pstmt.setString(3, auto.getCategoria());
            pstmt.setString(4, auto.getAlimentazione());
            pstmt.setFloat(5, auto.getPrezzoGiornaliero());
            pstmt.setBoolean(6, auto.isNoleggiata());
            pstmt.setInt(7, auto.getIdProprietario());
            pstmt.setString(9, auto.getModello());
            pstmt.setString(10, auto.getCambio());
            pstmt.setString(11, auto.getUltimaRevisione());
            pstmt.setInt(12, auto.getID());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Auto select(int id) {
        String sql = "SELECT targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, ID FROM auto WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String targa = rs.getString("targa");
                    int numeroPosti = rs.getInt("numeroPosti");
                    String categoria = rs.getString("categoria");
                    String alimentazione = rs.getString("alimentazione");
                    float prezzoGiornaliero = rs.getFloat("prezzoGiornaliero");
                    boolean noleggiata = rs.getBoolean("noleggiata");
                    int idProprietario = rs.getInt("idProprietario");
                    String marca = rs.getString("marca");
                    String modello = rs.getString("modello");
                    String cambio = rs.getString("cambio");
                    String ultimaRevisione = rs.getString("ultimaRevisione");
                    int autoId = rs.getInt("ID");

                    return new Auto(targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, autoId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Auto> selectAll() {
        String sql = "SELECT targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, ID FROM auto";
        List<Auto> autoList = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String targa = rs.getString("targa");
                int numeroPosti = rs.getInt("numeroPosti");
                String categoria = rs.getString("categoria");
                String alimentazione = rs.getString("alimentazione");
                float prezzoGiornaliero = rs.getFloat("prezzoGiornaliero");
                boolean noleggiata = rs.getBoolean("noleggiata");
                int idProprietario = rs.getInt("idProprietario");
                String marca = rs.getString("marca");
                String modello = rs.getString("modello");
                String cambio = rs.getString("cambio");
                String ultimaRevisione = rs.getString("ultimaRevisione");
                int id = rs.getInt("ID");

                Auto auto = new Auto(targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, id);
                autoList.add(auto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return autoList;
    }

    public boolean update(int id, Auto entità) {
        String sql = "UPDATE auto SET targa = ?, numeroPosti = ?, categoria = ?, alimentazione = ?, prezzoGiornaliero = ?, noleggiata = ?, idProprietario = ?, marca = ?, modello = ?, cambio = ?, ultimaRevisione = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, entità.getTarga());
            pstmt.setInt(2, entità.getNumeroPosti());
            pstmt.setString(3, entità.getCategoria());
            pstmt.setString(4, entità.getAlimentazione());
            pstmt.setFloat(5, entità.getPrezzoGiornaliero());
            pstmt.setBoolean(6, entità.isNoleggiata());
            pstmt.setInt(7, entità.getIdProprietario());
            pstmt.setString(8, entità.getMarca());
            pstmt.setString(9, entità.getModello());
            pstmt.setString(10, entità.getCambio());
            pstmt.setString(11, entità.getUltimaRevisione());
            pstmt.setInt(5, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM auto WHERE id = ?";

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
