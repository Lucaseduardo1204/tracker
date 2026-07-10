package com.lucas.gastos.model.entities;

import com.lucas.gastos.model.enums.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

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
        if (valor == null){
            throw new IllegalArgumentException("Valor n~ao deve ser nulo");
        }

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

        if (data.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Data não pode ser futura!");
        }

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
        return "\nValor: " + getValor()
                +  "\nData: " + getData()
                +  "\nCategoria: " + getCategoria()
                +  "\nDescrição: " + getDescricao()
                +  "\nForma de pagamento: " + getFormaPagamento()
                +  "\nBanco: " + getBanco() + "\n";
    }


    // Anotações:
    /*
     *  == compara o endereço e não o conteúdo.Ou seja, se  implementar, duas despesas idênticas (mesmo valor, mesma
     *  data, mesma categoria) são consideradas diferentes, porque são dois objetos distintos na memória.
     *
     *  .equals() = o conteúdo é equivalente? valem a mesma coisa?. É aqui que entra a decisão o que "equivalente"\
     *  significa pro objeto.
     *
     * O que faz duas despesas minhas serem a mesma? Quando valor, data, categoria e descrição são os mesmos?
     *
     * hashcode: é um número que o objeto gera para dizer "eu pertenço a tal grupo". Estruturas como
     * HashSet e HashMap usam esse número para organizar os objetos (buckets) e achar as coisas
     * sem precisar varrer tudo
     *
     * Se dois objetos são iguais pelo equals, eles TEM que ter o mesmo hashCode
     *
     * Se eu escrevo equals, automaticamente devo implantar o hashCode, pois se não implementada, o hashset
     * procura duplicata onde não existe ou acha onde não tem. Implementou um, implementa o outro, usando os
     * MESMOS campos
     *
     * == -> mesma cédula física (endereço de memória) - Java faz
     * .equals() -> valem a mesma coisa? (conteúdo)  - Eu decido o critério
     * .hashCode() -> para busca rápida - anda colado no  equals
     * ***Iguais no equals -> mesmo hashCode, sempre, mesmos campos
     *
     * Resumo: se eu inserir duas despesas, ao comparar com o == ele vai verificar se serão o mesmo objeto
     * na memória, no caso não, ao configurar o .equals() eu decido qual o critério, se o conteúdo é
     * equivalente por exemplo.
     * SE o equals não for escrito, por padrão, o java compara endereços de memória. Então se duas despesas
     * idênticas (mesmo valor, data, categoria) seriam vistas como diferentes, pois seriam objetos distintos
     * escrevemos o equals para parametrizalo quando eles serão iguais.
     * Objetos iguais no equals TEM que ter o mesmo hashCode. Senão a busca não acha a duplicata. Por isso os
     * dois sempre andam juntos, usando os mesmos campos
     *
     * */
    @Override
    public boolean equals(Object o){      //equals recebe um Object genérico

        if (this == o) return true;      // é o mesmo objeto?

        if (o == null || getClass() != o.getClass()) return false; // se é null ou de classe diferente, retorna falso

        Despesa despesa = (Despesa) o;  //converte para Despesa  e compara os campos

        return Objects.equals(valor, despesa.valor) && Objects.equals(data, despesa.data) && Objects.equals(categoria, despesa.categoria) &&
                Objects.equals(descricao, despesa.descricao) && Objects.equals(formaPagamento, despesa.formaPagamento) && Objects.equals(banco, despesa.banco);

    }

    @Override
    public int hashCode(){
        return Objects.hash(valor, data, categoria, descricao, formaPagamento, banco);
    }
}
