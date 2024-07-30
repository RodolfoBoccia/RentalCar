package view.menu;

import controller.ClienteController;
import model.Cliente;


public class MenuUtente extends Menu {

    public MenuUtente(ClienteController clienteController) {
        super(clienteController);
    }

    public void display() {
        boolean termina = false;
        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Visualizza dati utente");
            System.out.println(" 2 - Modifica dati utente");
            System.out.println(" 3 - Elimina utente");
            System.out.println(" x - Indietro");

            String input = scanner.next();
            switch (input) {
                case "1":
                    clienteController.mostraCliente();
                    continue;
                case "2":
                    displayModifica();
                    continue;
                case "3":
                    if (displayRimuovi()) {
                        clienteController.reset();
                        return;
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

    private boolean displayRimuovi() {
        System.out.println("Desideri rimuovere definitivamente l'utente con codice fiscale: " +
                clienteController.getCliente().getCf() + " e tutti i dati ad esso associato? (S/N) ");
        String confermaInput = scanner.next();
        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            clienteController.rimuoviUtente();
            System.out.println("---Utente rimosso dal sistema---");
            return true;
        }
        return false;
    }

    private void displayModifica() {
        String confermaInput;
        String input;
        boolean termina = false;
        Cliente cliente = clienteController.getCliente();

        while (!termina) {

            System.out.println("Scegliere l'attributo che si desidera modificare:");
            String listaOpzioniModifica = """
                     1 - Nome
                     2 - Cognome
                     3 - Email
                     4 - Password
                     x - Applica modifiche\
                    """;
            System.out.println(listaOpzioniModifica);
            input = scanner.next();
            switch (input) {
                case "1":
                    System.out.print("Inserire il nuovo nome: ");
                    input = scanner.next();
                    cliente.setNome(input);
                    continue;
                case "2":
                    System.out.print("Inserire il nuovo cognome: ");
                    input = scanner.next();
                    cliente.setCognome(input);
                    continue;
                case "3":
                    System.out.print("Inserire il nuovo indirizzo email: ");
                    input = scanner.next();
                    cliente.setEmail(input);
                    continue;
                case "4":
                    System.out.print("Inserire la nuova password: ");
                    input = scanner.next();
                    cliente.setPassword(input);
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore non valido");
            }
        }
        System.out.println("Applicare le modifiche all'utente? (S/N)");
        confermaInput = scanner.next();
        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            clienteController.modificaCliente(cliente);
            System.out.println("---Utente modificato---");
        } else {
            System.out.println("---Operazione annullata---");
        }
    }
}
