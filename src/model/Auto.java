package model;

public class Auto {
    private String targa;
    private int numeroPosti;
    private String categoria;
    private String alimentazione;
    private float prezzoGiornaliero;
    private boolean noleggiata;
    private int idProprietario;
    private String marca;
    private String modello;
    private String cambio;
    private String ultimaRevisione;
    private int ID;

    protected Auto(AutoBuilder builder) {
        this.targa = builder.targa;
        this.numeroPosti = builder.numeroPosti;
        this.categoria = builder.categoria;
        this.alimentazione = builder.alimentazione;
        this.prezzoGiornaliero = builder.prezzoGiornaliero;
        this.idProprietario = builder.idProprietario;
        this.marca = builder.marca;
        this.modello = builder.modello;
        this.noleggiata = builder.noleggiata;
        this.cambio = builder.cambio;
        this.ultimaRevisione = builder.ultimaRevisione;
        this.ID = builder.id;
    }

    public boolean isNoleggiata() {
        return noleggiata;
    }

    public void setNoleggiata(boolean noleggiata) {
        this.noleggiata = noleggiata;
    }

    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public int getNumeroPosti() {
        return numeroPosti;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public float getPrezzoGiornaliero() {
        return prezzoGiornaliero;
    }

    public void setPrezzoGiornaliero(float prezzoGiornaliero) {
        this.prezzoGiornaliero = prezzoGiornaliero;
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public int getID() {
        return ID;
    }

    public String getCambio() {
        return cambio;
    }

    public String getUltimaRevisione() {
        return ultimaRevisione;
    }

    public void setUltimaRevisione(String ultimaRevisione) {
        this.ultimaRevisione = ultimaRevisione;
    }

    public void setID(int id) {
        this.ID = id;
    }
}