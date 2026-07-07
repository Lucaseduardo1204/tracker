package com.lucas.gastos;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.model.enums.BancoEnum;
import com.lucas.gastos.model.enums.CategoriaEnum;
import com.lucas.gastos.model.enums.FormaPagamentoEnum;
import com.lucas.gastos.services.GerenciadorDespesas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        GerenciadorDespesas gerenciador = new GerenciadorDespesas();

        System.out.println(menuSelecao());

        int opcaoEscolhida = sc.nextInt();
        sc.nextLine();

        if (opcaoEscolhida == 1){
            Despesa nova = criarDespesa(sc);
            gerenciador.adicionarDespesa(nova);


        }

    }

    public static Despesa criarDespesa(Scanner sc){
        System.out.println("Digite o valor da despesa: ");
        BigDecimal valor = sc.nextBigDecimal();

        System.out.println("Digite a Data da despesa: ");
        LocalDate data = LocalDate.now(); // só pra representar

        System.out.println("Digite a categoria da despesa: ");
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
                "\n 3 - Excluir Despesas \n";
    }


}

