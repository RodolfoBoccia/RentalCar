package view.menu;

import controller.ProprietarioController;
import model.Proprietario;

public class MenuFacadeProprietario extends Menu {
    private static ProprietarioController proprietarioController;

    public MenuFacadeProprietario(ProprietarioController proprietarioController, Proprietario proprietario) {
        MenuFacadeProprietario.proprietarioController = proprietarioController;
        proprietarioController.setProprietario(proprietario);
    }

    public void display() {
        System.out.println("---Menu proprietario---");
        System.out.println("Bentornato nell'app di noleggio auto!");

        while (true) {
            System.out.println("Scegliere quale menu visualizzare: ");
            System.out.println(" 1 - Menu gestione clienti");
            System.out.println(" 2 - Menu gestione auto");
            System.out.println(" 3 - Menu gestione contratti");
            System.out.println(" x - Logout");

            String input = scanner.next();
            switch (input) {
                case "1":
                    MenuClienti menuClienti = new MenuClienti(proprietarioController);
                    menuClienti.display();
                    continue;
                case "2":
                    MenuAutoProprietario menuAutoProprietario = new MenuAutoProprietario(proprietarioController);
                    menuAutoProprietario.display();
                    continue;
                case "3":
                    MenuContrattiProprietario menuContrattiProprietario = new MenuContrattiProprietario(proprietarioController);
                    menuContrattiProprietario.display();
                    continue;
                case "x":
                    proprietarioController.reset();
                    return;
                default:
                    System.out.println("Valore inserito non valido");
            }
        }
    }
}