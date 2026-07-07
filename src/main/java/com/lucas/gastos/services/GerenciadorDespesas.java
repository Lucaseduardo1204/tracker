package com.lucas.gastos.services;

import com.lucas.gastos.model.entities.Despesa;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorDespesas {

    private List<Despesa> despesas = new ArrayList<>();


    public void adicionarDespesa(Despesa d){
        despesas.add(d);
    }

    public List<Despesa> listar() {
        return new ArrayList<>(despesas);
    }

    public void remover(Despesa despesa){
        despesas.remove(despesa);
    }


}
