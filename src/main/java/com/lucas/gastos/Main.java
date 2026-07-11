package com.lucas.gastos;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.model.enums.BancoEnum;
import com.lucas.gastos.model.enums.CategoriaEnum;
import com.lucas.gastos.model.enums.FormaPagamentoEnum;
import com.lucas.gastos.services.GerenciadorDespesas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        //Instância um gerenciador, do tipo Gerenciador despesa, como um novo GerenciadorDespesas
        GerenciadorDespesas gerenciador = new GerenciadorDespesas();

        //para o fim do do-while
        boolean resposta = true;

        do {
            //exibe o menu de seleção
            System.out.println(menuSelecao());

            //lê a opção e armazena na variável opção
            int opcao = sc.nextInt();
            //limpa o buffer de sc, consumindo o enter
            sc.nextLine();

            //verificação da opção e "decisões" baseadas nelas
            switch (opcao) {
                case 1:
                    //chama o questionário, passa nosso sc para entrada dos dados, e cria a despesa
                    Despesa nova = criarDespesa(sc);

                    //chama o gerenciador e adiciona a despesa na lista criada na classe GerenciadorDespesas
                    gerenciador.adicionarDespesa(nova);

                    //finaliza
                    break;

                case 2:
                    //chama o método de listar da classe GerenciadorDespesa
                    System.out.println(gerenciador.listar());

                    break;

                case 3:
                    System.out.println("Digite o número da despesa a ser removida: ");
                    int despesaARemover = sc.nextInt();
                    gerenciador.remover(despesaARemover);
                    break;

                case 4:
                    resposta = false;
                    break;

                default:
                    System.out.println("Opção inválida, tenta de novo!");
                    break;

            }
        }while(resposta);


        /*Teste do equals e hashcode

        Despesa d1 = new Despesa(new BigDecimal("50"), LocalDate.of(2026, 7, 7), CategoriaEnum.ALIMENTACAO, "almoço", FormaPagamentoEnum.CREDITO, BancoEnum.NUBANK);
        Despesa d2 = new Despesa(new BigDecimal("50"), LocalDate.of(2026, 7, 7), CategoriaEnum.ALIMENTACAO, "almoço", FormaPagamentoEnum.CREDITO, BancoEnum.NUBANK);

        Set<Despesa> conjunto = new HashSet<>();
        conjunto.add(d1);
        conjunto.add(d2);

        System.out.println("Tamanho do set: " + conjunto.size());  --Imprimiu 1, no caso, reconheceu que d1 e d2 s~ao a mesma e descartou a duplicata*/

    }

    //função responsável pelo quetionário de criação da despesa, o qual deverá ser respondido pelo usuário
    public static Despesa criarDespesa(Scanner sc){
        System.out.println("Digite o valor da despesa: ");
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine(); //limpa o enter

        System.out.println("Digite a Data da despesa (dd/MM/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(sc.nextLine(), formatter);

        System.out.println("Digite a categoria da despesa: ");

        // variavel categoria do tipo CategoriaEnum, Categoria enum pega o valor que é passado no parâmetro, ou seja, o
        // scanner lê a próxima linha, joga tudo pra maiúsculo como tratamento, e joga pro valor da CategoriaEnum, o qual
        // vai ser armazenado na variável, outra maneira de se fazer isso é atravéz de um menu de numeros inteiros onde
        // cada um representará uma categoria, minimizando erros
        CategoriaEnum categoria =  CategoriaEnum.valueOf(sc.nextLine().toUpperCase());

        System.out.println("Digite a descricao da despesa: ");
        String descricao = sc.nextLine();

        System.out.println("Digite a forma de pagamento da despesa: ");
        FormaPagamentoEnum formaDePagamento = FormaPagamentoEnum.valueOf(sc.nextLine().toUpperCase());

        System.out.println("Digite o banco registrada a despesa: ");
        BancoEnum banco = BancoEnum.valueOf(sc.nextLine().toUpperCase());

        return new Despesa(valor, data, categoria, descricao, formaDePagamento, banco);

    }

    public static String menuSelecao(){
        return "----------------------DESPESAS--------------------------" +
                " \nSELECIONE A OPÇÃO DESEJADA " +
                "\n 1 - Adicionar despesa" +
                "\n 2 - Listar Despesas" +
                "\n 3 - Excluir Despesas " +
                "\n 4 - Sair";
    }


}

