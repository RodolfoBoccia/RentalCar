package view.table;

import model.Cliente;
import java.util.List;

public class TabellaClienti extends TabellaGUI<Cliente> {
    private Object[] nomiColonne = {"ID",
             "codice_fiscale",
             "nome",
             "cognome",
             "email"};

    public TabellaClienti() {
        super("Tabella Clienti");
        modelloTabella.setColumnIdentifiers(nomiColonne);
    }

    @Override
    public void aggiornaTabella(List<Cliente> lista) {
        modelloTabella.setRowCount(0);

        for(Cliente i: lista) {
            Object[] riga = {i.getId(), i.getCf(), i.getNome(), i.getCognome(), i.getEmail()};
            modelloTabella.addRow(riga);
        }
    }

}
