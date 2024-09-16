package test;

import controller.ClienteController;
import dao.AutoDAO;
import dao.ClienteDAO;
import dao.ContrattoDAO;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteControllerTest {

    private ClienteController clienteController;
    private Cliente cliente;
    private Auto auto;
    private Contratto contratto;

    @BeforeEach
    public void setUp() {
        clienteController = new ClienteController();

        ClienteBuilder clienteBuilder = new ClienteBuilder();
        cliente = clienteBuilder.cf("CF12345")
                .nome("Mario")
                .cognome("Rossi")
                .email("mario.rossi@example.com")
                .password("password123")
                .build();
        clienteController.setCliente(cliente);

        AutoBuilder autoBuilder = new AutoBuilder();
        auto = autoBuilder.targa("AB123CD")
                .numeroPosti(5)
                .categoria("utilitaria")
                .alimentazione("benzina")
                .marca("Fiat")
                .modello("Punto")
                .cambio("manuale")
                .ultimaRevisione("2023-01-01")
                .prezzoGiornaliero(50)
                .id(999)
                .build();

        ContrattoBuilder contrattoBuilder = new ContrattoBuilder();
        contratto = contrattoBuilder.dataInizio("2023-01-01")
                .dataFine("2023-12-31")
                .idAuto(auto.getID())
                .cfCliente(cliente.getCf())
                .id(999)
                .build();
    }

    @AfterEach
    public void tearDown() {
        ClienteDAO clienteDAO = new ClienteDAO();
        AutoDAO autoDAO = new AutoDAO();
        ContrattoDAO contrattoDAO = new ContrattoDAO();

        List<Cliente> clienti = clienteDAO.selectAll();
        for (Cliente c : clienti) {
            if (c.getEmail().equals("mario.rossi@example.com")) {
                clienteDAO.delete(c.getId());
            }
        }

        List<Auto> autoList = autoDAO.selectAll();
        for (Auto a : autoList) {
            if (a.getTarga().equals("AB123CD")) {
                autoDAO.delete(a.getID());
            }
        }

        List<Contratto> contratti = contrattoDAO.selectAll();
        for (Contratto c : contratti) {
            if (c.getCfCliente().equals(cliente.getCf())) {
                contrattoDAO.delete(c.getID());
            }
        }
    }

    @Test
    public void testAggiungiContratto() {
        clienteController.aggiungiContratto(contratto);

        ContrattoDAO contrattoDAO = new ContrattoDAO();
        Contratto contrattoDalDB = contrattoDAO.select(contratto.getID());

            assertEquals(contratto.getID(), contrattoDalDB.getID());
            assertEquals(contratto.getIdAuto(), contrattoDalDB.getIdAuto());
            assertEquals(contratto.getCfCliente(), contrattoDalDB.getCfCliente());
            assertEquals(contratto.getCfProprietario(), contrattoDalDB.getCfProprietario());
            assertEquals(contratto.getDataInizio(), contrattoDalDB.getDataInizio());
            assertEquals(contratto.getDataFine(), contrattoDalDB.getDataFine());
            assertEquals(contratto.getTotale(), contrattoDalDB.getTotale());

    }



    @Test
    public void testModificaCliente() {
        Cliente clienteModificato = new ClienteBuilder()
                .cf(cliente.getCf())
                .nome("Giovanni")
                .cognome(cliente.getCognome())
                .email(cliente.getEmail())
                .password(cliente.getPassword())
                .build();

        clienteController.modificaCliente(clienteModificato);

        assertEquals("Giovanni", clienteController.getCliente().getNome());
    }

    @Test
    public void testRimuoviCliente() {
        clienteController.rimuoviUtente();

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteRimosso = clienteDAO.select(cliente.getEmail(), cliente.getPassword());
        assertNull(clienteRimosso);
    }

}
