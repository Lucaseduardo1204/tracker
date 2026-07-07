package com.lucas.gastos.model.entities;

import com.lucas.gastos.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Despesa {
    private BigDecimal valor;

    private LocalDate data;

    private CategoriaEnum categoria;

    private String descricao;

    private FormaPagamentoEnum formaPagamento;

    private BancoEnum banco;

    public Despesa(BigDecimal valor, LocalDate data, CategoriaEnum categoria, String descricao, FormaPagamentoEnum formaPagamento, BancoEnum banco){
        //Caso a verificação fosse lançada apenas com um if, e um sout indicando que o valor é inválido, a despesa nasceria com o valor
        // igual a null, já fazendo que ela seja criada de maneira incorreta. Se existe dados incorretos, nulos ou errados, então o objeto não deve existir
        // Com o throw new IllegalArgumentsException, lança a exceção de argumento ilegal indicando que o valor não pode ser negativo
        // Aqui é checkado o erro e não o acerto
        if (valor.signum() == -1) {
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
        this.valor = valor;

        //Como seria realizada a validação de datas, poderiam ser retroativas (se sim), quanto tempo? prospectivas?
        //Definido: lançamento das datas da despesa não podem ser futuras e não podem ser nulas, futuramente pode ser implementada a verificação de datas retroativas
        // Primeiro, verifica se é nula, depois verifica se é futura

        if(data == null){
            throw new IllegalArgumentException("Data deve ser válida");
        }
        this.data = data;

        if (data.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Data não pode ser futura!");
        }

        this.categoria = categoria;
        this.descricao = descricao;
        this.formaPagamento = formaPagamento;
        this.banco = banco;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public CategoriaEnum getCategoria() {
        return categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public FormaPagamentoEnum getFormaPagamento() {
        return formaPagamento;
    }

    public BancoEnum getBanco() {
        return banco;
    }

    @Override
    public String toString(){
        return "Valor: " + getValor()
                +  "\nData: " + getData()
                +  "\nCategoria: " + getCategoria()
                +  "\nDescrição: " + getDescricao()
                +  "\nForma de pagamento: " + getFormaPagamento()
                +  "\nBanco: " + getBanco();
    }
}
