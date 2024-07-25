package view.menu;

import controller.LoginController;
import model.ClienteBuilder;


public class MenuLogin extends Menu {

    public MenuLogin() {
        super(new LoginController());
    }

    public void display() {

        System.out.println("Benvenuto nell'app di noleggio auto!");
        boolean termina = false;

        while (!termina) {
            System.out.println("Digita uno dei seguenti numeri per scegliere l'operazione:");
            System.out.println(" 1 - Accedi ");
            System.out.println(" 2 - Registrati (nuovo cliente)");
            System.out.println(" x - Esci");
            String input = scanner.next();

            switch (input) {
                case "1":
                    displayAccesso();
                    continue;
                case "2":
                    displayRegistrazione();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public void displayAccesso() {

        boolean termina = false;
        char[] riprova = {'0'};            // inizializzo a 0, mi serve per metterci la scelta nell'else

        while (!termina) {
            System.out.println("Inserire email e password per accedere");

            System.out.print("Email: ");
            String email = scanner.next();

            System.out.print("Password: ");
            String password = scanner.next();

            if (loginController.isAccountProprietario(email, password)) { //TODO controllare
                proprietarioController.setProprietario(email, password);
                MenuFacadeProprietario menuFacadeProprietario = new MenuFacadeProprietario();
                menuFacadeProprietario.display();

            } else if (loginController.isAccountCliente(email, password)) { //TODO controllare
                MenuFacadeCliente menuFacadeCliente = new MenuFacadeCliente();
                menuFacadeCliente.display();

            } else {
                System.out.println("Email o password errate.");
                System.out.println("Riprovare? (s/n)");
                // se si sceglie qualcosa che inizia con 's' si riprova, altrimenti si esce
                // quindi scannerizzo l'input, lo rendo minuscolo e estraggo il carattere in prima posizione mettendolo in riprova
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if (riprova[0] != 's') {
                    termina = true;
                }
            }
        }
    }

    public void displayRegistrazione() {

        boolean verificato = false;
        char[] riprova = {'0'};

        String email = "";
        while (!verificato) {
            System.out.print("Email: ");
            email = scanner.next();
            if (!loginController.emailDisponibile(email)) { //TODO controllare
                System.out.println("Email già associata a un account. Riprovare? (s/n)");
                scanner.next().toLowerCase().getChars(0, 1, riprova, 0);
                if (riprova[0] != 's') {
                    return;
                }

            } else {
                verificato = true;
            }
        }

        scanner.nextLine();
        System.out.print("Password: ");
        String psw = scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();
        System.out.print("Codice fiscale: ");
        String cf = scanner.next();

        System.out.print("Confermi i dati inseriti? (s/n) ");
        String confermaInput = scanner.next();

        if (confermaInput.equals("S") || confermaInput.equals("s")) {
            ClienteBuilder c = new ClienteBuilder(); //TODO controllare
            c.nome(nome);
            c.cognome(cognome);
            c.cf(cf);
            c.email(email);
            c.password(psw);
            loginController.aggiungiCliente(c.build()); //TODO

            System.out.println("Registrazione avvenuta con successo");
        }
    }

}
