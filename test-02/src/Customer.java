import java.util.Random;

public class Customer extends Thread { // É thread
    private Bank bank;
    private Account account;
    private int targetStore; // Guarda índice da loja da próxima compra

    public Customer(String name, Bank bank) {
        super(name);
        this.bank = bank;
        this.account = new Account(bank, this, 1000.0); // Inicializa conta com 1000.0 de saldo
        this.targetStore = new Random().nextInt(2); // Define um valor inicial aleatório de índice

        bank.addCustomerAccounts(this.account); // Faz guardar conta na lista do banco
    }

    public Account getAccount() {
        return this.account;
    }

    // De acordo com a regra de negócio, define aleatoriamente um valor e o retorna
    public Double decideCost() {
        Double value = ((1 + new Random().nextInt(2)) * 100.0);
        if (this.account.getBalance() == 100.0) { // Impede débito
            value = 100.0;
        }

        return value;
    }

    // De acordo com a regra de negócio, faz alternar o índice da loja da próxima compra e retorna a loja
    public Store chooseStore() {
        Store store = (Store) this.bank.getStoreAccounts().get(this.targetStore).getHolder();
        this.targetStore = (this.targetStore + 1) % 2;

        return store;
    }

    // Sobrescreve o método de execução da thread e o adequa à regra de negócio
    public void run() {
        while (!this.account.scan()) { // Executa o método interminavelmente enquannto a varredura não retornar com a sinalização esclarecida em ./Account
            Store store = chooseStore();
            Double cost = decideCost();

            this.account.deposit(cost, store.getAccount()); // Na representação, faz uma compra
        }
    }
}
