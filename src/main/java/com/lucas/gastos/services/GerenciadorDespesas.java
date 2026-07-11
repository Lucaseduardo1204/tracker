package com.lucas.gastos.services;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.repository.DespesaRepository;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDespesas {


    private DespesaRepository repository = new DespesaRepository();

    private List<Despesa> despesas = new ArrayList<>();

    public GerenciadorDespesas(){  //Gerenciador nasce, pede pro repositório e guarda a resposta na lista de despesas
        this.despesas = repository.carregar();
    }


    public void adicionarDespesa(Despesa d){
        despesas.add(d);
        repository.salvar(despesas);
    }

    //passa uma cópia de nossa lista para que ela não seja alterada
    public List<Despesa> listar() {
        return new ArrayList<>(despesas);
    }

    public void remover(int despesa){
        int indice = despesa -1;
        if (indice >= 0 && indice < despesas.size()){
            despesas.remove(indice);
        } else {
            System.out.println("Valor inválido");
        }

    }


}
