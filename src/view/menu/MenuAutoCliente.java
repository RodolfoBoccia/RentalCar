package view.menu;

import controller.ClienteController;

public class MenuAutoCliente extends Menu {
    protected static ClienteController clienteController;

    public MenuAutoCliente(ClienteController clienteController) {
        MenuAutoCliente.clienteController = clienteController;
    }

    public void display() {
        boolean termina = false;

        while (!termina) {
            System.out.println("Scegli l'operazione da eseguire: ");
            System.out.println(" 1 - Mostra tabella auto");
            System.out.println(" x - Indietro");
            String input = scanner.next();

            switch (input) {
                case "1":
                    clienteController.mostraAuto();
                    continue;
                case "x":
                    termina = true;
                    continue;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }
}