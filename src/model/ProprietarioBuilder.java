package model;

public class ProprietarioBuilder {
    protected String nome;
    protected String cognome;
    protected String cf;
    protected String email;
    protected String password;
    protected int id;

    public ProprietarioBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProprietarioBuilder cognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public ProprietarioBuilder cf(String cf) {
        this.cf = cf;
        return this;
    }

    public ProprietarioBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ProprietarioBuilder password(String password) {
        this.password = password;
        return this;
    }

    public ProprietarioBuilder id(int id) {
        this.id = id;
        return this;
    }

    public Proprietario build() {
        return new Proprietario(this);
    }
}