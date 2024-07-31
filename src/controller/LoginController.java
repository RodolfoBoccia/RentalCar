package controller;

import dao.ClienteDAO;
import dao.ProprietarioDAO;
import model.Cliente;
import model.Proprietario;
import view.table.TabellaClienti;

import java.util.List;

public class LoginController {
    private final TabellaClienti tabellaClienti = new TabellaClienti();

    public List<Cliente> getAllClienti() {
        ClienteDAO clienteDao = new ClienteDAO();
        return clienteDao.selectAll();
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

    public void aggiorna() {
        List<Cliente> clienti = getAllClienti();
        tabellaClienti.aggiornaTabella(clienti);

    }
}