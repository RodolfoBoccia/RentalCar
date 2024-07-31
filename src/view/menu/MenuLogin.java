package view.menu;

import controller.ClienteController;
import controller.LoginController;
import controller.ProprietarioController;
import model.ClienteBuilder;

public class MenuLogin extends Menu {
    protected static LoginController loginController;

    public MenuLogin(LoginController loginController) {
        MenuLogin.loginController = loginController;
    }

    public void display() {
        System.out.println("---Benvenuto nell'app di noleggio auto!---");
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
        String confermaInput;           // inizializzo a 0, mi serve per metterci la scelta nell'else

        while (true) {
            System.out.println("Inserire email e password per accedere");
            System.out.print("Email: ");
            String email = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            if (loginController.isAccountProprietario(email, password)) {
                MenuFacadeProprietario menuFacadeProprietario = new MenuFacadeProprietario(new ProprietarioController(), loginController.getProprietario(email, password));
                menuFacadeProprietario.display();
                return;

            } else if (loginController.isAccountCliente(email, password)) {
                MenuFacadeCliente menuFacadeCliente = new MenuFacadeCliente(new ClienteController(), loginController.getCliente(email, password));
                menuFacadeCliente.display();
                return;

            } else {
                System.out.println("Email o password errate.");
                System.out.println("Riprovare? (S/N)");
                // se si sceglie qualcosa che inizia con 's' si riprova, altrimenti si esce
                confermaInput = scanner.next();
                if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                    return;
                }
            }
        }
    }

    public void displayRegistrazione() {
        boolean verificato = false;
        String confermaInput;
        String email = "";
        while (!verificato) {
            System.out.print("Email: ");
            email = scanner.next();
            scanner.nextLine();
            if (!loginController.emailDisponibile(email)) {
                System.out.println("Email già associata a un account. Riprovare? (S/N)");
                confermaInput = scanner.nextLine();
                if (!confermaInput.equals("s") && !confermaInput.equals("S")) {
                    return;
                }
            } else {
                verificato = true;
            }
        }
        System.out.print("Password: ");
        String psw = scanner.nextLine();

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Cognome: ");
        String cognome = scanner.nextLine();

        System.out.print("Codice fiscale: ");
        String cf = scanner.next();

        System.out.print("Confermi i dati inseriti? (S/N) ");
        confermaInput = scanner.next();

        if (confermaInput.equals("S") || confermaInput.equals("s")) {
            ClienteBuilder builder = new ClienteBuilder();
            builder.nome(nome).cognome(cognome).cf(cf).email(email).password(psw);
            loginController.aggiungiCliente(builder.build());
            System.out.println("---Registrazione avvenuta con successo---");
        }
    }
}