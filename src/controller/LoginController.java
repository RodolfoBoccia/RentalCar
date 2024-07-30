package controller;

import dao.AutoDAO;
import dao.ClienteDAO;
import dao.ContrattoDAO;
import dao.ProprietarioDAO;
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

public class LoginController {
    private final TabellaClienti tabellaClienti = new TabellaClienti();
    private final TabellaAuto tabellaAuto = new TabellaAuto();
    private final TabellaContratti tabellaContratti = new TabellaContratti();

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

    public boolean isAccountProprietario(String email, String password) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        return proprietarioDao.select(email, password) != null;
    }

    public Proprietario getProprietario(String email, String password) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        return proprietarioDao.select(email, password);
    }

    public boolean isAccountCliente(String email, String password) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.select(email, password) != null;
    }

    public Cliente getCliente(String email, String password) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.select(email, password);
    }

    public boolean emailDisponibile(String email) {
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.select(email) == null;
    }

    public void aggiungiCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.insert(cliente);
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
}