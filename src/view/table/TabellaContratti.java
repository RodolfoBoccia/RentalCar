package view.table;

import model.Contratto;
import java.util.List;

public class TabellaContratti extends TabellaGUI<Contratto> {
    private Object[] nomiColonne = {"ID",
            "cf_cliente",
            "ID_auto",
            "data_inizio",
            "data_fine",
            "totale"};

    public TabellaContratti() {
        super("Tabella Contratti");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Contratto> lista) {
        modelloTabella.setRowCount(0);

        for(Contratto c: lista) {
            Object[] riga = {c.getID(), c.getCfCliente(), c.getIdAuto(), c.getDataInizio(), c.getDataFine(), c.getTotale()};
            modelloTabella.addRow(riga);
        }
    }

}
