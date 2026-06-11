package servicos;

import entities.Gerente;
import utils.Validacao;

public class Menu {

    public static Servicos servicos = new Servicos();

    public static void menu1() {
        int opc = 0;

        do {

            IO.print(
                " =============== Menu =============== \n"+
                        "1. Adicionar estabelecimento\n"+
                        "2. Entrar em estabelecimento\n"+
                        "3. Sair\n"
            );

            opc = Validacao.validarInt("Escolha: ", 1, 3);

            switch (opc) {
                case 1:
                    IO.println(servicos.adicionarEstabelecimento());
                    break;

                case 2:
                    Gerente gerente = servicos.getDados();
                    if(gerente != null) {
                        IO.println("\nLogin realizado com sucesso!\n");
                        menu2(gerente);
                        opc = 3;
                    } else {
                        IO.println("\nNome ou senha inválida!\n");
                    }
                    break;
                case 3:
                    IO.println("Bye");
                    break;
            }

        } while(opc != 3);
    }

    public static void menu2(Gerente gerente) {

        int opc = 0;

        servicos.checkUp(gerente.getIdEstabelecimento(), gerente.getIdArmazem());

        do {

            IO.print(
                    "\n=============== Menu ===============\n"+
                            "1. Listar produtos do estabelecimento\n"+
                            "2. Listar produtos do armazém\n"+
                            "3. Listar categorias\n"+
                            "4. Listar vendas do estabelecimento\n"+
                            "5. Adicionar produtos ao estabelecimento\n"+
                            "6. Adicionar produtos ao armazém\n"+
                            "7. Adicionar categoria\n"+
                            "8. Registrar venda\n"+
                            "9. Dados do estabelecimento\n"+
                            "10. Sair\n"
            );

            opc = Validacao.validarInt("Escolha: ", 1, 10);

            switch (opc) {
                case 1:
                    servicos.listarProdutos(gerente.getIdEstabelecimento(), true);
                    break;
                case 2:
                    servicos.listarProdutos(gerente.getIdArmazem(), false);
                    break;
                case 3:
                    servicos.listarCategorias();
                    break;
                case 4:
                    servicos.listarVendas(gerente.getIdEstabelecimento());
                    break;
                case 5:
                    IO.println(servicos.adicionarProdutosEstabelecimento(gerente.getIdEstabelecimento(), gerente.getIdArmazem(), " "));
                    break;
                case 6:
                    servicos.adicionarProdutosArmazem(gerente.getIdArmazem());
                    break;
                case 7:
                    servicos.adicionarCategoria();
                    break;
                case 8:
                    IO.println(servicos.registrarVenda(gerente.getIdEstabelecimento(), gerente.getIdArmazem()));
                    break;
                case 9:
                    servicos.dadosDoEstabelecimento(gerente);
                    break;
                case 10:
                    IO.println("\n\t\t\t AU REVOIR");
                    break;
            }

        } while(opc != 10);

    }

}
