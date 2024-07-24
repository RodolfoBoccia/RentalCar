package model;

import java.time.LocalDate;
import java.util.Vector;

public class Proprietario extends Utente{
    public Proprietario(String email, String psw, String nome, String cognome, String cf)
    {
        super(cf, nome, cognome, email, psw);
    }

    protected Proprietario(ProprietarioBuilder proprietarioBuilder) {
        super(proprietarioBuilder.cf, proprietarioBuilder.nome, proprietarioBuilder.cognome, proprietarioBuilder.email, proprietarioBuilder.password);
    }

}
