package controller;

import dao.AutoDAO;
import dao.ClienteDAO;
import dao.ContrattoDAO;
import model.Auto;
import model.Cliente;
import model.Contratto;
import model.Proprietario;
import view.table.TabellaAuto;
import view.table.TabellaClienti;
import view.table.TabellaContratti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ProprietarioController {
    private final TabellaClienti tabellaClienti = new TabellaClienti();
    private final TabellaAuto tabellaAuto = new TabellaAuto();
    private final TabellaContratti tabellaContratti = new TabellaContratti();
    private Proprietario proprietario = null;

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public boolean isNoleggiata(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto).isNoleggiata();
    }

    public boolean isCliente(int idCliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.select(idCliente) != null;
    }

    public Auto getAuto(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto);
    }

    public boolean isContratto(int idContratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        return contrattoDao.select(idContratto) != null;
    }

    public float getPrezzoGiornaliero(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto).getPrezzoGiornaliero();
    }

    public List<Auto> getAllAuto() {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.selectAll();
    }

    public List<Cliente> getAllClienti() {
        ClienteDAO clienteDao = new ClienteDAO();
        return clienteDao.selectAll();
    }

    public List<Contratto> getAllContratti() {
        ContrattoDAO contrattoDAO = new ContrattoDAO();
        return contrattoDAO.selectAll();
    }

    public boolean isAuto(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto) != null;
    }

    public void modificaAuto(int idAuto, Auto auto) {
        AutoDAO autoDAO = new AutoDAO();
        autoDAO.update(idAuto, auto);
        aggiorna();
    }

    public void aggiungiAuto(Auto auto) {
        AutoDAO autoDAO = new AutoDAO();
        auto.setIdProprietario(proprietario.getId());
        autoDAO.insert(auto);
        aggiorna();
    }

    public void rimuoviAuto(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        autoDAO.delete(idAuto);
        aggiorna();
    }

    public void modificaCliente(int idCliente, Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.update(idCliente, cliente);
        aggiorna();
    }

    public Cliente getCliente(int idCliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.select(idCliente);
    }

    public Cliente getUltimoCliente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.selectLast();
    }

    public void aggiungiCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.insert(cliente);
        aggiorna();
    }

    public void rimuoviCliente(int idCliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.delete(idCliente);
        aggiorna();
    }

    public void aggiungiContratto(Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contratto.setCfProprietario(proprietario.getCf());
        contrattoDao.insert(contratto);
        aggiorna();
    }

    public void rimuoviContratto(int idContratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contrattoDao.delete(idContratto);
        aggiorna();
    }

    public void aggiorna() { //TODO controllare che deve fare
        try {
            AutoDAO autoDAO = new AutoDAO();
            List<Auto> auto = getAllAuto();
            List<Contratto> contratti = getAllContratti();
            List<Cliente> clienti = getAllClienti();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate adesso = LocalDate.now();

            for (Auto i : auto) {   // aggiorna il campo affittato degli auto
                boolean affittato = false;
                for (Contratto c : contratti) {
                    if (i.getID() == c.getIdAuto() && !adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        affittato = true;
                        break;
                    }
                }
                i.setNoleggiata(affittato);
                autoDAO.update(i.getID(), i);
            }
            tabellaContratti.aggiornaTabella(contratti);
            tabellaAuto.aggiornaTabella(auto);
            tabellaClienti.aggiornaTabella(clienti);

        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println(e);
            System.out.println("ATTENZIONE! nel database sono presenti date in un formato non riconosciuto (formato valido: YYYY-MM-DD)");
            System.out.println("Se non si effettuano le dovute modifiche alla tabella contratti il programma " +
                    "potrebbe non funzionare correttamente");
            System.out.println("(è possibile modificare le date dei contratti dal menu gestione contratti)");
        }
    }

    public void mostraAuto() {
        aggiorna();
        tabellaAuto.mostraTabella(getAllAuto());
    }

    public void mostraClienti() {
        aggiorna();
        tabellaClienti.mostraTabella(getAllClienti());
    }

    public void mostraContratti() {
        aggiorna();
        tabellaContratti.mostraTabella(getAllContratti());
    }

    public void reset() {
        tabellaClienti.dispose();
        tabellaContratti.dispose();
        tabellaAuto.dispose();
        proprietario = null;
    }
}