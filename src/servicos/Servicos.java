package servicos;

import entities.*;
import utils.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    public Resposta adicionarProdutosEstabelecimento(String idEstabelecimento, String idArmazem, String produto) {

        Resposta resposta = new Resposta("sucesso", "");

        IO.println("\n=========== Adicione produtos ao estabelecimento ===========\n");

        try {

            List<ProdutoArmazem> produtosDoArmazem = (ArrayList<ProdutoArmazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
            List<ProdutoEstabelecimento> produtosDoEstabelecimento = new ArrayList<>();
            boolean vazio = true;
            try {
                produtosDoEstabelecimento = (ArrayList<ProdutoEstabelecimento>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
                vazio = false;
            } catch (ClassCastException e) {
                ;
            }

            boolean existe = false;
            String nome = produto;

            if(nome.equals(" ")) {
                nome = Validacao.validarString("Produto: ");
            }

            for(ProdutoArmazem prod : produtosDoArmazem) {
                if(prod.getNome().equals(nome) && prod.getIdArmazem().equals(idArmazem)) {
                    existe = true;
                    int quantidade = Validacao.validarInt("Quantidade: ", 1, prod.getQuantidade());
                    if(vazio) {
                        produtosDoEstabelecimento.add(new ProdutoEstabelecimento(nome, quantidade, prod.getPreco(), prod.getPrazo(), prod.getIdCategoria(), idEstabelecimento));
                    } else {
                        boolean find = false;
                        for(ProdutoEstabelecimento pr : produtosDoEstabelecimento) {
                            if(pr.getNome().equals(nome) && pr.getIdEstabelecimento().equals(idEstabelecimento)) {
                                find = true;
                                produtosDoEstabelecimento.get((produtosDoEstabelecimento.indexOf(pr))).setQuantidade(pr.getQuantidade()+quantidade);
                            }
                        }
                        if(!find) {
                            produtosDoEstabelecimento.add(new ProdutoEstabelecimento(nome, quantidade, prod.getPreco(), prod.getPrazo(), prod.getIdCategoria(), idEstabelecimento));
                        }
                    }
                    actualizarProdutos(nome, quantidade, false, idEstabelecimento, prod.getIdArmazem());
                    GerenciarArquivos.escreverObjectos(produtosDoEstabelecimento, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat");
                    resposta.msg = "produto " + prod.getNome() + " adicionado ao estabelecimento";
                }
            }

            if(!existe) {
                resposta.status = "erro";
                resposta.msg = "o produto não existe no armazém";
                return resposta;
            }

        } catch(ClassCastException e) {
            resposta.status = "erro";
            resposta.msg = "nenhum produto no armazém para adicionar ao estabelecimento";
            return resposta;
        }

        return resposta;
    }

    public void adicionarProdutosArmazem(String id) {
        try {
            List<Categoria> categorias = (List<Categoria>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\categorias.dat"));
            IO.println("\n\t\t================ Adicione produtos ao armazém ================\t\t\n");

            IO.println("\n\t\t=========== Categorias ===========\t\t\n");

            for(Categoria cat : categorias) {
                IO.println(cat);
            }

            List<ProdutoArmazem> produtos = new ArrayList<>();
            String more = "";
            do {

                try {
                    produtos = (List<ProdutoArmazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
                } catch(ClassCastException e) {
                    ;
                }

                boolean existe = false;
                String nome = " ";
                do {
                    existe = false;
                    nome = Validacao.validarString("Produto: ");
                    for(ProdutoArmazem prod : produtos) {
                        if(prod.getNome().equals(nome)) {
                            IO.println("\nProduto já existe no armazém, tente novamente!\n");
                            existe = true;
                        }
                    }
                } while(existe);

                int quantidade = Validacao.validarInt("Quantidade: ", 1, 1000);
                double preco = Validacao.validarDouble("Preço: ", 0, 100000);
                IO.println("Prazo: ");
                String prazo = "";

                try {
                    do {
                        int dia = Validacao.validarInt("Dia: ", 1, 31);
                        int mes = Validacao.validarInt("Mês: ", 1, 12);
                        int ano = Validacao.validarInt("Ano: ", 2026, 2040);

                        if(dia < 10 && mes < 10) {
                            prazo = "0"+dia + "/0" + mes + "/" + ano;
                        } else if(mes < 10) {
                            prazo = dia + "/0" + mes + "/" + ano;
                        } else if (dia < 10) {
                            prazo = "0"+dia + "/" + mes + "/" + ano;
                        } else {
                            prazo = dia + "/" + mes + "/" + ano;
                        }

                        if(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(prazo, DateTimeFormatter.ofPattern("dd/MM/yyyy"))) < 10) {
                            IO.println("\nO prazo de validade do produto deve ser 10 dias maior que a data actual, digite novamente!\n");
                        }

                    } while (ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(prazo, DateTimeFormatter.ofPattern("dd/MM/yyyy"))) < 10);
                } catch(DateTimeParseException e) {
                    IO.println("\nData inválida!\n");
                    return;
                }

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

                produtos.add(new ProdutoArmazem(nome, quantidade, preco, prazo, idCategoria, id));

                GerenciarArquivos.escreverObjectos(produtos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat");

                more = Validacao.validarString("Adicionar mais[Y/N]: ");

            } while(more.toLowerCase().equals("y"));

        } catch(ClassCastException e) {
            IO.println("\nAdicione categorias antes de adicionar produtos ao armazém!\n");
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

    public Resposta registrarVenda(String idEstabelecimento, String idArmazem) {

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

            actualizarProdutos(nome, quantidade, true, idEstabelecimento, idArmazem);

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

        try {

            List<Estabelecimento> estabelecimentos = (ArrayList<Estabelecimento>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\estabelecimentos.dat"));
            List<Gerente> gerentes = (ArrayList<Gerente>) (GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\gerentes.dat"));

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

    public void actualizarProdutos(String produto, int quantidade, boolean estabelecimento, String idEst, String idArm) {
        try {
            int idProd = 0;
            if(estabelecimento) {
                String adicionar = " ";
                List<ProdutoEstabelecimento> produtos = (ArrayList<ProdutoEstabelecimento>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
                for(ProdutoEstabelecimento prod : produtos) {
                    if(prod.getNome().equals(produto) && prod.getIdEstabelecimento().equals(idEst)) {
                        prod.setQuantidade(prod.getQuantidade() - quantidade);
                        idProd = produtos.indexOf(prod);
                    }
                }
                if(produtos.get(idProd).getQuantidade() == 0) {
                    produtos.remove(produtos.get(idProd));
                } else if(produtos.get(idProd).getQuantidade() <= 10) {
                    IO.println("\n\n\t\t\t"+produtos.get(idProd).getNome()+" em baixa quantidade no estabelecimento, "+produtos.get(idProd).getQuantidade()+"!\n"
                    );

                    adicionar = Validacao.validarString("\n\t\t\tAdicione o produto ao estabelecimento[Y/N]: ");

                }
                GerenciarArquivos.escreverObjectos(produtos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat");
                if(adicionar.toLowerCase().equals("y")) {
                    adicionarProdutosEstabelecimento(idEst, idArm, produtos.get(idProd).getNome());
                }
            } else {
                List<ProdutoArmazem> produtos = (ArrayList<ProdutoArmazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
                for(ProdutoArmazem prod : produtos) {
                    if(prod.getNome().equals(produto) && prod.getIdArmazem().equals(idArm)) {
                        prod.setQuantidade(prod.getQuantidade() - quantidade);
                        idProd = produtos.indexOf(prod);
                    }
                }
                if(produtos.get(idProd).getQuantidade() == 0) {
                    produtos.remove(produtos.get(idProd));
                } else if(produtos.get(idProd).getQuantidade() <= 20) {
                    IO.println("\n\t\t\t"+produtos.get(idProd).getNome()+" em baixa quantidade no armazém, "+produtos.get(idProd).getQuantidade()+"!\n"
                    );
                }
                GerenciarArquivos.escreverObjectos(produtos, "C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat");
            }
        } catch(ClassCastException e) {
              ;
        }
    }

    public void dadosDoEstabelecimento(Gerente gerente) {
        Estabelecimento estabelecimento = ((ArrayList<Estabelecimento>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\estabelecimentos.dat"))).stream().filter(est -> est.getIdEstabelecimento().equals(gerente.getIdEstabelecimento())).toList().getFirst();
        Armazem armazem = ((ArrayList<Armazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\armazens.dat"))).stream().filter(ar -> ar.getIdEstabelecimento().equals(gerente.getIdEstabelecimento())).toList().getFirst();

        IO.println("\n\t\t\t================= Dados do estabelecimento =================\n");
        IO.print(estabelecimento);
        IO.println("Localização do armazém: "+armazem.getLocalizacao());
        IO.print(gerente);

    }

    public void checkUp(String idEstabelecimento, String idArmazem) {

        try {

            List<ProdutoEstabelecimento> produtosDoEstabelecimento = (List<ProdutoEstabelecimento>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtoses.dat"));
            produtosDoEstabelecimento = produtosDoEstabelecimento.stream().filter(prod -> prod.getIdEstabelecimento().equals(idEstabelecimento)).toList();

            List<ProdutoArmazem> produtos = (List<ProdutoArmazem>)(GerenciarArquivos.lerObjectos("C:\\Users\\eucli\\OneDrive\\Documentos\\Projecto\\src\\files\\produtosar.dat"));
            produtos = produtos.stream().filter(prod -> prod.getIdArmazem().equals(idArmazem)).toList();

            int primeiro = 0;

            for(ProdutoArmazem prod : produtos) {
                if(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(prod.getPrazo(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) < 60) {
                    if(primeiro == 0) {
                        IO.println("\n\t\t=============== Produtos do armazém a 60 dias de expirar prazo ===============\n\n");
                        primeiro = 1;
                    }
                    IO.println(prod);
                }
            }

            primeiro = 0;

            for(ProdutoEstabelecimento prod : produtosDoEstabelecimento) {
                if(ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(prod.getPrazo(), DateTimeFormatter.ofPattern("dd/MM/yyyy"))) < 60) {
                    if(primeiro == 0) {
                        IO.println("\n\t\t=============== Produtos do estabelecimento a 60 dias de expirar prazo ===============\n\n");
                        primeiro = 1;
                    }
                    IO.println(prod);
                }
            }

        } catch(ClassCastException | DateTimeParseException e) {
            ;
        }

    }

}
