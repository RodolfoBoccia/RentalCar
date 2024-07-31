package test;

import controller.ProprietarioController;

import dao.AutoDAO;
import dao.ClienteDAO;
import dao.ContrattoDAO;
import dao.ProprietarioDAO;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@Execution(ExecutionMode.SAME_THREAD)
public class ProprietarioControllerTest {

    Proprietario proprietario;
    ProprietarioController proprietarioController;
    Cliente cliente;
    Auto auto;

    public ProprietarioControllerTest() {
        ProprietarioBuilder builder = new ProprietarioBuilder();
        builder.email("email").password("password").nome("nome").cognome("cognome").cf("cf").id(999);
        this.proprietario = builder.build();
        this.proprietarioController = new ProprietarioController();
        this.proprietarioController.setProprietario(this.proprietario);
    }

    @BeforeEach
    void setUp() {
        AutoBuilder autoBuilder = new AutoBuilder();
        autoBuilder.targa("example")
                .numeroPosti(1)
                .categoria("example")
                .alimentazione("example")
                .marca("example")
                .modello("example")
                .cambio("example")
                .ultimaRevisione("2023-01-01")
                .prezzoGiornaliero(50)
                .id(999);
        this.auto = autoBuilder.build();
        ClienteBuilder clienteBuilder = new ClienteBuilder();
        clienteBuilder.cf("EXAMPLE")
                .nome("example")
                .cognome("example")
                .email("example@gmail.com")
                .password("example");
        this.cliente = clienteBuilder.build();
    }

