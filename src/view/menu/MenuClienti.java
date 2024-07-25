package view.menu;

import controller.ProprietarioController;
import model.*;

public class MenuClienti extends Menu {
    private static String listaOpzioniModifica = " 1 - Codice fiscale\n" +
            " 2 - Nome\n" +
            " 3 - Cognome\n" +
            " 4 - Email\n" +
            " x - Applica modifiche";

    public MenuClienti(ProprietarioController proprietarioController) {
        super(proprietarioController);
    }

    public void display() {

        boolean termina = false;

        while(!termina){
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Aggiungi cliente");
            System.out.println(" 2 - Mostra tabella clienti");
            System.out.println(" 3 - Modifica cliente");
            System.out.println(" 4 - Rimuovi cliente");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAggiungiCliente();
                    continue;
                case "2":
                    proprietarioController.mostraClienti(); //TODO da controllare
                    continue;
                case "3":
                    displayModificaCliente();
                    continue;
                case "4":
                    displayRimuoviCliente();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public Cliente displayAggiungiCliente() {
        boolean conferma = false;
        while (!conferma) {
            System.out.println("Dopo aver aggiunto i dati del cliente verrà richiesto " +
                    "di inserire i dati del relativo contratto");
            System.out.println("Inserire i dati del cliente");
            System.out.print("Nome: ");
            String nome = scanner.next();
            scanner.nextLine();
            System.out.print("Cognome: ");
            String cognome = scanner.nextLine();
            System.out.print("Codice fiscale: ");
            String cf = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.println("Confermi i dati inseriti? (S/n)");
            String confermaInput = scanner.next();

            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Cliente cliente = new Cliente(cf, nome, cognome, email, password);
                MenuContrattiProprietario menuContrattiProprietario = new MenuContrattiProprietario(proprietarioController);
                Contratto contratto = menuContrattiProprietario.displayAggiungiContratto(cliente);
                if(contratto != null) {
                    proprietarioController.aggiungiCliente(cliente); //TODO controllare
                    proprietarioController.aggiungiContratto(contratto); //TODO da controllare
                    System.out.println("Cliente e relativo contratto aggiunti con successo.");
                    return cliente;
                }
                return null;
            }

            System.out.println("Tornare al menu utente e cancellare l'operazione? (s/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                conferma = true;
            }
        }
        return null;
    }

    public void displayModificaCliente() {
        proprietarioController.mostraClienti(); //TODO da controllare
        String idCliente;
        String confermaInput;

        System.out.println("Inserire l'ID dell cliente che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        idCliente = scanner.next();

        if(proprietarioController.isCliente(idCliente)) {
            boolean termina=false;
            String input;
            ClienteBuilder builder = new ClienteBuilder();

            while(!termina){
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                switch (input){
                    case "1":
                        System.out.print("Inserire il nuovo codice fiscale: ");
                        input = scanner.next();
                        builder.cf(input);
                        continue;
                    case "2":
                        System.out.print("Inserire il nuovo nome: ");
                        input = scanner.next();
                        builder.nome(input);
                        continue;
                    case "3":
                        System.out.print("Inserire il nuovo cognome: ");
                        input = scanner.next();
                        builder.cognome(input);
                        continue;
                    case "8":
                        System.out.print("Inserire la nuova email: ");
                        input = scanner.next();
                        builder.email(input);
                        continue;
                    case "x":
                        termina = true;
                        continue;
                    default:
                        System.out.println("Valore non valido");
                }
            }
                System.out.println("Applicare le modifiche all'inquilino con ID: " + idCliente + "?");
                confermaInput=scanner.next();
                if (confermaInput.equals("s") || confermaInput.equals("S")) {
                    Cliente cliente = builder.build();
                    proprietarioController.modificaCliente(idCliente, cliente); //TODO da controllare
                    System.out.println("Cliente modificato");
                }

        }
        else {
            System.out.println("Non esiste nessun cliente con l'ID selezionato.");
        }
    }

    public void displayRimuoviCliente() {
        proprietarioController.mostraClienti(); //TODO da controllare
        String idCliente;
        String confermaInput;

        System.out.println("Inserire l'ID del cliente che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        idCliente = scanner.next();

        if(proprietarioController.isCliente(idCliente)) {
            System.out.println("Rimuovere definitivamente il cliente con ID: " + idCliente +
                    " e il relativo contratto? (S/n)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.rimuoviCliente(idCliente); //TODO da controllare
                System.out.println("Rimosso cliente con ID: " + idCliente);
            }
        }
        else {
            System.out.println("Non esiste nessun cliente con l'ID selezionato.");
        }
    }

}
