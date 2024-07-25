package view.menu;

import controller.ClienteController;
import model.Cliente;


public class MenuUtente extends Menu {
    private static String listaOpzioniModifica = " 1 - Nome\n" +
            " 2 - Cognome\n" +
            " 3 - Email\n" +
            " 4 - Password\n" +
            " x - Applica modifiche";

    public MenuUtente(ClienteController clienteController) {
        super(clienteController);
    }

    public void display() {

        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Modifica dati utente");
            System.out.println(" 2 - Elimina utente");
            System.out.println(" x - Indietro");

            String input = scanner.next();

            switch (input) {
                case "1":
                    displayModifica();
                    continue;
                case "2":
                    if (displayRimuovi()) {
                        clienteController.reset();
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
                clienteController.getCliente().getCf() + " e tutti i dati ad esso associato?(S/n) ");
        String confermaInput = scanner.next();

        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            clienteController.rimuoviUtente();
            System.out.println("Utente rimosso dal sistema");
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

        System.out.println("Applicare le modifiche all'utente?");
        confermaInput = scanner.next();
        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            clienteController.modificaCliente(cliente);
            System.out.println("Utente modificato");
        } else {
            System.out.println("Operazione annullata");
        }
    }
}
