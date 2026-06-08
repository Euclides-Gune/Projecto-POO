package entities;

import java.io.Serializable;
import java.util.UUID;

public abstract class Produto implements Serializable {
    protected String idProduto;
    protected String nome;
    protected int quantidade;
    protected double preco;
    protected String prazo;
    protected String idCategoria;

    public Produto(String nome, int quantidade, double preco, String prazo, String idCategoria) {
        this.idProduto = UUID.randomUUID().toString();
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.prazo = prazo;
        this.idCategoria = idCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public String getNome() {
        return nome;
    }

    public String getPrazo() {
        return prazo;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quant) {
        this.quantidade = quant;
    }

    @Override
    public String toString() {
        return
                "ID do produto: " + idProduto + '\n' +
                "ID da categoria: '" + idCategoria + '\n' +
                "Nome: " + nome + '\n' +
                "Quantidade: " + quantidade + '\n' +
                "Preco: " + preco + '\n' +
                "Prazo: " + prazo + '\n' +
                '}';
    }
}
