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
        GerenciadorDespesas gerenciador = new GerenciadorDespesas();

        boolean resposta = true;

        do {
            System.out.println(menuSelecao());

            int opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    Despesa nova = criarDespesa(sc); //chama o questionário, passa nosso sc para entrada dos dados, e cria a despesa
                    gerenciador.adicionarDespesa(nova); //chama o gerenciador e adiciona a despesa na lista criada na classe GerenciadorDespesas
                    break;
                case 2:
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
        }while(resposta == true);

    }

    //função responsável pelo quetionário de criação da despesa, o qual deverá ser respondido pelo usuário
    public static Despesa criarDespesa(Scanner sc){
        System.out.println("Digite o valor da despesa: ");
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine(); //limpa o enter

        System.out.println("Digite a Data da despesa (dd/MM/yyyy): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(sc.nextLine(), formatter); // só pra representar

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

// Anotações:
/*
*  == compara o endereço e não o conteúdo.Ou seja, se  implementar, duas despesas idênticas (mesmo valor, mesma
*  data, mesma categoria) são consideradas diferentes, porque são dois objetos distintos na memória.
*
*  .equals() = o conteúdo é equivalente? valem a mesma coisa?. É aqui que VOCÊ entra decidindo o que "equivalente" significa pro teu objeto.
    *
*
*
*
* */