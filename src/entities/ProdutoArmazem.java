package entities;

import java.io.Serializable;

public class ProdutoArmazem extends Produto implements Serializable {
    private String idArmazem;

    public ProdutoArmazem(String nome, int quantidade, double preco, String prazo, String idCategoria, String idArmazem) {
        super(nome, quantidade, preco, prazo, idCategoria);
        this.idArmazem = idArmazem;
    }

    public String getIdArmazem() {
        return idArmazem;
    }

    @Override
    public String toString() {
        return
                "ID do Produto: " + idProduto + '\n' +
                        "ID do Armazem: " + idArmazem + '\n' +
                        "ID da Categoria: " + idCategoria + '\n' +
                        "Nome: " + nome + '\n' +
                        "Prazo: " + prazo + '\n' +
                        "Preco: " + preco + '\n' +
                        "Quantidade: " + quantidade + '\n';
    }
}
