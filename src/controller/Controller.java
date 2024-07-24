package controller;


import model.*;
import view.table.TabellaContratti;
import view.table.TabellaAuto;
import view.table.TabellaClienti;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;


public class Controller {
    private TabellaClienti tabellaClienti = new TabellaClienti();
    private TabellaAuto tabellaAuto = new TabellaAuto();
    private TabellaContratti tabellaContratti = new TabellaContratti();
    private Proprietario proprietario = null;
    private boolean loggatoCliente = false;

    public boolean isCliente(String id) {
        return false;
    }

    public boolean isLoggatoCliente() {
        return loggatoCliente;
    }

    public void setLoggatoCliente() {
        this.loggatoCliente = true;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }
    public void reset() {
        tabellaClienti.dispose();
        tabellaContratti.dispose();
        tabellaAuto.dispose();
        proprietario = null;
    }

}
