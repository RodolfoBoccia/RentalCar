package model;

public class Auto {

    private String targa;
    private int numeroPosti;
    private String categoria;
    private String alimentazione;
    private float prezzoGiornaliero;
    private boolean noleggiata;
    private String idProprietario;
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

    public void setNumeroPosti(int numeroPosti) {
        this.numeroPosti = numeroPosti;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getAlimentazione() {
        return alimentazione;
    }

    public void setAlimentazione(String alimentazione) {
        this.alimentazione = alimentazione;
    }

    public float getPrezzoGiornaliero() {
        return prezzoGiornaliero;
    }

    public void setPrezzoGiornaliero(float prezzoGiornaliero) {
        this.prezzoGiornaliero = prezzoGiornaliero;
    }

    public String getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(String idProprietario) {
        this.idProprietario = idProprietario;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public String getUltimaRevisione() {
        return ultimaRevisione;
    }

    public void setUltimaRevisione(String ultimaRevisione) {
        this.ultimaRevisione = ultimaRevisione;
    }
}
