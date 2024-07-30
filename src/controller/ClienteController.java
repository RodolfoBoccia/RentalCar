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
import java.util.Collections;
import java.util.List;


public class ClienteController {
    private final TabellaClienti tabellaClienti = new TabellaClienti();
    private final TabellaAuto tabellaAuto = new TabellaAuto();
    private final TabellaContratti tabellaContratti = new TabellaContratti();
    private Cliente cliente = null;

    public boolean isAuto(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto) != null;
    }

    public boolean isNoleggiata(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto).isNoleggiata();
    }

    public float getPrezzoGiornaliero(int idAuto) {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.select(idAuto).getPrezzoGiornaliero();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getListCliente() {
        return Collections.singletonList(cliente);
    }

    public List<Auto> getAllAuto() {
        AutoDAO autoDAO = new AutoDAO();
        return autoDAO.selectAll();
    }

    public List<Contratto> getAllContratti() {
        ContrattoDAO contrattoDAO = new ContrattoDAO();
        return contrattoDAO.selectAll();
    }

    public List<Contratto> getContrattiCliente() {
        ContrattoDAO contrattoDAO = new ContrattoDAO();
        return contrattoDAO.selectAll(cliente.getCf());
    }

    public void aggiungiContratto(Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contratto.setCfProprietario(cliente.getCf());
        contrattoDao.insert(contratto);
        aggiorna();
    }

    public void modificaCliente(Cliente clienteModificato) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.update(cliente.getId(), clienteModificato);
        cliente = clienteModificato;
        aggiorna();
    }

    public void rimuoviUtente() {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.delete(cliente.getId());
        aggiorna();
    }

    public void aggiorna() { //TODO controllare che deve fare
        try {
            AutoDAO autoDAO = new AutoDAO();
            List<Auto> auto = getAllAuto();
            List<Contratto> contratti = getAllContratti();
            List<Cliente> clienti = Collections.singletonList(cliente);

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

    public void mostraContrattiCliente() {
        aggiorna();
        tabellaContratti.mostraTabella(getContrattiCliente());
    }

    public void mostraCliente() {
        aggiorna();
        tabellaClienti.mostraTabella(getListCliente());
    }

    public void reset() {
        tabellaClienti.dispose();
        tabellaContratti.dispose();
        tabellaAuto.dispose();
        cliente = null;
    }
}