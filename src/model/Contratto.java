package model;


public class Contratto {
    private final int ID;
    private final int idAuto;
    private final String cfCliente;
    private String cfProprietario;
    private final String dataInizio;
    private final String dataFine;
    private final float totale;

    protected Contratto(ContrattoBuilder contrattoBuilder) {
        this.ID = contrattoBuilder.ID;
        this.idAuto = contrattoBuilder.idAuto;
        this.cfCliente = contrattoBuilder.cfCliente;
        this.cfProprietario = contrattoBuilder.cfProprietario;
        this.dataInizio = contrattoBuilder.dataInizio;
        this.dataFine = contrattoBuilder.dataFine;
        this.totale = contrattoBuilder.totale;
    }

    public int getIdAuto() {
        return idAuto;
    }

    public String getCfCliente() {
        return cfCliente;
    }

    public String getCfProprietario() {
        return cfProprietario;
    }

    public void setCfProprietario(String cfProprietario) {
        this.cfProprietario = cfProprietario;
    }

    public String getDataInizio() {
        return dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public float getTotale() {
        return totale;
    }

    public int getID() {
        return this.ID;
    }
}