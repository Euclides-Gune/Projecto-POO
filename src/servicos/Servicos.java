package servicos;

import entities.*;
import utils.*;

import java.util.ArrayList;
import java.util.List;

public class Servicos implements Tarefas {

    public Resposta adicionarEstabelecimento() {

        Resposta resposta = new Resposta("sucesso", "estabelecimento adicionado com sucesso");

        String nome = Validacao.validarString("Nome do estabelecimento: ");
        String localizacao = Validacao.validarString("Localização do estabelecimento: ");
        String contacto = Validacao.validarString("Contacto do estabelecimento: ");

        Estabelecimento estabelecimento = new Estabelecimento(nome, localizacao, contacto);

        List<Estabelecimento> estabelecimentos = new ArrayList<>();

        try {
            estabelecimentos = (ArrayList<Estabelecimento>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\estabelecimentos.dat"));
        } catch(ClassCastException e) {
            IO.println("\nPrimeiro estabelecimento a ser adicionado :)\n");
        }

        for(Estabelecimento est : estabelecimentos) {
            if(est.equals(estabelecimento)) {
                resposta.status = "erro";
                resposta.msg = "o estabelecimento já está cadastrado";
                return resposta;
            }
        }

        String idArmazem = adicionarArmazem(estabelecimento.getIdEstabelecimento());
        adicionarGerente(estabelecimento.getIdEstabelecimento(), idArmazem);

        estabelecimentos.add(estabelecimento);

        GerenciarArquivos.escreverObjectos(estabelecimentos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\estabelecimentos.dat");

        return resposta;
    }

    public void adicionarGerente(String idEstabelecimento, String idArmazem) {

        /*
        *  O status informa se o novo gerente está cadastrado ou não
        *  -1 significa que nenhum gerente foi cadastrado
        *  -2 significa que a senha introduzida pelo usuário é inválida
        * */

        List<Gerente> gerentes = new ArrayList<>();

        try {
            gerentes = (ArrayList<Gerente>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\gerentes.dat"));
        } catch(ClassCastException e) {
            ;
        }

        String nome = null;
        String password= null;
        Gerente gerente = null;

        int status = -1;

        do {

            if(status == -1) {
                nome = Validacao.validarString("Nome do gerente: ");
                password = Validacao.validarSenha("Senha: ", 6);
            } else if(status == -2) {
                password = Validacao.validarSenha("Senha: ", 6);
            }

            status = 0;

            gerente = new Gerente(nome, password, idEstabelecimento, idArmazem);

            for(Gerente gr : gerentes) {
                if(gr.equals(gerente)) {
                    IO.println("\n\t\t\tO gerente já está cadastrado!\n\t\t\tDigite os dados novamente: \n");
                    status = -1;
                } else if(gr.getPassword().equals(gerente.getPassword())) {
                    IO.println("\n\t\t\tO password já existe!\n\t\t\tDigite novamente: \n");
                    status = -2;
                }
            }

        } while(status == -1 || status == -2);

        gerentes.add(gerente);

        GerenciarArquivos.escreverObjectos(gerentes, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\gerentes.dat");
    }

    public String adicionarArmazem(String idEstabelecimento) {

        String localizacao = Validacao.validarString("localização do armazém: ");

        List<Armazem> armazens = new ArrayList<>();;

        try {
            armazens = (ArrayList<Armazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\armazens.dat"));
        } catch(ClassCastException e) {
            ;
        }

        Armazem armazem = new Armazem(localizacao, idEstabelecimento);

        armazens.add(armazem);

        GerenciarArquivos.escreverObjectos(armazens, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\armazens.dat");

        return armazem.getIDArmazem();
    }

    public void adicionarProdutos(String id, boolean estabelecimento) {

        IO.println("\n=========== Adicione produtos ao estabelecimento ===========\n");

        listarCategorias();

        try {

            List<Categoria> categorias = (List<Categoria>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\categorias.dat"));

            String more = "";

            do {

                String nome = Validacao.validarString("Produto: ");
                int quantidade = Validacao.validarInt("Quantidade: ", 1, 100000);
                double preco = Validacao.validarDouble("Preço: ", 0, 100000);
                String prazo = Validacao.validarString("Prazo: ");
                String idCategoria = null;
                do {
                    String categoria = Validacao.validarString("Categoria: ");
                    for (Categoria cat : categorias) {
                        if (cat.getNome().equals(categoria)) {
                            idCategoria = cat.getIdCategoria();
                        }
                    }
                    if (idCategoria == null) {
                        IO.println("\nCategoria inválida, adicione novamente!");
                    }
                } while (idCategoria == null);

                if (estabelecimento) {
                    String idEstabelecimento = id;

                    List<ProdutoEstabelecimento> produtos = new ArrayList<>();

                    try {
                        produtos = (ArrayList<ProdutoEstabelecimento>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
                    } catch (ClassCastException e) {
                        IO.println("\nPrimeiros produtos a serem adicionados :)\n");
                    }

                    produtos.add(new ProdutoEstabelecimento(nome, quantidade, preco, prazo, idCategoria, idEstabelecimento));
                    GerenciarArquivos.escreverObjectos(produtos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat");
                } else {
                    String idArmazem = id;

                    List<ProdutoArmazem> produtos = new ArrayList<>();

                    try {
                        produtos = (ArrayList<ProdutoArmazem>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
                    } catch (ClassCastException e) {
                        IO.println("\nPrimeiros produtos a serem adicionados :)\n");
                    }

                    produtos.add(new ProdutoArmazem(nome, quantidade, preco, prazo, idCategoria, idArmazem));
                    GerenciarArquivos.escreverObjectos(produtos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat");
                }

                more = Validacao.validarString("\nAdicionar mais [Y/N]: ");

            } while (more.toLowerCase().equals("y"));

            IO.println("\nProdutos adicionados com sucesso\n");

        } catch(ClassCastException e) {
            IO.println("Nenhuma categoria adicionada");
        }

    }

    public Resposta adicionarCategoria() {

        Resposta resposta = new Resposta("sucesso", "categoria adicionada");

        List<Categoria> categorias = new ArrayList<>();

        try {
            categorias = (ArrayList<Categoria>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\categorias.dat"));
        } catch (ClassCastException e) {
            ;
        }

        String nome = Validacao.validarString("Categoria: ");

        for(Categoria cat : categorias) {
            if(cat.getNome().equals(nome)) {
                resposta.status = "erro";
                resposta.msg = "categoria já cadastrada";
                return resposta;
            }
        }

        categorias.add(new Categoria(nome));

        GerenciarArquivos.escreverObjectos(categorias, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\categorias.dat");

        return resposta;
    }

    public Resposta registrarVenda(String idEstabelecimento) {

        Resposta resposta = new Resposta("sucesso", "venda adicionada");

        try {

            List<ProdutoEstabelecimento> produtos = (List<ProdutoEstabelecimento>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
            produtos = produtos.stream().filter(prod -> prod.getIdEstabelecimento().equals(idEstabelecimento)).toList();

            if(produtos.size() < 1) {
                resposta.status = "erro";
                resposta.msg = "nenhum produto adicionado ao estabelecimento";
                return resposta;
            }

            ProdutoEstabelecimento produto = null;
            boolean existe = false;

            String nome = Validacao.validarString("Nome do produto: ");

            for(ProdutoEstabelecimento prod : produtos) {
                if(prod.getNome().equals(nome)) {
                    existe = true;
                    produto = prod;
                }
            }

            if(!existe) {
                resposta.status = "erro";
                resposta.msg = "impossível encontrar produto no estabelecimento";
                return resposta;
            }

            int quantidade = Validacao.validarInt("Quantidade: ", 1,produto.getQuantidade() );
            double total = quantidade * produto.getPreco();

            List<Venda> vendas = new ArrayList<>();
            try {
                vendas = (ArrayList<Venda>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\vendas.dat"));
            } catch (ClassCastException e) {
                ;
            }

            vendas.add(new Venda(nome, quantidade, total, idEstabelecimento));
            GerenciarArquivos.escreverObjectos(vendas, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\vendas.dat");


        } catch(ClassCastException e) {
            resposta.status = "erro";
            resposta.msg = "nenhum produto adicionado ao estabelecimento";
        }
        return resposta;

    }

    public Gerente getDados() {
        String nomeEstabelecimento = Validacao.validarString("Nome do estabelecimento: ");
        String nome = Validacao.validarString("Nome do gerente: ");
        String password = Validacao.validarSenha("Senha do gerente: ", 6);

        List<Estabelecimento> estabelecimentos = new ArrayList<>();
        List<Gerente> gerentes = new ArrayList<>();

        try {

            estabelecimentos = (ArrayList<Estabelecimento>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\estabelecimentos.dat"));
            gerentes = (ArrayList<Gerente>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\gerentes.dat"));

            String idEstabelecimento = null;

            for(Estabelecimento est : estabelecimentos) {
                if(est.getNome().equals(nomeEstabelecimento)) {
                    idEstabelecimento = est.getIdEstabelecimento();
                    break;
                }
            }

            for(Gerente ger : gerentes) {
                if(ger.getNome().equals(nome) && ger.getPassword().equals(password) && ger.getIdEstabelecimento().equals(idEstabelecimento)) {
                    return ger;
                }
            }

        } catch(ClassCastException e) {
            IO.println("\nNehnhum armazém ou gerente cadastrados!\n");
        }

        return null;
    }

    public void listarCategorias() {
        try {
            List<Categoria> categorias = (List<Categoria>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\categorias.dat"));
            IO.println("\n============== Categorias ==============\n");
            for(Categoria categoria : categorias) {
                IO.println(categoria);
            }
        } catch (ClassCastException e) {
            IO.println("\nNenhuma categoria foi adicionada!\n");
        }
    }

    public void listarVendas(String idEstabelecimento) {
        try {
            List<Venda> vendas = (List<Venda>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\vendas.dat"));
            vendas = vendas.stream().filter(venda -> venda.getIdEstabelecimento().equals(idEstabelecimento)).toList();

            if(vendas.size() < 1) {
                IO.println("\nO estabelecimento ainda não efectuou nenhuma venda\n");
            } else {

                IO.println("\n============= Vendas do estabelecimento =============\n");

                for (Venda venda : vendas) {
                    IO.println(venda);
                }

            }

        } catch(ClassCastException e) {
            IO.println("\nO estabelecimento ainda não efectuou nenhuma venda\n");
        }

    }

    public void listarProdutos(String id, boolean estabelecimento) {
        if(estabelecimento) {
            try {
                List<ProdutoEstabelecimento> produtos = (List<ProdutoEstabelecimento>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
                produtos = produtos.stream().filter(prod -> prod.getIdEstabelecimento().equals(id)).toList();

                if(produtos.size() < 1) {
                    IO.println("\nEstabelecimento vazio\n");
                } else {

                    IO.println("\n=========== Produtos do estabelecimento ===========\n");

                    for(ProdutoEstabelecimento prod : produtos) {
                        IO.println(prod);
                    }

                }

            } catch(ClassCastException e) {
                IO.println("\nEstabelecimento vazio\n");
            }
        } else {
            try {
                List<ProdutoArmazem> produtos = (List<ProdutoArmazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
                produtos = produtos.stream().filter(prod -> prod.getIdArmazem().equals(id)).toList();
                if(produtos.size() < 1) {
                    IO.println("\nArmazém vazio vazio\n");
                } else {
                    IO.println("\n=========== Produtos do armazém ===========\n");
                    for (ProdutoArmazem prod : produtos) {
                        IO.println(prod);
                    }
                }
            } catch(ClassCastException e) {
                IO.println("\nArmazém vazio vazio\n");
            }
        }
    }

}
