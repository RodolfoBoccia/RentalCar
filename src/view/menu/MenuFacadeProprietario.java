package view.menu;

import controller.Controller;


public class MenuFacadeProprietario extends Menu {

    public MenuFacadeProprietario() {
        super(new Controller());
    }

    public void display() {
        System.out.println("Menu proprietario");

        boolean termina = false;
        System.out.println("Bentornato nell'app di noleggio auto!");

        while(true){
            System.out.println("Scegliere quale menu visualizzare: ");//TODO operazioni cliente
            System.out.println(" 1 - Menu gestione clienti");
            System.out.println(" 2 - Menu gestione auto");
            System.out.println(" 3 - Menu gestione contratti");
            System.out.println(" x - Logout");

            String input = scanner.next();

            switch (input) {
                case "1":
                    MenuClienti menuClienti = new MenuClienti(controller);
                    menuClienti.display();
                    continue;
                case "2":
                    MenuAutoProprietario menuAutoProprietario = new MenuAutoProprietario(controller);
                    menuAutoProprietario.display();
                    continue;
                case "3":
                    MenuContrattiProprietario menuContrattiProprietario = new MenuContrattiProprietario(controller);
                    menuContrattiProprietario.display();
                    continue;
                case "x":
                    controller.reset();
                    if(!displayLogin()) {
                        return;
                    }
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }
    public boolean displayLogin() { //TODO che cazz fa sta roba? controlla se ti sei loggato

        MenuLogin menuLogin = new MenuLogin(controller);
        menuLogin.display();
        if(controller.getProprietario() == null || !controller.isLoggatoCliente()) {
            return false;  // se non setto il proprietario nel menuLogin vuol dire che voglio uscire
        }
        return true;
    }
}
