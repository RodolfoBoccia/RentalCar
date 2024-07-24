package view.menu;

public class MenuFacadeCliente extends Menu {
    public MenuFacadeCliente() {
        super(controller);
    }

    public void display() {
        System.out.println("Menu cliente");

        boolean termina = false;
        System.out.println("Bentornato nell'app di noleggio auto!");

        while (true) {
            System.out.println("Scegliere quale menu visualizzare: ");
            System.out.println(" 1 - Menu gestione utente"); //TODO operazioni cliente
            System.out.println(" 2 - Menu auto");
            System.out.println(" 3 - Menu contratti");
            System.out.println(" x - Logout");

            String input = scanner.next();

            switch (input) {
                case "1":
                    MenuUtente menuUtente = new MenuUtente(controller);
                    if (menuUtente.display(0)) {      // se ritorna true vuol dire che ho eliminato l'utente
                        if (!displayLogin()) {
                            return;
                        }
                    }
                    continue;
                case "2":
                    MenuAutoCliente menuClienteAuto = new MenuAutoCliente(controller);
                    menuClienteAuto.display();
                    continue;
                case "3":
                    MenuContrattiCliente menuClienteContratti = new MenuContrattiCliente(controller);
                    menuClienteContratti.display();
                    continue;
                case "x":
                    controller.reset();
                    if (!displayLogin()) {
                        return;
                    }
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }

    public boolean displayLogin() { //TODO che cazz fa sta roba?

        MenuLogin menuLogin = new MenuLogin(controller);
        menuLogin.display();
        if (!controller.isLoggatoCliente()) {
            return false;  // se non setto il proprietario nel menuLogin vuol dire che voglio uscire
        }
        return true;
    }
}