package entities;

import java.io.Serializable;
import java.util.UUID;

public class Armazem implements Serializable {
    private String idArmazem;
    private String localizacao;
    private String idEstabelecimento;

    public Armazem(String localizacao, String idEstabelecimento) {
        this.idArmazem = UUID.randomUUID().toString();
        this.localizacao = localizacao;
        this.idEstabelecimento = idEstabelecimento;
    }

    public String getIDArmazem() {
        return idArmazem;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return
                "ID do armazem: " + idArmazem + '\n' +
                "Localizacao: " + localizacao + '\n' +
                "ID do estabelecimento: " + idEstabelecimento + '\n';
    }
}
