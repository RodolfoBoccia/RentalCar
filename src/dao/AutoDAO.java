package dao;

import model.Auto;
import model.AutoBuilder;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AutoDAO extends BaseDAO<Auto> {
    public AutoDAO() {
        createTabella();
    }

    protected void createTabella() {
    }

    public boolean insert(Auto auto) {
        String sql = "INSERT INTO auto (targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, auto.getTarga());
            statement.setInt(2, auto.getNumeroPosti());
            statement.setString(3, auto.getCategoria());
            statement.setString(4, auto.getAlimentazione());
            statement.setFloat(5, auto.getPrezzoGiornaliero());
            statement.setBoolean(6, auto.isNoleggiata());
            statement.setInt(7, auto.getIdProprietario());
            statement.setString(8, auto.getMarca());
            statement.setString(9, auto.getModello());
            statement.setString(10, auto.getCambio());
            statement.setString(11, auto.getUltimaRevisione());
            statement.setInt(12, auto.getID());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Auto select(int id) {
        String sql = "SELECT targa, numeroPosti, categoria, alimentazione, prezzoGiornaliero, noleggiata, idProprietario, marca, modello, cambio, ultimaRevisione, ID FROM auto WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
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
                    int idAuto = rs.getInt("ID");

                    AutoBuilder builder = new AutoBuilder();
                    builder.numeroPosti(numeroPosti).categoria(categoria).idProprietario(idProprietario)
                            .prezzoGiornaliero(prezzoGiornaliero).targa(targa).marca(marca).modello(modello).id(idAuto)
                            .cambio(cambio).alimentazione(alimentazione).ultimaRevisione(ultimaRevisione).noleggiata(noleggiata);
                    return builder.build();
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
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

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
                int idAuto = rs.getInt("ID");

                AutoBuilder builder = new AutoBuilder();
                builder.numeroPosti(numeroPosti).categoria(categoria).idProprietario(idProprietario)
                        .prezzoGiornaliero(prezzoGiornaliero).targa(targa).marca(marca).modello(modello).id(idAuto)
                        .cambio(cambio).alimentazione(alimentazione).ultimaRevisione(ultimaRevisione).noleggiata(noleggiata);

                autoList.add(builder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return autoList;
    }

    public boolean update(int id, Auto nuovaAuto) {
        String sql = "UPDATE auto SET targa = ?, numeroPosti = ?, categoria = ?, alimentazione = ?, prezzoGiornaliero = ?, noleggiata = ?, idProprietario = ?, marca = ?, modello = ?, cambio = ?, ultimaRevisione = ? WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, nuovaAuto.getTarga());
            statement.setInt(2, nuovaAuto.getNumeroPosti());
            statement.setString(3, nuovaAuto.getCategoria());
            statement.setString(4, nuovaAuto.getAlimentazione());
            statement.setFloat(5, nuovaAuto.getPrezzoGiornaliero());
            statement.setBoolean(6, nuovaAuto.isNoleggiata());
            statement.setInt(7, nuovaAuto.getIdProprietario());
            statement.setString(8, nuovaAuto.getMarca());
            statement.setString(9, nuovaAuto.getModello());
            statement.setString(10, nuovaAuto.getCambio());
            statement.setString(11, nuovaAuto.getUltimaRevisione());
            statement.setInt(12, nuovaAuto.getID());

            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String deleteAutoSql = "DELETE FROM auto WHERE id = ?";
        String deleteContrattoSql = "DELETE FROM contratto WHERE idAuto = ?";

        try (Connection conn = getConnection()) {
            // Inizia la transazione
            conn.setAutoCommit(false);

            try (PreparedStatement deleteContrattoStatement = conn.prepareStatement(deleteContrattoSql);
                 PreparedStatement deleteAutoStatement = conn.prepareStatement(deleteAutoSql)) {

                // Elimina i contratti associati all'auto
                deleteContrattoStatement.setInt(1, id);
                deleteContrattoStatement.executeUpdate();

                // Elimina l'auto
                deleteAutoStatement.setInt(1, id);
                deleteAutoStatement.executeUpdate();

                // Se tutto va bene, conferma la transazione
                conn.commit();

                // Successo
                return true;

            } catch (SQLException e) {
                // Se c'è un errore, annulla la transazione
                conn.rollback();
                e.printStackTrace();
                return false;  // Errore durante la transazione
            } finally {
                // Ripristina l'autocommit
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Errore durante la connessione
        }
    }


}