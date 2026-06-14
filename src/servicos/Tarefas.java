package servicos;

import utils.Resposta;

import entities.*;

public interface Tarefas {
    public Resposta adicionarEstabelecimento();
    public void adicionarGerente(String idEstabelecimento, String idArmazem);
    public String adicionarArmazem(String idEstabelecimento);
    public Resposta adicionarCategoria();
    public Resposta adicionarProdutosEstabelecimento(String idEstabelecimento, String idArmazem, String produto);
    public void adicionarProdutosArmazem(String id);
    public Resposta registrarVenda(String idEstabelecimento, String idArmazem);
    public Gerente getDados();
    public void listarCategorias();
    public void listarVendas(String idEstabelecimento);
    public void listarProdutos(String id, boolean estabelecimento);
    public void limparTela();
    
}
