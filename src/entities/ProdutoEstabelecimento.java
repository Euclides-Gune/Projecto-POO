package entities;

import java.io.Serializable;

public class ProdutoEstabelecimento extends Produto implements Serializable {
    private String idEstabelecimento;

    public ProdutoEstabelecimento(String nome, int quantidade, double preco, String prazo, String idCategoria, String idEstabelecimento) {
        super(nome, quantidade, preco, prazo, idCategoria);
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

//    public void setQuantidade(int quantidade) {
//        this.quantidade = quantidade;
//    }

    @Override
    public String toString() {
        return
                "ID do Produto: " + idProduto + '\n' +
                        "ID do Estabelecimento: " + idEstabelecimento + '\n' +
                "ID da Categoria: " + idCategoria + '\n' +
                "Nome: " + nome + '\n' +
                "Prazo: " + prazo + '\n' +
                "Preco: " + preco + '\n' +
                "Quantidade: " + quantidade + '\n';
    }
}
