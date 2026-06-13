package gestaocompras;

import gestaocompras.service.ListaService;
import gestaocompras.ui.MenuUI;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ListaService service = new ListaService();
        MenuUI menu = new MenuUI(sc, service);
        menu.exibirMenu();
        sc.close();
    }
}
