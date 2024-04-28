## Sistema Bancário utilizando threads

Este projeto implementa um sistema bancário em Java com programação multithreading. O sistema simula transações financeiras, garantindo a corretude dos saldos das contas dos objetos.

### Entidades do sistema

#### Bank
- Responsável por intermediar as transações financeiras entre as lojas, clientes e funcionários;
- Garante a consistência dos saldos das contas.

#### Store
- Representa um estabelecimento onde os clientes realizam compras;
- Possui uma conta para receber pagamentos dos clientes;
- Paga os funcionários assim que possuir o valor dos seus salários.

#### Employee
- Trabalha em uma loja e é responsável por receber seu salário e investir parte dele;
- Possui duas contas: uma para receber o salário da loja e outra para investimentos.

#### Customer
- Representa um cliente e possui uma conta bancária.
- Realiza compras em diferentes lojas, alternando entre elas, até que seu saldo esteja vazio.

#### Account
- Representa uma conta bancária associada a um funcionário, cliente ou uma loja.

> O sistema possui um banco, duas lojas, quatro funcionários e cinco clientes.
> Cada cliente é uma thread com uma conta de saldo inicial de R$1000,00; cada funcionário é uma thread.

### Execução

Para executar o programa, basta compilar e rodar o arquivo principal `App.java`. Você pode encontrá-lo [aqui](https://github.com/rmiquilito/ucb-programacao-concorrente-distribuida/blob/main/test-02/src/App.java). Certifique-se de ter Java configurado corretamente.