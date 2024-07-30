package model;

public class Proprietario extends Utente {

    protected Proprietario(ProprietarioBuilder proprietarioBuilder) {
        super(proprietarioBuilder.cf, proprietarioBuilder.nome, proprietarioBuilder.cognome, proprietarioBuilder.email, proprietarioBuilder.password, proprietarioBuilder.id);
    }
}