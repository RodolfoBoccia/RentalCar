package view.menu;

import controller.ProprietarioController;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class MenuContrattiProprietario extends Menu {
        private static ProprietarioController proprietarioController;

    public MenuContrattiProprietario(ProprietarioController proprietarioController) {
        MenuContrattiProprietario.proprietarioController = proprietarioController;
    }

    public void display() {
        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Mostra tabella contratti");
            System.out.println(" 2 - Aggiungi contratto");
            System.out.println(" 3 - Rimuovi contratto");
            System.out.println(" x - Indietro");

            String input = scanner.next();
            switch (input) {
                case "1":
                    proprietarioController.mostraContratti();
                    continue;
                case "2":
                    displayAggiungiContratto();
                    continue;
                case "3":
                    displayRimuoviContratto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public void displayAggiungiContratto() {
        String confermaInput;
        System.out.println("Vuoi creare un contratto per un cliente già registrato? (S/N)");
        confermaInput = scanner.next();

        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            proprietarioController.mostraClienti();
            System.out.println("Inserire l'ID dell cliente che vuole noleggiare l'auto (come indicato in tabella)");
            System.out.print("ID: ");
            int idCliente = scanner.nextInt();

            if (proprietarioController.isCliente(idCliente)) {
                Cliente cliente = proprietarioController.getCliente(idCliente);
                Contratto contratto = displayAggiungiContratto(cliente);
                if (contratto == null) {
                    return;
                }
                proprietarioController.aggiungiContratto(contratto);
                System.out.println("Contratto aggiunto con successo.");
            } else {
                System.out.println("Non esiste nessun cliente con l'ID selezionato.");
            }
        } else {
            MenuClienti menuClienti = new MenuClienti(proprietarioController);
            if (menuClienti.displayAggiungiCliente()){
                Cliente cliente = proprietarioController.getUltimoCliente();
                Contratto contratto = displayAggiungiContratto(cliente);
                if (contratto == null) {
                    return;
                }
                proprietarioController.aggiungiContratto(contratto);
                System.out.println("Contratto aggiunto con successo.");
            }
        }
    }

    public Contratto displayAggiungiContratto(Cliente cliente) {
        boolean termina = false;
        String cfCliente = cliente.getCf();
        String confermaInput;
        System.out.println("Inserisci i dati del contratto");

        while (!termina) {
            proprietarioController.mostraAuto();
            System.out.println("Inserire l'ID dell'auto che si vuole noleggiare (come indicato in tabella)");
            System.out.print("ID: ");
            int idAuto = getIntInput();

            while (!termina){
                if (!proprietarioController.isAuto(idAuto)) {
                System.out.println("L'auto selezionata non è presente nel database. Si desidera inserirla adesso? (S/N)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    MenuAutoProprietario menuAutoProprietario = new MenuAutoProprietario(proprietarioController);
                    Auto auto = menuAutoProprietario.displayAggiungiAuto();
                    if (auto == null) {
                        return null;
                    }
                    termina = true;
                    idAuto = auto.getID();
                } else {
                    System.out.println("Tornare al menu e cancellare l'operazione? (S/N)");
                    confermaInput = scanner.next();
                    if (confermaInput.equals("s")) {
                        return null;
                    }
                }
            } else if (proprietarioController.isNoleggiata(idAuto)) {
                System.out.println("L'auto selezionata è già noleggiata.");
                System.out.println("Tornare al menu e cancellare l'operazione? (S/N)");
                confermaInput = scanner.next();
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    return null;
                }
            }
            }
            termina = false;
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
            float totale = proprietarioController.getPrezzoGiornaliero(idAuto) * giorniNoleggio;

            System.out.print("Totale: " + totale);
            System.out.println("Confermi i dati inseriti? (S/N)");
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

    private void displayRimuoviContratto() {
        proprietarioController.mostraContratti();
        String confermaInput;
        System.out.println("Inserire l'ID del contratto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        int idContratto = getIntInput();

        if (proprietarioController.isContratto(idContratto)) {
            System.out.println("Rimuovere definitivamente il contratto con ID: " + idContratto + "? (S/N)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.rimuoviContratto(idContratto);
                System.out.println("Rimosso contratto con ID: " + idContratto);
            }
        } else {
            System.out.println("Non esiste nessun contratto con l'ID selezionato.");
        }
    }
}