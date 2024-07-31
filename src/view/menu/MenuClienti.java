package view.menu;

import controller.ProprietarioController;
import model.Cliente;
import model.ClienteBuilder;

public class MenuClienti extends Menu {
    private static ProprietarioController proprietarioController;

    public MenuClienti(ProprietarioController proprietarioController) {
        MenuClienti.proprietarioController = proprietarioController;
    }

    public void display() {
        boolean termina = false;
        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Mostra tabella clienti");
            System.out.println(" 2 - Aggiungi cliente");
            System.out.println(" 3 - Modifica cliente");
            System.out.println(" 4 - Rimuovi cliente");
            System.out.println(" x - Indietro");

            String input = scanner.next();
            switch (input) {
                case "1":
                    proprietarioController.mostraClienti();
                    continue;
                case "2":
                    displayAggiungiCliente();
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

    public boolean displayAggiungiCliente() {
        while (true) {
            ClienteBuilder clienteBuilder = new ClienteBuilder();
            System.out.println("Inserire i dati del cliente");

            System.out.print("Nome: ");
            String nome = scanner.next();
            clienteBuilder.nome(nome);
            scanner.nextLine();

            System.out.print("Cognome: ");
            String cognome = scanner.nextLine();
            clienteBuilder.cognome(cognome);

            System.out.print("Codice fiscale: ");
            String cf = scanner.nextLine();
            clienteBuilder.cf(cf);

            System.out.print("Email: ");
            String email = scanner.nextLine();
            clienteBuilder.email(email);

            System.out.print("Password: ");
            String password = scanner.nextLine();
            clienteBuilder.password(password);

            System.out.println("Confermi i dati inseriti? (S/N)");
            String confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                Cliente cliente = clienteBuilder.build();
                proprietarioController.aggiungiCliente(cliente);
                return true;
            }
            System.out.println("Tornare al menu utente e cancellare l'operazione? (S/N)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                return false;
            }
        }
    }

    public void displayModificaCliente() {
        proprietarioController.mostraClienti();
        String confermaInput;
        System.out.println("Inserire l'ID dell cliente che si desidera modificare (come indicato in tabella)");
        System.out.print("ID: ");
        int idCliente = getIntInput();

        if (proprietarioController.isCliente(idCliente)) {
            boolean termina = false;
            String input;
            Cliente cliente = proprietarioController.getCliente(idCliente);

            while (!termina) {
                System.out.println("Scegliere l'attributo che si desidera modificare:");
                String listaOpzioniModifica = """
                         1 - Codice fiscale
                         2 - Nome
                         3 - Cognome
                         4 - Email
                         x - Applica modifiche\
                        """;
                System.out.println(listaOpzioniModifica);
                input = scanner.next();
                switch (input) {
                    case "1":
                        System.out.print("Inserire il nuovo codice fiscale: ");
                        input = scanner.next();
                        cliente.setCf(input);
                        continue;
                    case "2":
                        System.out.print("Inserire il nuovo nome: ");
                        input = scanner.next();
                        cliente.setNome(input);
                        continue;
                    case "3":
                        System.out.print("Inserire il nuovo cognome: ");
                        input = scanner.next();
                        cliente.setCognome(input);
                        continue;
                    case "4":
                        System.out.print("Inserire la nuova email: ");
                        input = scanner.next();
                        cliente.setEmail(input);
                        continue;
                    case "x":
                        termina = true;
                        continue;
                    default:
                        System.out.println("Valore non valido");
                }
            }
            System.out.println("Applicare le modifiche al cliente con ID: " + idCliente + "? (S/N)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.modificaCliente(idCliente, cliente);
                System.out.println("Cliente modificato");
            }
        } else {
            System.out.println("Non esiste nessun cliente con l'ID selezionato.");
        }
    }

    public void displayRimuoviCliente() {
        proprietarioController.mostraClienti();
        System.out.println("Inserire l'ID del cliente che si desidera rimuovere (come indicato in tabella)");
        System.out.print("ID: ");
        int idCliente = getIntInput();
        String confermaInput;

        if (proprietarioController.isCliente(idCliente)) {
            System.out.println("Rimuovere definitivamente il cliente con ID: " + idCliente + " e il relativo contratto? (S/N)");
            confermaInput = scanner.next();
            if (confermaInput.equals("s") || confermaInput.equals("S")) {
                proprietarioController.rimuoviCliente(idCliente);
                System.out.println("Rimosso cliente con ID: " + idCliente);
            }
        } else {
            System.out.println("Non esiste nessun cliente con l'ID selezionato.");
        }
    }
}