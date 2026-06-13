package gestaocompras.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lista implements Serializable {

    private String nome;
    private List<Item> itens;

    public Lista(String nome) {
        this.nome = nome;
        this.itens = new ArrayList<>();
    }

    public String getNome() { return nome; }
    public List<Item> getItens() { return itens; }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double getTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.getTotal();
        }
        return total;
    }

    public double getQuantidadeTotalComprada() {
        double total = 0;
        for (Item item : itens) {
            total += item.getQuantidadeComprada();
        }
        return total;
    }
}
