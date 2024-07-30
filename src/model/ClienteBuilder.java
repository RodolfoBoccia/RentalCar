package model;

public class ClienteBuilder {
    protected int id;
    protected String nome;
    protected String cognome;
    protected String cf;
    protected String email;
    protected String password;

    public ClienteBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public ClienteBuilder cf(String cf) {
        this.cf = cf;
        return this;
    }

    public ClienteBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder password(String password) {
        this.password = password;
        return this;
    }

    public Cliente build() {
        return new Cliente(this);
    }
}