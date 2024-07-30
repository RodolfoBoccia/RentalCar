package view.menu;

import controller.ClienteController;
import controller.LoginController;
import controller.ProprietarioController;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;
    protected static ProprietarioController proprietarioController;
    protected static ClienteController clienteController;
    protected static LoginController loginController;

    public Menu(LoginController loginController) {
        Menu.loginController = loginController;
        this.scanner = new Scanner(System.in);
    }

    public Menu(ProprietarioController proprietarioController) {
        Menu.proprietarioController = proprietarioController;
        this.scanner = new Scanner(System.in);
    }

    public Menu(ClienteController clienteController) {
        Menu.clienteController = clienteController;
        this.scanner = new Scanner(System.in);
    }

    public abstract void display();

    public int getIntInput() {
        int input = 0;
        boolean inputValido = false;
        while (!inputValido) {
            try {
                input = scanner.nextInt();
                inputValido = true;
            } catch (InputMismatchException e) {
                System.out.println("Inserire un numero intero");
                scanner.next();
            }
        }
        return input;
    }
}