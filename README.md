# election-java-rmi-guilhermegoa

***

### Aluno

__Guilherme Oliveira Antonio__

### Professor

__Hugo Bastos De Paula__
***

## Descrição

Este trabalho é baseado numa proposta do livro do Coulouris, Dollimore, Kinberg, Blair (2013), pag. 227.

Considere uma interface Election que fornece dois métodos remotos:

- **vote(String eleitor, String candidato)**
    - String eleitor: código hash MD5 gerado a partir do nome completo do eleitor.
    - String candidato: String de 3 caracteres numéricos que identificam um candidato.
- **result(String candidato)**: este método possui dois parâmetros com os quais o servidor recebe o número de um
  candidato e retorna para o cliente o número de votos desse candidato.
- Os identificadores de eleitor devem ser gerados a partir de uma função MD5 do nome completo do eleitor.
- O sistema deve carregar a lista de candidatos a partir do arquivo senadores.csvPré-visualizar o documento  
  Desenvolva um sistema para o serviço Election utilizando o Java RMI, que garanta que seus registros permaneçam
  consistentes quando ele é acessado simultaneamente por vários clientes.

O serviço Election deve garantir que todos os votos sejam armazenados com segurança, mesmo quando o processo servidor
falha.

Considerando que o Java RMI possui semântica at-most-once, implemente um mecanismo de recuperação de falha no cliente
que consiga obter uma semântica exactly-once para o caso do serviço ser interrompido por um tempo inferior a 30
segundos.
***

## Classes

**IElection**: Interface que extende Remote do java rmi, onde tem os métodos que serão implemetados.

**Senator**: Classe que guarda os dados de um Senador.

**File**: Classe que cuida dos metodos que mexem com arquivo.

**ElectionServant**: Classe que implemeta a interface IElection e gurarda a cache dos dados da eleição.

**Server**: Classe que inicia o servidor, instancioando ElectionServant e o coloca no rmiregistry.

**Client**: Classe na qual executa as ações do cliente.

***

## Compilar (no terminal dentro da pasta src)

- Para compilar o projeto, use os comandos:

> javac Server.java
>
> javac Client.java

***

## Iniciar

- Para iniciar o rmiregistry

> **Linux**: rmiregistry &
>
>**Windowns**: start rmiregistry

- Para iniciar o server e o cliente, use os comandos:

> java Server
>
> java Client

***

### Comandos (é necessario digitar um número seguido enter)

- 0 - Para sair.

- 1 - Votar (nomes repetidos nao votam)
    - Digite o nome da pessoa que está votando.
    - Digite o numero do candidato.

- 2 - Ver resultado do candidato
    - Digite o numero do candidato



