package view.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Menu {
    protected Scanner scanner;

    public Menu() {
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