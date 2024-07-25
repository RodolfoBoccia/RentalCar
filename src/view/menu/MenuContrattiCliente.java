package view.menu;

import controller.ClienteController;
import model.Contratto;
import model.ContrattoBuilder;
import model.Auto;
import model.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MenuContrattiCliente extends Menu {

    public MenuContrattiCliente(ClienteController clienteController) {
        super(clienteController);
    }

    public void display() {

        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi contratto");
            System.out.println(" 2 - Mostra tabella contratti");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    clienteController.aggiungiContratto(displayAggiungiContratto());
                    continue;
                case "2":
                    clienteController.mostraContrattiUtente(); //TODO controllare
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public Contratto displayAggiungiContratto() {
        boolean termina = false;
        String cfCliente = clienteController.getCliente().getCf();
        String confermaInput;
        System.out.println("Inserisci i dati del contratto");

        while (!termina) {
            clienteController.mostraAuto();
            System.out.print("ID dell'auto come indicato nella tabella: ");
            String idAuto = scanner.next();

            if (!clienteController.isAuto(idAuto)) {  //TODO controllare
                System.out.println("L'auto selezionata non è presente nel database.");

                System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s")) {
                    termina = true;
                    continue;
                } else {
                    continue;
                }

            } else if (clienteController.isNoleggiata(idAuto)) { //TODO controllare
                System.out.println("L'auto selezionata è già affittata.");
                System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    termina = true;
                    continue;
                } else {
                    continue;
                }
            }

            System.out.print("Data di inizio contratto (formato: YYYY-MM-DD): ");
            String dataInizio = scanner.next();
            System.out.print("Data di fine contratto (formato: YYYY-MM-DD): ");
            String dataFine = scanner.next();

            LocalDate inizio = LocalDate.parse(dataInizio, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate fine = LocalDate.parse(dataFine, DateTimeFormatter.ISO_LOCAL_DATE);

            long giorniNoleggio = ChronoUnit.DAYS.between(inizio, fine);// Calcola la differenza in giorni tra le due date
            float totale = clienteController.getPrezzoGiornaliero(idAuto) *giorniNoleggio; //TODO controllare
            System.out.print("Totale: " + totale);

            System.out.println("Confermi i dati inseriti? (S/n) ");
            confermaInput = scanner.next();
            try {
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    ContrattoBuilder builder = new ContrattoBuilder();
                    builder.idAuto(Integer.parseInt(idAuto))
                            .cfCliente(cfCliente)
                            .dataInizio(dataInizio)
                            .dataFine(dataFine)
                            .totale(totale);
                    Contratto contratto = builder.build();
                    return contratto;
                }
            } catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti dei valori non validi");
            }

            System.out.println("Riprovare? (S/n)");
            confermaInput = scanner.next();

            if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                termina = true;
            }
        }
        return null;
    }

}
