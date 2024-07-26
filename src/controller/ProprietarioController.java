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

public class ProprietarioController {
    private TabellaClienti tabellaClienti = new TabellaClienti();
    private TabellaAuto tabellaAuto = new TabellaAuto();
    private TabellaContratti tabellaContratti = new TabellaContratti();
    private Proprietario proprietario = null;
    private boolean loggatoProprietario = false;

    public boolean isProprietario(String id) {
        return false;
    }

    public void setProprietario(int id) {
        ProprietarioDAO proprietarioDao = new ProprietarioDAO();
        proprietario = proprietarioDao.select(id);
    }

    public boolean isLoggatoProprietario() {
        return loggatoProprietario;
    }

    public void setLoggatoProprietario() {
        this.loggatoProprietario = true;
    }

    public Proprietario getProprietario() {
        return proprietario;
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

    public boolean isCliente(String idCLiente) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            return clienteDAO.select(Integer.parseInt(idCLiente)) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return false;
    }

    public boolean isContratto(String idContratto) {
        try {
            ContrattoDAO contrattoDao = new ContrattoDAO();
            return contrattoDao.select(Integer.parseInt(idContratto)) != null;
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
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

    public boolean isAuto(String idAuto) {
        try {
            AutoDAO autoDAO = new AutoDAO();
            return autoDAO.select(Integer.parseInt(idAuto)) != null;
        } catch (NumberFormatException e) {
            System.out.println();
        }
        return false;
    }

    public boolean modificaAuto(String idAuto, Auto auto) {
        AutoDAO autoDAO = new AutoDAO();
        boolean esito = autoDAO.update(Integer.parseInt(idAuto), auto);
        aggiorna();
        return esito;
    }

    public boolean aggiungiAuto(Auto auto) {
        AutoDAO autoDAO = new AutoDAO();
        auto.setIdProprietario(proprietario.getID());
        boolean esito = autoDAO.insert(auto);
        List<Auto> autos = autoDAO.selectAll();
        for(Auto i: autos) {
            if(Objects.equals(i.getTarga(), auto.getTarga()) && Objects.equals(i.getMarca(), auto.getMarca()) &&
                    Objects.equals(i.getModello(), auto.getModello())) {
                auto.setID(i.getID());
            }
        }
        aggiorna();
        return esito;
    }

    public boolean rimuoviAuto(String idAuto) {
        boolean esito = false;
        try {
            AutoDAO autoDAO = new AutoDAO();
            esito = autoDAO.delete(Integer.parseInt(idAuto));
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return esito;
    }

    public boolean modificaCliente(String idCliente, Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean esito = clienteDAO.update(Integer.parseInt(idCliente), cliente);
        aggiorna();
        return esito;
    }

    public Cliente getCliente(String idCliente) {
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            return clienteDAO.select(Integer.parseInt(idCliente));
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return null;
    }

    public boolean aggiungiCliente(Cliente cliente) {
        ClienteDAO clienteDAO = new ClienteDAO();
        boolean esito = clienteDAO.insert(cliente);
        aggiorna();
        return esito;
    }

    public boolean rimuoviCliente(String idCliente) {
        boolean esito = false;
        try {
            ClienteDAO clienteDAO = new ClienteDAO();
            esito = clienteDAO.delete(Integer.parseInt(idCliente));
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
        return esito;
    }

    public boolean aggiungiContratto(Contratto contratto) {
        ContrattoDAO contrattoDao = new ContrattoDAO();
        contratto.setCfProprietario(proprietario.getCf());
        try {
            LocalDate oggi = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }catch (DateTimeParseException | NumberFormatException e) {
            System.out.println("ATTENZIONE! sono stati inseriti valori non validi per le date");
            System.out.println("Per poter utilizzare tutte le funzionalità del programma " +
                    "è necessario modificarle dal menu gestione contratti");
        }
        boolean esito = contrattoDao.insert(contratto);
        aggiorna();
        return esito;
    }

    public boolean rimuoviContratto(String idContratto) {
        boolean esito = false;
        try {
            ContrattoDAO contrattoDao = new ContrattoDAO();
            esito = contrattoDao.delete(Integer.parseInt(idContratto));
            aggiorna();
        } catch (NumberFormatException e) {
            System.out.println("L'ID inserito non è un valore valido");
        }
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
