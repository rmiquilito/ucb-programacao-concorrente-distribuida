public class Employee extends Thread { // É thread
    private Store store; // Guarda loja na qual trabalha
    private Bank bank;
    private Account salaryAccount; // Guarda conta para salário
    private Account investmentAccount; // Guarda conta de investir

    public Employee(String name, Store store, Bank bank) {
        super(name);
        this.store = store;
        this.bank = bank;
        this.salaryAccount = new Account(bank, this, 0.0); // Inicializa conta com 0.0 de saldo
        this.investmentAccount = new Account(bank, this, 0.0); // Inicializa conta com 0.0 de saldo

        bank.addEmployeeAccounts(this.salaryAccount); // Faz guardar conta na lista do banco
        bank.addEmployeeAccounts(this.investmentAccount); // Faz guardar conta na lista do banco
    }

    public Store getStore() {
        return this.store;
    }

    public Account getSalaryAccount() {
        return this.salaryAccount;
    }

    public Account getInvestmentAccount() {
        return this.investmentAccount;
    }

    // Percorre a lista de funcionários da loja na qual trabalha e retorna o colega de trabalho
    public Employee getCoworker() {
        int i = 0;
        for (Employee employee : this.store.getEmployees()) {
            // Faz encontrar o índice do funcionário que não ele
            if (employee != this) {
                i = this.store.getEmployees().indexOf(employee);
            }
        }

        return this.store.getEmployees().get(i);
    }

    // Sobrescreve o método de execução da thread e o adequa à regra de negócio
    public void run() {
        while (!this.store.communicate() || this.store.funds()) { // Executa o método interminavelmente enquanto a loja não retornar com as sinalizações esclarecidas em ./Store
            Boolean lateWithdraw = this.salaryAccount.getStatement().size() < this.getCoworker().getSalaryAccount()
                    .getStatement().size(); // Calcula se o colega de trabalho possui um recebimento de salário a mais
            Boolean notLateWithdraw = this.salaryAccount.getStatement().size() == this.getCoworker().getSalaryAccount()
                    .getStatement().size(); // Calcula se o colega de trabalho recebeu a mesma quantidade de salários

            // Se recebeu o colega de trabalho a mais, verifica se há fundos para receber enfim
            if (lateWithdraw) {
                if (this.store.getAccount().getBalance() >= 700.0) {
                    this.salaryAccount.withdraw(this.store.getAccount(), 700.0); // Na representação, faz o saque de um salário
                    this.salaryAccount.deposit(700.0 * 0.2, this.getInvestmentAccount()); // Na representação, faz um investimento
                }

            }

            // Se os funcionários receberam a mesma quantidade, verifica se há fundos para receber de forma equidistante
            if (notLateWithdraw) {
                if (this.store.getAccount().getBalance() >= 1400.0) { // Se houver o suficiente para honrar com todos os funcionários, segue
                    this.salaryAccount.withdraw(this.store.getAccount(), 700.0);
                    this.salaryAccount.deposit(700.0 * 0.2, this.getInvestmentAccount());
                } else if (this.store.communicate()) { // Se não houver, verifica se a loja sinalizou: se sim, não hão de receber mais; se não, retorna em outro momento
                    return;
                }
            }
        }
    }
}
