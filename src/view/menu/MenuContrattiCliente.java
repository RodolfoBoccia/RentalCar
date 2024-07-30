package view.menu;

import controller.ClienteController;
import model.Contratto;
import model.ContrattoBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class MenuContrattiCliente extends Menu {

    public MenuContrattiCliente(ClienteController clienteController) {
        super(clienteController);
    }

    public void display() {
        boolean termina = false;
        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Mostra tabella contratti");
            System.out.println(" 2 - Aggiungi contratto");
            System.out.println(" x - Indietro");

            String input = scanner.next();
            switch (input) {
                case "1":
                    clienteController.mostraContrattiCliente();
                    continue;
                case "2":
                    Contratto contratto = displayAggiungiContratto();
                    if (contratto != null) {
                        clienteController.aggiungiContratto(contratto);
                        continue;
                    }
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
            System.out.println("Inserire l'ID dell'auto che si vuole noleggiare (come indicato in tabella)");
            System.out.print("ID: ");
            int idAuto = getIntInput();

            if (!clienteController.isAuto(idAuto)) {
                System.out.println("L'auto selezionata non è presente nel database.");
                System.out.println("Tornare al menu e cancellare l'operazione? (S/N)");
                confermaInput = scanner.next();
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    termina = true;
                }
                continue;

            } else if (clienteController.isNoleggiata(idAuto)) {
                System.out.println("L'auto selezionata è già noleggiata.");
                System.out.println("Tornare al menu e cancellare l'operazione? (S/N)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    termina = true;
                }
                continue;
            }
            boolean dateValide = false;
            LocalDate inizio = null;
            LocalDate fine = null;
            while (!dateValide) {
                try {
                    System.out.print("Data di inizio contratto (formato: YYYY-MM-DD): ");
                    String dataInizio = scanner.next();
                    System.out.print("Data di fine contratto (formato: YYYY-MM-DD): ");
                    String dataFine = scanner.next();

                    inizio = LocalDate.parse(dataInizio, DateTimeFormatter.ISO_LOCAL_DATE);
                    fine = LocalDate.parse(dataFine, DateTimeFormatter.ISO_LOCAL_DATE);

                    if (inizio.isAfter(fine)) {
                        System.out.println("La data di inizio non può essere successiva alla data di fine. Riprova.");
                    } else {
                        dateValide = true; // Se le date vengono parse correttamente, esci dal ciclo

                    }

                } catch (DateTimeParseException e) {
                    System.out.println("Formato data errato. Assicurati di inserire la data nel formato YYYY-MM-DD. Riprova.");
                }
            }
            long giorniNoleggio = ChronoUnit.DAYS.between(inizio, fine);// Calcola la differenza in giorni tra le due date
            float totale = clienteController.getPrezzoGiornaliero(idAuto) * giorniNoleggio;

            System.out.println("Totale: " + totale);
            System.out.println("Confermi i dati inseriti? (S/N) ");
            confermaInput = scanner.next();
            try {
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    ContrattoBuilder builder = new ContrattoBuilder();
                    builder.idAuto(idAuto).cfCliente(cfCliente).dataInizio(String.valueOf(inizio)).dataFine(String.valueOf(fine)).totale(totale);
                    return builder.build();
                }
            } catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti dei valori non validi");
            }
            System.out.println("Riprovare? (S/N)");
            confermaInput = scanner.next();
            if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                termina = true;
            }
        }
        return null;
    }
}