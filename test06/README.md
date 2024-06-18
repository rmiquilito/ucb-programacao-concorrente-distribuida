# Servidor de Controle de Livros de Biblioteca com Sockets

Este projeto em Java implementa um servidor para controle de um registro de livros de uma biblioteca, utilizando comunicação por sockets entre o servidor e um cliente que atua como uma bibliotecária.

## Funcionalidades Implementadas

- **Listagem de Livros:** Permite à bibliotecária solicitar a listagem de todos os livros disponíveis na biblioteca.
  
- **Aluguel de Livros:** Permite à bibliotecária registrar o aluguel de um livro específico, reduzindo o número de exemplares disponíveis.
  
- **Cadastro de Livros:** Permite à bibliotecária cadastrar novos livros na biblioteca, atualizando o arquivo JSON que serve como "base de dados" da biblioteca.

## Estrutura do Projeto

O projeto é estruturado da seguinte forma:

- **Server.java:** Implementa o servidor que aguarda conexões da bibliotecária e executa as operações solicitadas.
  
- **Library.java:** Classe responsável por gerenciar os livros da biblioteca, incluindo operações de listagem, aluguel e cadastro.
  
- **Book.java:** Classe que define a estrutura de dados de um livro, com os atributos autor, nome, gênero e número de exemplares.
  
- **Librarian.java:** Implementa o cliente que atua como bibliotecária, se conectando ao servidor por meio de sockets e enviando solicitações para as operações mencionadas.

- **books.json:** Arquivo JSON que contém inicialmente 10 livros, representando a "base de dados" da biblioteca.

## Requisitos e Observações

- A comunicação entre servidor e bibliotecária (cliente) é realizada via sockets, seguindo um protocolo definido para cada operação.
  
- Alterações realizadas pela bibliotecária (como cadastro de novos livros ou registro de aluguel de livros) devem ser refletidas no arquivo JSON que armazena os livros da biblioteca.

## Como Executar

Para executar o projeto:

1. Clone o repositório para sua máquina local.
2. Abra o projeto em sua IDE preferida compatível com Java 17.
3. Compile e execute o `Server.java` para iniciar o servidor.
4. Compile e execute o `Librarian.java` para iniciar o cliente (bibliotecária) e interagir com o servidor.

Certifique-se de ter configurado corretamente as dependências e o ambiente de desenvolvimento para Java 17 antes de executar o projeto.