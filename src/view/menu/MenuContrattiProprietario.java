package view.menu;

import controller.Controller;
import model.Contratto;
import model.ContrattoBuilder;
import model.Auto;
import model.Cliente;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class MenuContrattiProprietario extends Menu {

    public MenuContrattiProprietario(Controller controller) {
        super(controller);
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
                    //controller.mostraContratti();
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

    public void displayAggiungiContratto() { //TODO si puo fare contratto con cliente gia esistente
        System.out.println("Per creare un contratto è necessario creare prima il cliente a cui è associato.");
        MenuClienti menuClienti = new MenuClienti(this.controller);
        menuClienti.displayAggiungiCliente();
    }

    public Contratto displayAggiungiContratto(Cliente cliente) {
        boolean termina = false;
        String cfCliente = cliente.getCf();
        String confermaInput;
        System.out.println("Inserisci i dati del contratto");

        while (!termina) {
            //controller.mostraAuto();
            System.out.print("ID dell'auto come indicato nella tabella: ");
            String idAuto = scanner.next();

            if (true) { //TODO !controller.isAuto(idAuto)
                System.out.println("L'auto selezionata non è presente nel database. Si desidera inserirla adesso? (S/n)");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    MenuAutoProprietario menuAutoProprietario = new MenuAutoProprietario(this.controller);
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
            } else if (true) { //TODO controller.isNoleggiata(idAuto)
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
            float totale =  giorniNoleggio; //TODO controller.getPrezzoGiornaliero(idAuto) *
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
        //controller.mostraContratti();
        String idContratto;
        String confermaInput;

        System.out.println("Inserire l'ID del contratto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idContratto = scanner.next();
        if (true) { //TODO controller.isContratto(idContratto)
            System.out.println("Rimuovere definitivamente il contratto con ID: " + idContratto + "?");
            //TODO togliere rimozione cliente
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                //controller.rimuoviContratto(idContratto);
                System.out.println("Rimosso contratto con ID: " + idContratto);
            }
        } else {
            System.out.println("Non esiste nessun contratto con l'ID selezionato.");
        }
    }

}
