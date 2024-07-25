package view.menu;

import controller.ClienteController;
import controller.LoginController;
import controller.ProprietarioController;

import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;
    protected static ProprietarioController proprietarioController;
    protected static ClienteController clienteController;
    protected static LoginController loginController;

    Menu() {
    }

    public Menu(LoginController loginController) {
        this.loginController = loginController;
        this.scanner = new Scanner(System.in);
    }

    public Menu(ProprietarioController proprietarioController) {
        this.proprietarioController = proprietarioController;
        this.scanner = new Scanner(System.in);
    }

    public Menu(ClienteController clienteController) {
        this.clienteController = clienteController;
        this.scanner = new Scanner(System.in);
    }

    public abstract void display();
}
