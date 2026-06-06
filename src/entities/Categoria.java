package entities;

import java.io.Serializable;
import java.util.UUID;

public class Categoria implements Serializable {
    private String idCategoria;
    private String nome;

    public Categoria(String nome) {
        this.idCategoria = UUID.randomUUID().toString();
        this.nome = nome;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return
                "ID da categoria: " + idCategoria + '\n' +
                "Nome: " + nome + '\n';
    }
}
