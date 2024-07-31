import controller.LoginController;
import view.menu.MenuLogin;


public class Main {
    public static void main(String[] args) {
        MenuLogin menu = new MenuLogin(new LoginController());
        menu.display();
    }
}