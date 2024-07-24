package model;


public class Contratto {

    public Contratto(int idAuto, String cfCliente, String cfProprietario, String dataInizio, String dataFine,
                      float totale, int ID)
    {
        this.idAuto = idAuto;
        this.cfCliente = cfCliente;
        this.cfProprietario = cfProprietario;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.totale = totale;
        this.ID = ID;
    }

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

    public void setIdAuto(int idAuto) {
        this.idAuto = idAuto;
    }

    public String getCfCliente() {
        return cfCliente;
    }

    public void setCfCliente(String cfCliente) {
        this.cfCliente = cfCliente;
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

    public void setDataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
    }

    public String getDataFine() {
        return dataFine;
    }

    public void setDataFine(String dataFine) {
        this.dataFine = dataFine;
    }

    public float getTotale() {
        return totale;
    }

    public void setTotale(float totale) {
        this.totale = totale;
    }

    public int getID() {
        return this.ID;
    }

    public void setID(int id) {
        this.ID = id;
    }

    private int ID;
    private int idAuto;
    private String cfCliente;
    private String cfProprietario;
    private String dataInizio;
    private String dataFine;
    private float totale;

}
