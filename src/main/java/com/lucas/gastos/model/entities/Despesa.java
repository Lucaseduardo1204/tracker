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
        this.valor = valor;
        this.data = data;
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
