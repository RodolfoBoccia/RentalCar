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
import java.util.Objects;

public class LoginController {
    private TabellaClienti tabellaClienti = new TabellaClienti();
    private TabellaAuto tabellaAuto = new TabellaAuto();
    private TabellaContratti tabellaContratti = new TabellaContratti();

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
        List<Proprietario> proprietari = proprietarioDao.selectAll(); //TODO select in query
        for (Proprietario p : proprietari) {
            if (Objects.equals(p.getEmail(), email) && Objects.equals(p.getPassword(), password)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAccountCliente(String email, String password) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            List<Cliente> clienti = clienteDAO.selectAll();
            for (Cliente c : clienti) {
                if (Objects.equals(c.getEmail(), email) && Objects.equals(c.getPassword(), password)) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean emailDisponibile(String email) { //TODO mettere per cliente fatto da controllare
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clienti = clienteDAO.selectAll();
        for (Cliente c : clienti) {
            if (Objects.equals(c.getEmail(), email)) {
                return false;
            }
        }
        return true;
    }

    public boolean aggiungiCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean esito = clienteDAO.insert(cliente);
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
}
