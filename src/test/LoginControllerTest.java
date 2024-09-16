package test;

import controller.LoginController;
import dao.ClienteDAO;
import model.Cliente;
import model.ClienteBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginControllerTest {

    private LoginController loginController;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        loginController = new LoginController();
        ClienteBuilder clienteBuilder = new ClienteBuilder();
        cliente = clienteBuilder.cf("CF12345")
                .nome("Test")
                .cognome("Cliente")
                .email("test@example.com")
                .password("password123")
                .build();
    }

    @AfterEach
    public void tearDown() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clienti = clienteDAO.selectAll();
        for (Cliente c : clienti) {
            if (c.getEmail().equals("test@example.com") || c.getEmail().equals("newtest@example.com")) {
                clienteDAO.delete(c.getId());
            }
        }
    }

    @Test
    public void testEmailDisponibile() {
        assertTrue(loginController.emailDisponibile("newtest@example.com"));

        loginController.aggiungiCliente(cliente);
        assertFalse(loginController.emailDisponibile("test@example.com"));
    }

    @Test
    public void testAggiungiCliente() {
        assertTrue(loginController.emailDisponibile("test@example.com"));

        loginController.aggiungiCliente(cliente);

        assertFalse(loginController.emailDisponibile("test@example.com"));

        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteInserito = clienteDAO.select(cliente.getEmail(), cliente.getPassword());
        assertTrue(clienteInserito != null && clienteInserito.getEmail().equals(cliente.getEmail()));
    }
}
