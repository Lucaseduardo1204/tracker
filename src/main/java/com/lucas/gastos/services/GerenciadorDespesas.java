package com.lucas.gastos.services;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.model.enums.CategoriaEnum;
import com.lucas.gastos.repository.DespesaRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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

    // Percorre a lista de despesas e acumula o valor de cada uma no total
    // da sua categoria, num Map<CategoriaEnum, BigDecimal> (chave = categoria,
    // valor = soma acumulada)
    // despesa.getCategoria = retorna a categoria

    //método publico do qual cria uma tabela hash fazendo com que a categoria seja a "chave" e BigDecimal seja o valor
//    public Map<CategoriaEnum, BigDecimal> totalPorCategoria(){
//
//        //declara uma variável (totalCategoria) do tipo Map<CategoriaEnum, BigDecimal>, como um novo HashMap<>();
//        Map<CategoriaEnum, BigDecimal> totalCategoria = new HashMap<>();
//
//        //para cada despesa na lista de despesas
//        for(Despesa despesa : despesas){
//            //Declara totalPorCategoria como BigDecimal
//            // getOrDefault(chave, valor padrão) -> procura a chave no mapa, se achar te entrega o valor, se não achar, entrega o Plano B no lugar de Null
//            // Ou seja, se no mapa.get(chave) ele não encontrar, retorna null. No mapa.getOrDefault() se não achar, retorna o segundo parâmetro (no caso BigDecimal.ZERO)
//            // .getOrDefault apenas lê, não altera, devolve 0  se não achar a chave
//            BigDecimal totalAtual = totalCategoria.getOrDefault(despesa.getCategoria(), BigDecimal.ZERO);
//
//            //novo total do tipo BigDecimal, recebe o totalPorCategoria mais a despesa.getValor();
//            BigDecimal novoTotal = totalAtual.add(despesa.getValor());
//
//            //Altera a variavel (do tipo Map, new HashMap) totalCategoria a categoria, e o novo valor
//            // .put -> só grava (cria ou sobrescreve, soma é feita antes no .add()
//            totalCategoria.put(despesa.getCategoria(), novoTotal);
//        }
//        return totalCategoria; //retona nosso Map totalCategoria
//
//        // EM Resumo:
//        // mapa nasce vazio; uma chave só passa a existir quando um put cria ela
//        // getOrDefault = pesquisa (SÓ lê); chave ausente → devolve o plano B
//        // put = grava SEMPRE (cria ou sobrescreve; sem condição, sem soma)
//    }

    // método público que retorna uma despesa
//    public Despesa despesaDeMaiorValor(){
//
//        if (despesas.isEmpty()){
//            //IllegalStateException
//            throw new IllegalStateException("Não há despesas cadastradas");
//        }
//        //cria variavel do tipo despesa e a inicializa com a primeira despesa de despesas
//        Despesa maiorDespesa = despesas.get(0);
//
//        //pra cada despesa em despesas
//        for (Despesa despesa : despesas){
//            // se o valor da despesa, comparado ao valor da maior despesa, for maior que 0
//            // .compareTo() recebe como parametro um valor em BigDecimal, caso retorne positivo o valor que chamou o compareTo, é maior
//            // se retornar negativo, o valor que chamou é menor, se retornar 0 é igual
//            if (despesa.getValor().compareTo(maiorDespesa.getValor()) > 0){
//                // Maior despesa recebe a despesa
//                maiorDespesa = despesa;
//            }
//        }
//        return maiorDespesa;
//    }

    public Despesa despesaMaiorValorStream(){
        // O stream() é um metodo que vai percorrer os itens no nosso exemplo de despesa, ele
        // vai pegar despesas  e percorrer todos os itens, no .max() ele chama comparator.comparing que vai comparar o valor
        //  da ultima com a nova e devolver a despesa, o orElseThrow é o que vai verificar, se tiver vazia, lança a exception,
        // se não, devolve o item pro return
        //.stream() percorre todos os itens da lista
        //.max() maior até agora
        // Comparator.comparing(Despesa::getValor) -> "compare-as pelo valor"
        // Method reference -> Despesa::getValor -> de cada uma, o valor
        // .orElsethrow -> abre a caixa: cheia → devolve o conteúdo (alimenta o return); vazia → fabrica e lança a exceção
        // () -> new IllegalStateException -> uma nova exceção por lambda function (só é executada se o ElseThrow retornar vazio)
        return despesas.stream()
                .max(Comparator.comparing(Despesa::getValor))
                .orElseThrow(() -> new IllegalStateException("Não há despesas cadastradas"));

    }

    // Método público que devolve um Map: chave = categoria, valor = soma acumulada
    // Faz o mesmo que o TotalPorCategoria() do for, porém com stream()
    public Map<CategoriaEnum, BigDecimal> totalPorCategoriaStream() {

        // .stream() -> coloca a lista em uma "esteira)
        return despesas.stream()

                // .collect(...) -> Terminal, pega o que sai da esteira e monta a estrutura final
                .collect(Collectors.groupingBy(

                        // Primeiro argumento da regra de separação "Olhe a categoria dessa Despesa"
                        // Method reference: ação, não nome de tipos como CategoriaEnum
                        Despesa::getCategoria,

                        // Segundo argumento, o que agrupa-los em cada "caixa "
                        Collectors.reducing(
                                BigDecimal.ZERO, // Toda soma começa no 0
                                Despesa::getValor,  // de cada despesa do grupo, o valor
                                BigDecimal::add)    // vai somando
                ));

    }
}



