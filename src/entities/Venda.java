package entities;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.time.LocalDateTime;

public class Venda implements Serializable {
    private String idVenda;
    private String nomeProduto;
    private int quantidade;
    private String data;
    private double total;
    private String idEstabelecimento;

    public Venda(String nomeProduto, int quantidade, double total, String idEstabelecimento) {
        idVenda = UUID.randomUUID().toString();
        this.nomeProduto = nomeProduto;
        this.quantidade =  quantidade;
        this.data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        this.total = total;
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public String getIdVenda() {
        return idVenda;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    @Override
    public String toString() {
        return
                "ID da venda: " + idVenda + '\n' +
                "Nome do produto: " + nomeProduto + '\n' +
                "Quantidade: " + quantidade + '\n' +
                "Data: " + data + '\n' +
                "Total: " + total + '\n' +
                "ID do estabelecimento: " + idEstabelecimento + '\n' +
                '}';
    }
}
