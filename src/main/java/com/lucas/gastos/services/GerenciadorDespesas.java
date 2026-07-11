package com.lucas.gastos.services;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.repository.DespesaRepository;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDespesas {

    // Instância repository como uma nova DespesaRepositoy
    private DespesaRepository repository = new DespesaRepository();

    // Declara lista do tipo Despesa como um novo ArrayList<>()
    private List<Despesa> despesas = new ArrayList<>();

    // No construtor de GerenciadorDespesas, a variável (lista) da classe recebe a resposta do método carregar da classe
    // repository
    public GerenciadorDespesas(){
        this.despesas = repository.carregar();
    }


    // Método que recebe uma despesa como parametro (d), e adiciona na lista da classe, após isso chama o repository para
    // persistencia do objeto no arquivo csv
    public void adicionarDespesa(Despesa d){
        despesas.add(d);
        repository.salvar(despesas);
    }

    //passa uma cópia de nossa lista para que ela não seja alterada
    public List<Despesa> listar() {
        return new ArrayList<>(despesas);
    }

    //remover recebe um inteiro como indice
    public void remover(int despesa){
        // tratamos o indice para que se adapte a índices do programa, por exemplo, caso usuario digite 4, no programa
        // seria 3 pois o zero também é um índice
        int indice = despesa -1;
        //Se indice for maior ou igual a zero e indice for menor que o tamanho da lista de despesas
        if (indice >= 0 && indice < despesas.size()){
            //então remove a despesa na posição passada
            despesas.remove(indice);
            repository.salvar(despesas);
        } else {
            // exibe no terminal a mensagem de que o valor passado foi inválido
            System.out.println("Valor inválido");
        }

    }


}
