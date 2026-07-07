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
        if (despesa >= 0 && despesa < despesas.size()){
            despesas.remove(despesa - 1);
        } else {
            System.out.println("Valor inválido");
        }

    }


}
