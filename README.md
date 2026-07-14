# Gastos Tracker

Aplicação de linha de comando em Java puro para controle de despesas pessoais.
Construída sem frameworks, com o objetivo de consolidar fundamentos da linguagem:
modelagem de domínio, coleções, tratamento de exceções, persistência em arquivo e Stream API.

## Como rodar

```bash
git clone git@github.com:Lucaseduardo1204/tracker.git
cd gastos-tracker
mvn compile
mvn exec:java -Dexec.mainClass="com.lucas.gastos.Main"
```

Requer Java 21+. Os dados são persistidos em `gastos.csv`, criado automaticamente na
primeira execução.

## Funcionalidades

| Opção | Descrição |
|---|---|
| 1 | Cadastrar despesa (valor, data, categoria, descrição, forma de pagamento, banco) |
| 2 | Listar todas as despesas |
| 3 | Excluir despesa por número |
| 4 | Relatório: total por categoria |
| 5 | Relatório: despesa de maior valor |
| 6 | Sair |

## Decisões de design

**BigDecimal para valores monetários.** `double` usa ponto flutuante binário e não
representa 0.1 exatamente — `0.1 + 0.2` resulta em `0.30000000000000004`. `BigDecimal`
construído a partir de String preserva os dígitos decimais exatos. O relatório soma
`0.10 + 0.20` e retorna `0.30`, não `0.30000000000000004`.

**Enums para categoria, forma de pagamento e banco.** São conjuntos fechados e conhecidos
em tempo de compilação. Garantem que só valores válidos existam no sistema — impossível
cadastrar "Comdia" por erro de digitação. O custo é que adicionar um valor novo exige
recompilar; em um sistema multiusuário, "banco" provavelmente viraria uma tabela.

**Validação no construtor.** A `Despesa` se recusa a nascer inválida: valor nulo ou
negativo, data nula ou futura, e descrição contendo `;` lançam `IllegalArgumentException`.
Não existe despesa inválida em memória — o objeto protege as próprias regras.

**Cópia defensiva no `listar()`.** O método retorna `new ArrayList<>(despesas)`, não a
lista interna. Quem chama não consegue alterar o estado do gerenciador por fora.

**Tolerância a arquivo corrompido.** O carregamento usa duas camadas de defesa:
- *Guard clause* para problemas estruturais (linha vazia, número de campos diferente de 6) — a linha é ignorada com aviso
- *Multi-catch* (`IllegalArgumentException | DateTimeParseException`) para problemas de
  conteúdo (valor não numérico, enum inexistente, data mal formatada)

O `try-catch` fica **dentro** do loop de leitura: uma linha corrompida é descartada
individualmente, sem abortar o carregamento das demais. Arquivo inexistente é tratado
como ausência de histórico, não como erro.

**Separação de responsabilidades.** O serviço detecta e sinaliza (`throw`); a camada de
interface captura e traduz para o usuário. Relatório com lista vazia lança
`IllegalStateException`, tratada no menu com mensagem amigável — sem derrubar a aplicação.

## Estrutura

com.lucas.gastos
├── Main.java                    interface de console
├── model/entities/Despesa.java  domínio (imutável, autovalidado)
├── model/enums/                 CategoriaEnum, FormaPagamentoEnum, BancoEnum
├── services/GerenciadorDespesas relatórios e regras de negócio
└── repository/DespesaRepository persistência em CSV

## Melhorias futuras

- Relatórios: total por forma de pagamento, total por banco, filtro por período
- Total geral de gastos
- Migração da persistência para banco relacional
