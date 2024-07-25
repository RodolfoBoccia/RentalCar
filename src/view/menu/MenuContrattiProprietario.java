package view.menu;

import controller.ClienteController;
import controller.ProprietarioController;
import model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MenuContrattiProprietario extends Menu {

    public MenuContrattiProprietario(ProprietarioController proprietarioController) {
        super(proprietarioController);
    }

    public void display() {

        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi contratto");
            System.out.println(" 2 - Mostra tabella contratti");
            System.out.println(" 3 - Rimuovi contratto");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiContratto();
                    continue;
                case "2":
                    proprietarioController.mostraContratti();
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

    public void displayAggiungiContratto() { //TODO si puo fare contratto con cliente gia esistente CONTROLLARE
        String confermaInput;
        System.out.println("Vuoi creare un contratto per un cliente già registrato? (S/n)");
        confermaInput = scanner.next();

        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            proprietarioController.mostraClienti();
            String idCliente;
            System.out.println("Inserire l'ID dell cliente che si desidera modificare (come indicato in tabella)");
            System.out.print("ID: ");
            idCliente = scanner.next();

            if (proprietarioController.isCliente(idCliente)) {
                Cliente cliente = proprietarioController.getCliente(idCliente);
                Contratto contratto = displayAggiungiContratto(cliente);
                proprietarioController.aggiungiContratto(contratto);
                System.out.println("Contratto aggiunto con successo.");
            } else {
                System.out.println("Non esiste nessun cliente con l'ID selezionato.");
            }
        } else {
            MenuClienti menuClienti = new MenuClienti(this.proprietarioController);
            menuClienti.displayAggiungiCliente();
        }
    }

    public Contratto displayAggiungiContratto(Cliente cliente) {
        boolean termina = false;
        String cfCliente = cliente.getCf();
        String confermaInput;
        System.out.println("Inserisci i dati del contratto");

        while (!termina) {
            proprietarioController.mostraAuto();
            System.out.print("ID dell'auto come indicato nella tabella: ");
            String idAuto = scanner.next();

            if (!proprietarioController.isAuto(idAuto)) { //TODO controllare
                System.out.println("L'auto selezionata non è presente nel database. Si desidera inserirla adesso? (S/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    MenuAutoProprietario menuAutoProprietario = new MenuAutoProprietario(this.proprietarioController);
                    Auto auto = menuAutoProprietario.displayAggiungiAuto();
                    if (auto == null) {
                        return null;
                    }
                    idAuto = Integer.toString(auto.getID());
                } else {
                    System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
                    confermaInput = scanner.next();

                    if (confermaInput.equals("s")) {
                        termina = true;
                        continue;
                    } else {
                        continue;
                    }
                }
            } else if (proprietarioController.isNoleggiata(idAuto)) { //TODO controllare
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
            float totale = proprietarioController.getPrezzoGiornaliero(idAuto) * giorniNoleggio; //TODO controllare
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

    private void displayRimuoviContratto() {
        proprietarioController.mostraContratti();
        String idContratto;
        String confermaInput;

        System.out.println("Inserire l'ID del contratto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idContratto = scanner.next();
        if (proprietarioController.isContratto(idContratto)) { //TODO controllare
            System.out.println("Rimuovere definitivamente il contratto con ID: " + idContratto + "?");
            //TODO togliere rimozione cliente
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
