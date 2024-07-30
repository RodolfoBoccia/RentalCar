package view.menu;

import controller.ClienteController;

public class MenuFacadeCliente extends Menu {
    public MenuFacadeCliente() {
        super(new ClienteController());
    }

    public void display() {
        System.out.println("---Menu cliente---");
        System.out.println("Bentornato nell'app di noleggio auto!");
        while (true) {
            System.out.println("Scegliere quale menu visualizzare: ");
            System.out.println(" 1 - Menu gestione utente");
            System.out.println(" 2 - Menu auto");
            System.out.println(" 3 - Menu contratti");
            System.out.println(" x - Logout");

            String input = scanner.next();
            switch (input) {
                case "1":
                    MenuUtente menuUtente = new MenuUtente(clienteController);
                    menuUtente.display();
                    if (clienteController.getCliente() == null) { // utente eliminato
                        return;
                    }
                    continue;
                case "2":
                    MenuAutoCliente menuClienteAuto = new MenuAutoCliente(clienteController);
                    menuClienteAuto.display();
                    continue;
                case "3":
                    MenuContrattiCliente menuClienteContratti = new MenuContrattiCliente(clienteController);
                    menuClienteContratti.display();
                    continue;
                case "x":
                    clienteController.reset();
                    return;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }
}