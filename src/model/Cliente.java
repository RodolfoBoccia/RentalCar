package model;


public class Cliente extends Utente{
    private int ID;

    public Cliente(String cf, String nome, String cognome, String email, String password)
    {
        super(cf, nome, cognome, email, password);
    }

    protected Cliente(ClienteBuilder clienteBuilder) {
        super(clienteBuilder.cf, clienteBuilder.nome, clienteBuilder.cognome, clienteBuilder.email, clienteBuilder.password);
        this.ID = clienteBuilder.id;
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }
}
