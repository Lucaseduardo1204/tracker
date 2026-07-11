package com.lucas.gastos.repository;

import com.lucas.gastos.model.entities.Despesa;
import com.lucas.gastos.model.enums.BancoEnum;
import com.lucas.gastos.model.enums.CategoriaEnum;
import com.lucas.gastos.model.enums.FormaPagamentoEnum;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class DespesaRepository {


    //Aqui eu preciso carregar o arquivo, ler a primeira linha, separar campo por campo, atribuir uma nova despesa com os
    //campos carregados do arquivo retornar a lista com as despesas carregadas

    public List<Despesa> carregar(){

        //declara lista carregada como uma lista de Despesa
        List<Despesa> listaCarregada = new ArrayList<>();

        String linha;
        //Declara reader como um BufferedReader, ele sera um novo BufferedReader que, como parametro, constroi um novo
        // FileReader que recebe como parametro o nome do arquivo (gastos.csv)
        try (BufferedReader reader = new BufferedReader(new FileReader("gastos.csv"))){

            //Enquanto não chegar no fim do arquivo
            while ((linha = reader.readLine()) != null){
                // conversões podem estourar IllegalArgumentException (valor/enum); o try remedia linha a linha
                // sem finalizar o carregamento.  (DateTimeException) - atenção para trata-lo depois
                try {
                    //separa a string, transforma em item tudo que está antes do ;, e armazena no array do tipo de string
                    String[] campos  =  linha.split(";");

                    // Caso a linha com campos estivessem todas vazias, não faria com que caisse no IllegalArgumentsException
                    // e sim no ArrayIndexOutOfBoundsException, apos passar pelo primeiro campo, o programa pede algo que não
                    // existe, pois campos seria um array com apenas 1 elemento (a string vazia). Podemos tratar de duas
                    // maneiras, lancando mais um catch (remediando) ou previnindo fazendo alguma verificação antes do erro
                    // que é o caso
                    if (linha.isBlank() || campos.length != 6){
                        System.out.println("linha ignorada, formato inválido");
                        continue; //cancela a linha e pula pra próxima

                    }
                    // armazena campo na posição 0 na variável descricao
                    String descricao = campos[0];
                    // converte campo na posição 1 em um BigDecimal usando o construtor que aceita texto
                    BigDecimal valor = new BigDecimal(campos[1]);
                    // converte o texto do campo na posição 2 em um LocalDate
                    LocalDate data = LocalDate.parse(campos[2]);
                    // converte campo na posição 3 em um enum e armazena em categoria
                    CategoriaEnum categoria = CategoriaEnum.valueOf(campos[3]);
                    FormaPagamentoEnum formaPagamento = FormaPagamentoEnum.valueOf(campos[4]);
                    BancoEnum banco = BancoEnum.valueOf(campos[5]);

                    // instancia uma nova despesa com os campos extraidos do arquivo
                    Despesa novaDespesa = new Despesa(valor, data, categoria, descricao, formaPagamento, banco);
                    // adiciona na lista
                    listaCarregada.add(novaDespesa);

                }catch (IllegalArgumentException | DateTimeParseException e){   //caso caia na excessão, ignora a linha e a exibe no console com a mensagem
                    System.out.println("Linha ignorada (dado corrompido): " + linha);
                }
            }
         //se não achar o arquivo (nova execução) não faz nada
        } catch (FileNotFoundException e){

        } //em caso de falha externa
        catch (IOException e) {
            System.out.println("Falha na leitura do arquivo csv!");
        }
        // retorna a lista
        return listaCarregada;
    }

    public  void salvar(List<Despesa> despesas){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("gastos.csv"))){ // Declara writer do
            // tipo BufferedWriter, como um novo BufferedWriter, como parâmetro recebe um novo FileWriter que escreverá
            // o arquivo, nomeado como gastos.csv

            //para cada despesa em despesas, linha recebe atributo + ; (definito para o meu csv, podendo tambem ser , ou |)
            for (Despesa despesa : despesas){
                String linha = despesa.getDescricao() + ";" + despesa.getValor() + ";"  + despesa.getData() + ";" + despesa.getCategoria() + ";"
                        + despesa.getFormaPagamento() + ";" + despesa.getBanco();
                //writer escreve a linha no arquivo e após, cria uma nova linha para a próxima despesa
                writer.write(linha); writer.newLine();
            }
        } //caso de errado, pega a exception, do tipo IOException (InputOutput) e exibe o sout na tela
        catch (IOException e) {
            System.out.println("Não foi possível salvar no arquivo: " + e.getMessage());
        }

        //try - tenta executar algo
        // catch - captura algo inesperado

        //Unchecked (IllegalArgumentException, NullPointerException...) -  se trata de erros de lógica ou de programação - Resumindo, erro meu
        //Checked (IOException) -  erros externos, disco cheio, arquivo travado por programas, remoção do pendrive durante a escrita...  -  erro externo
    }
}