    @AfterEach
    void tearDown() {
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
        List<Proprietario> utenti = proprietarioDAO.selectAll();
        for (Proprietario utente : utenti) {
            if (utente.getEmail().equals("example@gmail.com")) {
                proprietarioDAO.delete(utente.getId());
            }
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clienti = clienteDAO.selectAll();
        for (Cliente i : clienti) {
            if (i.getEmail().equals("example@gmail.com")) {
                clienteDAO.delete(i.getId());
            }
        }
        ContrattoDAO contrattoDAO = new ContrattoDAO();
        List<Contratto> contratti = contrattoDAO.selectAll();
        for (Contratto c : contratti) {
            if (c.getCfCliente().equals("EXAMPLE")) {
                contrattoDAO.delete(c.getID());
            }
        }
        AutoDAO autoDAO = new AutoDAO();
        List<Auto> autos = autoDAO.selectAll();
        for (Auto auto : autos) {
            if (auto.getTarga().equals("modificato")) {
                autoDAO.delete(auto.getID());
            }
        }
    }

    @Test
    void testAggiungiProprietario() {         // va fatto per forza sul DAO visto che non c'è metodo getUtente() in Controller
        ProprietarioBuilder builder = new ProprietarioBuilder();
        builder.email("example@gmail.com").password("example").nome("example").cognome("example").cf("EXAMPLE").id(1);
        ProprietarioDAO proprietarioDAO = new ProprietarioDAO();
        assertTrue(proprietarioDAO.insert(builder.build()));
        Proprietario nuovoProprietario = proprietarioDAO.select("example@gmail.com", "example");
        assertEquals(nuovoProprietario.getCf(), "EXAMPLE");
        assertEquals(nuovoProprietario.getNome(), "example");
        assertEquals(nuovoProprietario.getCognome(), "example");
        assertEquals(nuovoProprietario.getPassword(), "example");
        assertEquals(nuovoProprietario.getEmail(), "example@gmail.com");
    }

    @Test
    void testAggiungi() {
        AutoDAO autoDAO = new AutoDAO();
        //verifichiamo che l'auto venga aggiunta
        assertTrue(this.proprietarioController.aggiungiAuto(this.auto));
        // verifichiamo che l'ID sia stato aggiunto correttamente all'oggetto auto appena inserito
        List<Auto> autos = this.proprietarioController.getAllAuto();
        for (Auto i : autos) {
            if (Objects.equals(i.getTarga(), this.auto.getTarga()) && Objects.equals(i.getMarca(), this.auto.getMarca()) &&
                    Objects.equals(i.getModello(), this.auto.getModello())) {
                assertEquals(this.auto.getID(), i.getID());
                assertEquals(this.auto.getIdProprietario(), this.proprietario.getId());
            }
        }
        // verifichiamo che noleggiata sia false automaticamente
        this.auto = autoDAO.select(auto.getID());
        assertFalse(this.auto.isNoleggiata());
        // verifichiamo che il cliente sia aggiunto correttamente
        assertTrue(this.proprietarioController.aggiungiCliente(cliente));
        ContrattoBuilder contrattoBuilder = new ContrattoBuilder();
        contrattoBuilder.dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfProprietario("EXAMPLE")
                .idAuto(this.auto.getID())
                .cfCliente(this.cliente.getCf());
        Contratto contratto = contrattoBuilder.build();
        // verifichiamo che il contratto sia aggiunto correttamente
        assertTrue(this.proprietarioController.aggiungiContratto(contratto));
        // verifichiamo che dopo l'aggiunta del contratto l'auto sia noleggiata
        autos = this.proprietarioController.getAllAuto();
        for (Auto i : autos) {
            if (Objects.equals(i.getTarga(), this.auto.getTarga()) && Objects.equals(i.getMarca(), this.auto.getMarca()) &&
                    Objects.equals(i.getModello(), this.auto.getModello())) {
                assertTrue(i.isNoleggiata());
            }
        }
    }

    @Test
    void testModifica() {
        //test modifica auto
        AutoDAO autoDAO = new AutoDAO();
        auto.setTarga("modificato");

        assertTrue(proprietarioController.modificaAuto(this.auto.getID(), auto));
        this.auto = autoDAO.select(this.auto.getID());
        assertEquals(this.auto.getTarga(), "modificato");
    }

    // Qui si verifica se eliminando il cliente si elimina anche il contratto
    @Test
    void testRimuoviCliente() {
        assertTrue(this.proprietarioController.aggiungiCliente(this.cliente));

        ContrattoBuilder contrattoBuilder = new ContrattoBuilder();
        contrattoBuilder.dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfProprietario("EXAMPLE")
                .idAuto(this.auto.getID())
                .cfCliente(this.cliente.getCf());
        Contratto contratto = contrattoBuilder.build();
        assertTrue(this.proprietarioController.aggiungiContratto(contratto));

        // seleziono gli id di cliente e contratto appena inseriti e verifico che rimuovendo cliente venga
        // rimosso anche contratto
        List<Cliente> clienti = this.proprietarioController.getAllClienti();
        for (Cliente i : clienti) {
            if (i.getCf().equals("EXAMPLE")) {
                this.cliente.setId(i.getId());
            }
        }
        List<Contratto> contratti = this.proprietarioController.getAllContratti();
        for (Contratto c : contratti) {
            if (c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.proprietarioController.rimuoviCliente(this.cliente.getId()));
        // verifico che non ci sia già più il contratto //TODO vai jacopo tutto tuo
        //assertFalse(this.proprietarioController.rimuoviContratto(contratto.getID()));
    }

    // Qui si verifica se eliminando l'auto si elimina anche il contratto
    @Test
    void testRimuoviAuto() {
        assertTrue(this.proprietarioController.aggiungiCliente(this.cliente));

        ContrattoBuilder contrattoBuilder = new ContrattoBuilder();
        contrattoBuilder.dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfProprietario("EXAMPLE")
                .idAuto(this.auto.getID())
                .cfCliente(this.cliente.getCf());
        Contratto contratto = contrattoBuilder.build();

        assertTrue(this.proprietarioController.aggiungiContratto(contratto));

        // seleziono gli id di auto e contratto appena inseriti e verifico che rimuovendo auto venga
        // rimosso anche contratto
        List<Auto> autos = this.proprietarioController.getAllAuto();
        for (Auto i : autos) {
            if (i.getIdProprietario() == 1) {
                this.auto.setID(i.getID());
            }
        }
        List<Contratto> contratti = this.proprietarioController.getAllContratti();
        for (Contratto c : contratti) {
            if (c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.proprietarioController.rimuoviAuto(this.auto.getID()));
        //assertFalse(this.proprietarioController.rimuoviContratto(contratto.getID())); //TODO vai jacopo tutto tuo
    }

    @Test
    void testRimuoviContratto() {
        assertTrue(this.proprietarioController.aggiungiAuto(this.auto));

        assertTrue(this.proprietarioController.aggiungiCliente(this.cliente));

        ContrattoBuilder contrattoBuilder = new ContrattoBuilder();
        contrattoBuilder.dataFine("2025-01-01")
                .dataInizio("2023-01-01")
                .cfProprietario("EXAMPLE")
                .idAuto(this.auto.getID())
                .cfCliente(this.cliente.getCf());
        Contratto contratto = contrattoBuilder.build();

        assertTrue(this.proprietarioController.aggiungiContratto(contratto));

        List<Contratto> contratti = this.proprietarioController.getAllContratti();
        for (Contratto c : contratti) {
            if (c.getCfProprietario().equals("EXAMPLE")) {
                contratto.setID(c.getID());
            }
        }
        assertTrue(this.proprietarioController.rimuoviContratto(contratto.getID()));
    }
}