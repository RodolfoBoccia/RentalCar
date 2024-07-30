package view.menu;

import controller.ProprietarioController;
import model.Auto;
import model.AutoBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MenuAutoProprietario extends Menu {

    public MenuAutoProprietario(ProprietarioController proprietarioController) {
        super(proprietarioController);
    }

    public void display() {
        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Mostra tabella auto");
            System.out.println(" 2 - Aggiungi auto");
            System.out.println(" 3 - Modifica auto");
            System.out.println(" 4 - Rimuovi auto");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    proprietarioController.mostraAuto();
                    continue;
                case "2":
                    displayAggiungiAuto();
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

    public Auto displayAggiungiAuto() {
        boolean termina = false;
        AutoBuilder builder = new AutoBuilder();

        while (!termina) {
            String confermaInput;
            try {
                System.out.println("Inserire i dati dell'auto");
                System.out.print("Targa: ");
                String targa = scanner.next();
                scanner.nextLine();
                System.out.print("Marca: ");
                String marca = scanner.next();
                System.out.print("Modello: ");
                String modello = scanner.next();
                System.out.print("Cambio: ");
                String cambio = scanner.next();
                System.out.print("Alimentazione: ");
                String alimentazione = scanner.next();
                System.out.print("Numero posti: ");
                String numeroPosti = scanner.next();
                System.out.print("Categoria: ");
                String categoria = scanner.next();
                System.out.print("Prezzo giornaliero: ");
                String prezzoGiornaliero = scanner.next();

                boolean dateValide = false;
                LocalDate dataRevisione = null;
                while (!dateValide) {
                    try {
                        System.out.print("Data ultima revisione (formato: YYYY-MM-DD): ");
                        String ultimaRevisione = scanner.next();

                        dataRevisione = LocalDate.parse(ultimaRevisione, DateTimeFormatter.ISO_LOCAL_DATE);

                        if (dataRevisione.isAfter(LocalDate.now())) {
                            System.out.println("La data della revisione non può essere successiva ad oggi. Riprova.");
                        } else {
                            dateValide = true; // Se le date vengono parse correttamente, esci dal ciclo

                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato data errato. Assicurati di inserire la data nel formato YYYY-MM-DD. Riprova.");
                    }
                }

                builder.numeroPosti(Integer.parseInt(numeroPosti)).categoria(categoria)
                        .prezzoGiornaliero(Float.parseFloat(prezzoGiornaliero)).targa(targa).marca(marca).modello(modello)
                        .cambio(cambio).alimentazione(alimentazione).ultimaRevisione(String.valueOf(dataRevisione)).noleggiata(false).idProprietario(1);

                System.out.println("Confermi i dati inseriti? (S/N) ");
                confermaInput = scanner.next();

                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Auto auto = builder.build();
                    proprietarioController.aggiungiAuto(auto);
                    System.out.println("Auto aggiunta con successo.");
                    proprietarioController.aggiorna();
                    return auto;
                } else {
                    System.out.println("Riprovare? (S/N) ");
                    confermaInput = scanner.next();
                    if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                        termina = true;
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Sono stati inseriti valori non validi. Riprovare? (S/N) ");
                confermaInput = scanner.next();
                if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                    termina = true;
                }
            }
        }
        return null;
    }

    private void displayModificaAuto() {
        proprietarioController.mostraAuto();
        int idAuto;
        String confermaInput;
        System.out.println("Inserire l'ID dell'auto che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idAuto = getIntInput();

        if (proprietarioController.isAuto(idAuto)) {
            boolean termina = false;
            String input;
            Auto auto = proprietarioController.getAuto(idAuto);
            while (!termina) {
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                String listaOpzioniModifica = """
                         1 - Targa
                         2 - Prezzo giornaliero
                         3 - Ultima revisione
                         x - Applica modifiche\
                        """;
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                try {
                    switch (input) {
                        case "1":
                            System.out.print("Inserire nuova targa: ");
                            input = scanner.next();
                            auto.setTarga(input);
                            continue;
                        case "2":
                            System.out.print("Inserire nuovo prezzo giornaliero: ");
                            input = scanner.next();
                            auto.setPrezzoGiornaliero(Float.parseFloat(input));
                            continue;
                        case "3":
                            boolean dateValide = false;
                            LocalDate dataRevisione;
                            while (!dateValide) {
                                try {
                                    System.out.print("Inserire nuova data ultima revisione (formato: YYYY-MM-DD): ");
                                    String ultimaRevisione = scanner.next();

                                    dataRevisione = LocalDate.parse(ultimaRevisione, DateTimeFormatter.ISO_LOCAL_DATE);

                                    if (dataRevisione.isAfter(LocalDate.now())) {
                                        System.out.println("La data della revisione non può essere successiva ad oggi. Riprova.");
                                    } else {
                                        dateValide = true; // Se le date vengono parse correttamente, esci dal ciclo

                                    }
                                } catch (DateTimeParseException e) {
                                    System.out.println("Formato data errato. Assicurati di inserire la data nel formato YYYY-MM-DD. Riprova.");
                                }
                            }
                            auto.setUltimaRevisione(input);
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
            System.out.println("Applicare le modifiche all'auto con ID: " + idAuto + "? (S/N) ");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.modificaAuto(idAuto, auto);
                System.out.println("Auto modificata");
            }
        } else {
            System.out.println("Non esiste nessun auto con l'ID selezionato.");
        }
    }

    private void displayRimuoviAuto() {
        proprietarioController.mostraAuto();
        String confermaInput;
        System.out.println("Inserire l'ID dell'auto che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        int idAuto = getIntInput();

        if (proprietarioController.isAuto(idAuto)) {
            System.out.println("Rimuovere definitivamente l'auto con ID " + idAuto + "?");
            System.out.print("ATTENZIONE: verranno rimossi anche eventuali clienti ad essa associati (S/N) ");
            confermaInput = scanner.next(); //TODO controllare rimozione cliente e contratto
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.rimuoviAuto(idAuto);
                System.out.println("---Rimossa auto con ID " + idAuto + "---");
            }
        } else {
            System.out.println("Non esiste nessun auto con l'ID selezionato.");
        }
    }
}