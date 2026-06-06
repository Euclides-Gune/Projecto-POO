package servicos;

import utils.Resposta;

import entities.*;

public interface Tarefas {
    public Resposta adicionarEstabelecimento();
    public void adicionarGerente(String idEstabelecimento, String idArmazem);
    public String adicionarArmazem(String idEstabelecimento);
    public void adicionarProdutos(String id, boolean estabelecimento);
    public Resposta adicionarCategoria();
    public Resposta registrarVenda(String idEstabelecimento);
    public Gerente getDados();
    public void listarCategorias();
    public void listarVendas(String idEstabelecimento);
    public void listarProdutos(String id, boolean estabelecimento);

}
