package gestaocompras.model;

import java.io.Serializable;

public class Item implements Serializable {

    private String descricao;
    private String unidade;
    private double quantidade;
    private double precoUnit;
    private double quantidadeComprada;

    public Item(String descricao, String unidade, double quantidade) {
        this.descricao = descricao;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.precoUnit = 0;
        this.quantidadeComprada = quantidade;
    }

    public String getDescricao() { return descricao; }
    public String getUnidade() { return unidade; }
    public double getQuantidade() { return quantidade; }
    public double getPrecoUnit() { return precoUnit; }
    public double getQuantidadeComprada() { return quantidadeComprada; }

    public void setPrecoUnit(double precoUnit) { this.precoUnit = precoUnit; }
    public void setQuantidadeComprada(double quantidadeComprada) { this.quantidadeComprada = quantidadeComprada; }

    public double getTotal() {
        return precoUnit * quantidadeComprada;
    }
}
