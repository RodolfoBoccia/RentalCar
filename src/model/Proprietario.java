package model;

import java.time.LocalDate;
import java.util.Vector;

public class Proprietario extends Utente{

    private int ID;
    public Proprietario(String email, String psw, String nome, String cognome, String cf, int id)
    {
        super(cf, nome, cognome, email, psw);
        this.ID = id;
    }

    protected Proprietario(ProprietarioBuilder proprietarioBuilder) {
        super(proprietarioBuilder.cf, proprietarioBuilder.nome, proprietarioBuilder.cognome, proprietarioBuilder.email, proprietarioBuilder.password);
    }

    public int getID() {
        return ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

}
