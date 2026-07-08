package com.lucas.gastos.repository;

import com.lucas.gastos.model.entities.Despesa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DespesaRepository {

    public  void salvar(List<Despesa> despesas){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gastos.csv"))){
            for (Despesa despesa : despesas){
                String linha = despesa.getDescricao() + ";" + despesa.getValor() + ";"  + despesa.getData() + ";" + despesa.getCategoria() + ";"
                        + despesa.getFormaPagamento() + ";" + despesa.getBanco();
                writer.write(linha); writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Não foi possível salvar no arquivo: " + e.getMessage());
        }

        //try - tenta executar algo
        // catch - captura algo inesperado

        //Unchecked (IllegalArgumentException, NullPointerException...) -  se trata de erros de lógica ou de programação - Resumindo, erro meu
        //Checked (IOException) -  erros externos, disco cheio, arquivo travado por programas, remoção do pendrive durante a escrita...  -  erro externo
    }
}
