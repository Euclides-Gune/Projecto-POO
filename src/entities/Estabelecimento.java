package entities;
import java.io.Serializable;
import java.util.UUID;

public class Estabelecimento implements Serializable {
    private String idEstabelecimento;
    private String nome;
    private String localizacao;
    private String contacto;

    public Estabelecimento(String nome, String localizacao, String contacto) {
        this.idEstabelecimento = UUID.randomUUID().toString();
        this.nome = nome;
        this.localizacao = localizacao;
        this.contacto = contacto;
    }

    public String getIdEstabelecimento() {
        return idEstabelecimento;
    }

    public String getNome() {
        return nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public String getContacto() {
        return contacto;
    }

    @Override
    public String toString() {
        return
                "ID: " + idEstabelecimento + '\n' +
                "Nome: " + nome + '\n' +
                "Localizacao: " + localizacao + '\n' +
                "Contacto: " + contacto + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        Estabelecimento outro = (Estabelecimento) (obj);
        return this.nome.toLowerCase().equals(outro.nome.toLowerCase()) && this.localizacao.equals(outro.localizacao) && this.contacto.equals(outro.contacto);
    }
}
