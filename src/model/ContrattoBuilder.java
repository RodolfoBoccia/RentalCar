package model;

public class ContrattoBuilder {
    protected int ID;
    protected int idAuto;
    protected String cfCliente;
    protected String cfProprietario;
    protected String dataInizio;
    protected String dataFine;
    protected float totale;

    public ContrattoBuilder id(int id) {
        this.ID = id;
        return this;
    }

    public ContrattoBuilder idAuto(int idAuto) {
        this.idAuto = idAuto;
        return this;
    }

    public ContrattoBuilder cfCliente(String cfCliente) {
        this.cfCliente = cfCliente;
        return this;
    }

    public ContrattoBuilder cfProprietario(String cfProprietario) {
        this.cfProprietario = cfProprietario;
        return this;
    }

    public ContrattoBuilder dataInizio(String dataInizio) {
        this.dataInizio = dataInizio;
        return this;
    }

    public ContrattoBuilder dataFine(String dataFine) {
        this.dataFine = dataFine;
        return this;
    }

    public ContrattoBuilder totale(float totale) {
        this.totale = totale;
        return this;
    }

    public Contratto build() {
        return new Contratto(this);
    }

}
