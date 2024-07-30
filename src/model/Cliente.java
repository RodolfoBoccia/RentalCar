package model;

public class Cliente extends Utente {

    protected Cliente(ClienteBuilder clienteBuilder) {
        super(clienteBuilder.cf, clienteBuilder.nome, clienteBuilder.cognome, clienteBuilder.email, clienteBuilder.password, clienteBuilder.id);
    }
}