package model;

public class AutoBuilder {
    protected String targa;
    protected int numeroPosti;
    protected String categoria;
    protected String alimentazione;
    protected float prezzoGiornaliero;
    protected boolean noleggiata;
    protected int idProprietario;
    protected String marca;
    protected String modello;
    protected String cambio;
    protected String ultimaRevisione;
    protected int id;

    public AutoBuilder id(int id) {
        this.id = id;
        return this;
    }

    public AutoBuilder targa(String targa) {
        this.targa = targa;
        return this;
    }

    public AutoBuilder numeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
        return this;
    }

    public AutoBuilder categoria(String categoria) {
        this.categoria = categoria;
        return this;
    }

    public AutoBuilder alimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
        return this;
    }

    public AutoBuilder prezzoGiornaliero(float prezzoGiornaliero) {
        this.prezzoGiornaliero = prezzoGiornaliero;
        return this;
    }

    public AutoBuilder idProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
        return this;
    }

    public AutoBuilder marca(String marca) {
        this.marca = marca;
        return this;
    }

    public AutoBuilder modello(String modello) {
        this.modello = modello;
        return this;
    }

    public AutoBuilder cambio(String cambio) {
        this.cambio = cambio;
        return this;
    }

    public AutoBuilder ultimaRevisione(String ultimaRevisione) {
        this.ultimaRevisione = ultimaRevisione;
        return this;
    }

    public AutoBuilder noleggiata(boolean noleggiata) {
        this.noleggiata = noleggiata;
        return this;
    }

    public Auto build() {
        return new Auto(this);
    }
}