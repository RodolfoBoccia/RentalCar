package controller;


import dao.AutoDAO;
import dao.ClienteDAO;
import dao.ContrattoDAO;
import model.*;
import view.table.TabellaContratti;
import view.table.TabellaAuto;
import view.table.TabellaClienti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;


public class ClienteController {
    private TabellaClienti tabellaClienti = new TabellaClienti();
    private TabellaAuto tabellaAuto = new TabellaAuto();
    private TabellaContratti tabellaContratti = new TabellaContratti();
    private Cliente cliente = null;

    public boolean isAuto(String idAuto) {
        try {
            AutoDAO autoDAO = new AutoDAO();
            return autoDAO.select(Integer.parseInt(idAuto)) != null;
        } catch (NumberFormatException e) {
            System.out.println();
        }
        return false;
    }

    public boolean isNoleggiata(String idAuto) { //TODO
        try {
            AutoDAO autoDAO = new AutoDAO();
            return autoDAO.select(Integer.parseInt(idAuto)).isNoleggiata();
        } catch (NumberFormatException e) {
            System.out.println("L'ID auto non è valido");
        }
        return false;
    }

    public float getPrezzoGiornaliero(String idAuto) {
        try {
            AutoDAO autoDAO = new AutoDAO();
            return autoDAO.select(Integer.parseInt(idAuto)).getPrezzoGiornaliero();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return 0;
    }

    public Cliente getCliente() {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            return clienteDAO.select(cliente.getID());
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return null;
    }

    public void setCliente(int id) {
        ClienteDAO clienteDAO = new ClienteDAO();
        cliente = clienteDAO.select(id);
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

    public boolean aggiungiContratto(Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contratto.setCfProprietario(cliente.getCf());
        try {
            LocalDate oggi = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        } catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("ATTENZIONE! sono stati inseriti valori non validi per le date");
            System.out.println("Per poter utilizzare tutte le funzionalità del programma " +
                    "è necessario modificarle dal menu gestione contratti");
        }
        boolean esito = contrattoDao.insert(contratto);
        aggiorna();
        return esito;
    }

    public boolean modificaCliente(Cliente nuocoCliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean esito = clienteDAO.update(0, nuocoCliente);
        cliente = nuocoCliente;
        aggiorna();
        return esito;
    }

    public boolean rimuoviUtente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean esito = clienteDAO.delete(0);
        aggiorna();
        return esito;
    }

    public void aggiorna() { //TODO controllare che cazz deve fare
        try {
            AutoDAO autoDAO = new AutoDAO();
            ClienteDAO clienteDAO = new ClienteDAO();
            ContrattoDAO contrattoDAO = new ContrattoDAO();

            List<Auto> auto = getAllAuto();
            List<Contratto> contratti = getAllContratti();
            List<Cliente> inquilini = getAllClienti();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate adesso = LocalDate.now();

            // aggiorna il campo affittato degli auto
            for (Auto i : auto) {
                boolean affittato = false;
                for (Contratto c : contratti) {
                    if (i.getID() == c.getIdAuto() && !adesso.isAfter(LocalDate.parse(c.getDataFine(), formatter))) {
                        affittato = true;
                    }
                }
                i.setNoleggiata(affittato);
                autoDAO.update(i.getID(), i);
            }


            tabellaContratti.aggiornaTabella(contratti);
            tabellaAuto.aggiornaTabella(auto);
            tabellaClienti.aggiornaTabella(inquilini);

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

    public void mostraContrattiUtente() {
        aggiorna();
        tabellaContratti.mostraTabella(getAllContratti()); //TODO controllare solo per utente loggato
    }

    public void reset() {
        tabellaClienti.dispose();
        tabellaContratti.dispose();
        tabellaAuto.dispose();
        cliente = null;
    }

}
