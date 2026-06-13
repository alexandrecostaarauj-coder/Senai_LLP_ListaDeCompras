package gestaocompras.ui;

import gestaocompras.model.Item;
import gestaocompras.model.Lista;
import gestaocompras.service.ListaService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class MenuUI {

    private final Scanner sc;
    private final ListaService service;

    public MenuUI(Scanner sc, ListaService service) {
        this.sc = sc;
        this.service = service;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n.-------------------.");
            System.out.println("| Gestão de compras |");
            System.out.println("'-------------------'");
            System.out.println("Selecione a opção:");
            System.out.println("1. Nova lista");
            System.out.println("2. Fazer compras");
            System.out.println("3. Relatório");
            System.out.println("0. Sair");
            System.out.print(">> Opção: ");
            opcao = lerInt();

            switch (opcao) {
                case 1: novaLista(); break;
                case 2: fazerCompras(); break;
                case 3: relatorio(); break;
                case 0: System.out.println("Encerrando..."); break;
                default: System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void novaLista() {
        String dataHoje = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String nomePadrao = "lista_" + dataHoje;

        System.out.print("\n>> Nova lista, informe o nome [" + nomePadrao + "]: ");
        String nome = sc.nextLine().trim();
        if (nome.isEmpty()) nome = nomePadrao;

        Lista lista = new Lista(nome);

        while (true) {
            System.out.println(">> ---Informe o item---------");
            System.out.print(">> Descrição: ");
            String descricao = sc.nextLine().trim();
            if (descricao.isEmpty()) break;

            String unidade;
            while (true) {
                System.out.print(">> Unidade (UN, KG, LT, CX): ");
                unidade = sc.nextLine().trim().toUpperCase();
                if (unidade.equals("UN") || unidade.equals("KG") ||
                    unidade.equals("LT") || unidade.equals("CX")) break;
                System.out.println("   Unidade inválida. Use: UN, KG, LT ou CX");
            }

            System.out.print(">> Quantidade: ");
            double quantidade = lerDouble();

            lista.adicionarItem(new Item(descricao, unidade, quantidade));
        }

        service.salvar(lista);
        System.out.println(">> ---Lista salva!---------");
    }

    private void fazerCompras() {
        Lista lista = selecionarLista();
        if (lista == null) return;

        List<Item> itens = lista.getItens();
        if (itens.isEmpty()) {
            System.out.println("Lista vazia.");
            return;
        }

        System.out.println("\n>> ---Fazer compras [" + lista.getNome() + "]---");
        int total = itens.size();

        for (int i = 0; i < total; i++) {
            Item item = itens.get(i);
            System.out.printf(">> (%d/%d) Produto: %s %.1f %s%n",
                    i + 1, total, item.getDescricao(), item.getQuantidade(), item.getUnidade());

            System.out.printf(">> Quantidade [%.1f %s]: ", item.getQuantidade(), item.getUnidade());
            String qtdInput = sc.nextLine().trim();
            double qtd = qtdInput.isEmpty() ? item.getQuantidade() : Double.parseDouble(qtdInput.replace(",", "."));

            System.out.print(">> Preço (0 = em falta): ");
            double preco = lerDouble();

            if (preco == 0) {
                System.out.println("   Item em falta, pulando...");
                item.setPrecoUnit(0);
                item.setQuantidadeComprada(0);
                continue;
            }

            item.setPrecoUnit(preco);
            item.setQuantidadeComprada(qtd);
            System.out.printf("   Total do item: R$ %.2f%n", item.getTotal());
        }

        service.salvar(lista);
        System.out.println(">> ---Total------------------");
        System.out.printf(">> R$: %.2f%n", lista.getTotal());
    }

    private void relatorio() {
        Lista lista = selecionarLista();
        if (lista == null) return;

        System.out.println("\n>> ---Relatório [" + lista.getNome() + "]---");
        System.out.println(">> Item, Descrição, Qtd, UN, Preço");

        List<Item> itens = lista.getItens();
        for (int i = 0; i < itens.size(); i++) {
            Item item = itens.get(i);
            System.out.printf(">> %d, %s, %.1f, %s, %.2f%n",
                    i + 1, item.getDescricao(), item.getQuantidadeComprada(),
                    item.getUnidade(), item.getPrecoUnit());
        }

        System.out.printf(">> 0, TOTAL, %.1f, UN, %.2f%n",
                lista.getQuantidadeTotalComprada(), lista.getTotal());
    }

    private Lista selecionarLista() {
        List<Lista> listas = service.getListas();
        if (listas.isEmpty()) {
            System.out.println("Nenhuma lista encontrada. Crie uma primeiro.");
            return null;
        }

        System.out.println("\nListas disponíveis:");
        for (int i = 0; i < listas.size(); i++) {
            System.out.println((i + 1) + ". " + listas.get(i).getNome());
        }
        System.out.print(">> Selecione: ");
        int idx = lerInt() - 1;

        if (idx < 0 || idx >= listas.size()) {
            System.out.println("Opção inválida.");
            return null;
        }
        return listas.get(idx);
    }

    private int lerInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double lerDouble() {
        try {
            return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
