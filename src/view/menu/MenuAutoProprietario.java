package view.menu;

import controller.Controller;
import model.Auto;
import model.AutoBuilder;


public class MenuAutoProprietario extends Menu {
    private static String listaOpzioniModifica = " 1 - Targa\n" +
            " 2 - Prezzo giornaliero\n" +
            " 3 - Ultima revisione\n" + //TODO aggiungere revisione controllare
            " x - Applica modifiche";

    public MenuAutoProprietario(Controller controller) {
        super(controller);
    }

    public void display() {

        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi auto");
            System.out.println(" 2 - Mostra tabella auto");
            System.out.println(" 3 - Modifica auto");
            System.out.println(" 4 - Rimuovi auto");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiAuto();
                    continue;
                case "2":
                    //controller.mostraAuto();
                    continue;
                case "3":
                    displayModificaAuto();
                    continue;
                case "4":
                    displayRimuoviAuto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public Auto
    displayAggiungiAuto() {
        boolean termina = false;
        AutoBuilder builder = new AutoBuilder();

        while (!termina) {
            String confermaInput;
            try { //TODO correggere inserimento dati controllare
                System.out.println("Inserire i dati dell'auto");
                System.out.print("Targa: ");
                String targa = scanner.nextLine();
                System.out.print("Marca: ");
                String marca = scanner.nextLine();
                System.out.print("Modello: ");
                String modello = scanner.nextLine();
                System.out.print("Cambio: ");
                String cambio = scanner.next();
                System.out.println("Alimentazione: ");
                String alimentazione = scanner.next();
                System.out.print("Numero posti: ");
                String numeroPosti = scanner.next();
                System.out.print("Categoria: ");
                String categoria = scanner.next();
                System.out.print("Prezzo giornaliero: ");
                String prezzoGiornaliero = scanner.next();
                System.out.print("Data ultima revisione: ");
                String ultimaRevisione = scanner.next();

                builder.numeroPosti(Integer.parseInt(numeroPosti)).categoria(categoria)
                        .prezzoGiornaliero(Float.parseFloat(prezzoGiornaliero)).targa(targa).marca(marca).modello(modello)
                        .cambio(cambio).alimentazione(alimentazione).ultimaRevisione(ultimaRevisione).noleggiata(false);

                System.out.println("Confermi i dati inseriti? (S/n) ");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Auto auto = builder.build();
                   // controller.aggiungiAuto(auto);
                    System.out.println("Auto aggiunta con successo.");
                    return auto;
                } else {
                    System.out.println("Riprovare? (S/n) ");
                    confermaInput = scanner.next();
                    if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                        termina = true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti valori non validi. Riprovare? (S/n) ");
                confermaInput = scanner.next();
                if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                    termina = true;
                }
            }
        }
        return null;
    }

    private void displayModificaAuto() {
        //controller.mostraAuto();
        String idAuto;
        String confermaInput;

        System.out.println("Inserire l'ID dell'auto che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idAuto = scanner.next();
        if (true) { //TODO controller.isAuto(idAuto)
            boolean termina = false;
            String input;
            AutoBuilder builder = new AutoBuilder();
            while (!termina) {
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                try {
                    switch (input) {
                        case "1":
                            System.out.print("Inserire nuova targa: ");
                            input = scanner.next();
                            builder.targa(input);
                            continue;
                        case "2":
                            System.out.print("Inserire nuovo prezzo giornaliero: ");
                            input = scanner.next();
                            builder.prezzoGiornaliero(Float.parseFloat(input));
                            continue;
                        case "3":
                            System.out.print("Inserire nuova data ultima revisione: ");
                            input = scanner.next();
                            builder.ultimaRevisione(input);
                            continue;
                        case "x":
                            termina = true;
                            continue;
                        default:
                            System.out.println("Valore non valido");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Valore inserito non valido");
                }
            }

            System.out.println("Applicare le modifiche all'auto con ID: " + idAuto + "?");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Auto auto = builder.build();
               // controller.modificaAuto(idAuto, auto);
                System.out.println("Auto modificata");
            }
        } else {
            System.out.println("Non esiste nessun auto con l'ID selezionato.");
        }
    }

    private void displayRimuoviAuto() {
        //controller.mostraAuto();
        String idAuto;
        String confermaInput;

        System.out.println("Inserire l'ID dell'auto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idAuto = scanner.next();
        if (true) { //TODO controller.isAuto(idAuto)
            System.out.println("Rimuovere definitivamente l'auto con ID " + idAuto + "?");
            System.out.print("ATTENZIONE: verranno rimossi anche eventuali clienti e contratto ad esso associati (S/n) ");
            confermaInput = scanner.next(); //TODO controllare rimozione cliente e contratto
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                //controller.rimuoviAuto(idAuto);
                System.out.println("Rimossa auto con ID " + idAuto);
            }
        } else {
            System.out.println("Non esiste nessun auto con l'ID selezionato.");
        }
    }

}

