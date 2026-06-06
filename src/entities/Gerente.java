package entities;

import java.io.Serializable;
import java.util.UUID;

public class Gerente implements Serializable {
    private String idGerente;
    private String nome;
    private String password;
    private String idEstabelecimento;
    private String idArmazem;

    public Gerente(String nome, String password, String idEstabelecimento, String idArmazem) {
        this.idGerente = UUID.randomUUID().toString();
        this.nome = nome;
        this.password = password;
        this.idEstabelecimento = idEstabelecimento;
        this.idArmazem = idArmazem;
    }

    public Gerente() {};

    public String getIdGerente() {
        return idGerente;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public String getIdArmazem() {
        return idArmazem;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "ID do gerente: " + idGerente + '\n' +
                "Nome: " + nome + '\n' +
                "Pasword: " + password + '\n' +
                "ID do estabelecimento: " + idEstabelecimento + '\n' +
                        "ID do armazém: " + idEstabelecimento + '\n';

    }

    @Override
    public boolean equals(Object obj) {
        Gerente outro = (Gerente) (obj);
        return this.nome.equals(outro.nome) && this.password.equals(outro.password);
    }
}
