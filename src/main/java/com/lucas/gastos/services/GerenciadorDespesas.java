package com.lucas.gastos.services;

import com.lucas.gastos.model.entities.Despesa;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDespesas {

    private List<Despesa> despesas = new ArrayList<>();


    public void adicionarDespesa(Despesa d){
        despesas.add(d);
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
