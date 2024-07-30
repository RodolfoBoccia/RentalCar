package view.table;

import model.Auto;
import java.util.List;

public class TabellaAuto extends TabellaGUI<Auto> {
    private Object[] nomiColonne = {"ID",
            "targa",
            "numero_posti",
            "categoria",
            "alimentazione",
            "cambio",
            "marca",
            "modello",
            "noleggiata",
            "ultima_revisione",
            "prezzo_giornaliero"};

    public TabellaAuto() {
        super("Tabella Auto");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Auto> lista) {
        modelloTabella.setRowCount(0);

        for(Auto i: lista) {
            Object[] riga = {i.getID(), i.getTarga(), i.getNumeroPosti(), i.getCategoria(), i.getAlimentazione(),
                    i.getCambio(), i.getMarca(), i.getModello(), i.isNoleggiata()?"sì":"no", i.getUltimaRevisione(), i.getPrezzoGiornaliero()};
            modelloTabella.addRow(riga);
        }
    }
}