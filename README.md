<h1 align="center">PROJETO SISTEMA DE DELIVERY - POB</h1>

`"Runner"` - Aplicação que simula um delivery. 

## Ideias
+ Temas: livros, comida, entregas no geral

## Requisitos
+ `Java 8`
+ `VS Code` ou `Eclipse` ou `IntelliJ` 

## Instalação
1. Instale `Java 8` na sua máquina (caso não tenha).
2. Baixe ou clone o projeto para o seu computador.
3. Abra o terminal ou prompt de comando e navegue até o diretório do projeto.
4. Inicie o programa executando o arquivo `TelaPrincipal`.
5. Também é possível utilizá-lo como executável.

## Uso
>  Projeto desenvolvido na disciplina de Persistência de Objetos 2024.2, utilizando a linguagem java, ministrada pelo professor Fausto Veras, no curso de Sistemas para Internet (IFPB).


## Exigências
+ `Swing:` Telas para a aplicação ser executada.
+ `Poo:` Lógica do delivery e estruturação de arquivos.
+ `Pob:` Integração do DAO com JPA e relacionamentos entre classes.

## Funcionamento
1. Objetivo: Praticar os conceitos de persistência transparente de objetos, através do desenvolvimento de um sistema de informação, usando arquitetura em camadas, banco de objetos db4o e padrão DAO.

2. Tarefas:
- 2.1 Parte 1:
   1. (1pt) Implementar as classes de negócio para o tema fornecido. Configurar as operações de cascata na classe Util.
   2. (1pt) Implementar as classes DAO específicas, uma para cada classe de negócio, incluindo as 3 consultas do tema escolhido.
   3. (1pt) Implementar a classe Fachada com os requisitos do sistema e as regras de negócio
   4. (1pt) Implementar as aplicações console:
      1. o Cadastrar.java - cadastrar vários objetos como massa de teste
      2. o Listar.java – listar todos os objetos cadastrados
      3. o Consultar.java – executar as 3 consultas pedidas
   5. (1pt) Implementar as aplicações gráficas – uma tela para cada classe de negócio e uma tela para as 3 consultas.
   6. (5pt) Apresentar o projeto, a apresentação é obrigatória para cada aluno ter nota
- 2.2 Parte 2:
    1. (10pt) Telas
    2. (10pt) Classes DAOs
    3. (20pt) Classe Fachada
    4. (30pt) Apresentação (final) do projeto

3. Requisitos:
   1. Classes:
       1. Pedido(id, data, valor, descrição)
       2. Entrega(id, data, endereço, entregador, pedido)
       3. Entregador(id, nome, lista de entrega)
   
   2. Regras de Negócio:
       1. Um entregador não pode ter mais de 5 entregas por dia
       2. A entrega deve ser feita apenas por um entregador
       3. Uma entrega não pode ter mais de um endereço
       4. Um pedido não pode ter valor igual ou inferior a zero 
       5. As datas de pedido e entrega são obtidas pelo sistema e seguem o formato (dd/mm/aaaa)
       6. O entregador não pode fazer entrega de um pedido que esteja fora da sua lista
       7. Apenas entregadores disponíveis podem ser atribuídos a novas entregas.
       
   3. Consultas:
       1. Quais as entregas na data X 
       2. Quais as entregas com data diferente da data do pedido 
       3. Quais os entregadores que tem mais N entregas

4. Implementação
    1. Utilizar a classe DAO<T> genérica fornecida em sala de aula
    2. Configurar todos os relacionamentos com o tipo de carregamento (fetch) = FetchType.LAZY
    3. Implementar telas:
        - As telas das classes do modelo de negócio devem contemplar listagem de objetos (incluindo relacionamentos) e a criação, atualização e exclusão de objetos. Uma dessas telas deve exibir uma foto
        - A tela de consulta deve exibir as 3 consultas do tema
    4. Todas as consultas devem ser implementadas dentro das classes DAO, usando a linguagem JPQL
    5. Renomear as tabelas acrescentando a matrícula do aluno, ex: @Table(name="produto") para @Table(name="produto20201351234")
    6. Utilizar banco de dados com nome "pob" no PostgreSQL ou Mysql
    7. Usar Hibernate versão 6.6.5
   
## Descrição do arquivo
| Nome | Descrição |
| ---- | --------- |
| modelo | Contém as classes principais que representam as entidades do sistema, como Pedido, Entrega e Entregador. Essas classes encapsulam os atributos e comportamentos básicos das entidades. |
| appswing | mplementa a interface gráfica (GUI) utilizando o framework Swing, permitindo que o usuário interaja com o sistema por meio de janelas e elementos visuais. |
| appconsole | Oferece uma interface de linha de comando (CLI) para interação com o sistema, ideal para testes ou usuários que preferem o terminal. |
| daojpa | Gerencia a persistência de dados utilizando o banco de dados orientado a objetos JPA, incluindo operações de salvar, buscar e atualizar objetos. |
| regras_negocio | Contém a lógica do sistema, implementando as validações e as regras de negócio para assegurar que as ações sejam executadas corretamente. |

### Dev
- [Ananda Guedes](https://github.com/agu3des)
- [Maira Larissa](https://github.com/maira-larissa)
