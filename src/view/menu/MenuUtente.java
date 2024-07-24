package view.menu;

import controller.Controller;
import model.Cliente;
import model.Proprietario;

import java.util.ArrayList;
import java.util.List;


public class MenuUtente extends Menu {
    private static String listaOpzioniModifica = " 1 - Nome\n" +
            " 2 - Cognome\n" +
            " 3 - Email\n" +
            " 4 - Password\n" +
            " x - Applica modifiche";

    public MenuUtente(Controller controller) {
        super(controller);
    }

    public void display() {
        return;             // solo per avere una signature coerente con la superclasse
    }

    public boolean display(int n) {

        boolean termina = false;

        while(!termina){
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
                    if(displayRimuovi()) {
                        controller.reset();
                        return true;    // se ritorno true vuol dire che ho eliminato l'utente
                    }
                     continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
        return false;
    }

    private boolean displayRimuovi() {
        System.out.println("Desideri rimuovere definitivamente l'utente con codice fiscale " +
                controller.getProprietario().getCf() + " e tutti i dati ad esso associato?(S/n) ");
        String confermaInput = scanner.next();

        if (confermaInput.equals("s") || confermaInput.equals("S")) {
            //controller.rimuoviProprietario();
            System.out.println("Utente rimosso dal sistema");
            return true;
        }
        return false;
    }

    private void displayModifica() {
        String confermaInput;
        String input;
        boolean termina = false;
        Proprietario proprietario = controller.getProprietario();

        while(!termina) {

            System.out.println("Scegliere l'attributo che si desidera modificare:");
            System.out.println(listaOpzioniModifica);
            input = scanner.next();
            switch (input) {
                case "1":
                    System.out.print("Inserire il nuovo nome: ");
                    input = scanner.next();
                    proprietario.setNome(input);
                    continue;
                case "2":
                    System.out.print("Inserire il nuovo cognome: ");
                    input = scanner.next();
                    proprietario.setCognome(input);
                    continue;
                case "3":
                    System.out.print("Inserire il nuovo indirizzo email: ");
                    input = scanner.next();
                    proprietario.setEmail(input);
                    continue;
                case "4":
                    System.out.print("Inserire la nuova password: ");
                    input = scanner.next();
                    proprietario.setPassword(input);
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore non valido");
            }
        }

        System.out.println("Applicare le modifiche all'utente?");
        confermaInput=scanner.next();
        if (confermaInput.equals("s") || confermaInput.equals("S")) {
           // controller.modificaProprietario(proprietario);
            System.out.println("Utente modificato");
        }
        else {
            System.out.println("Operazione annullata");
        }
    }
}
