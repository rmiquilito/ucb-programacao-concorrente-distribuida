import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();
        ArrayList<Store> stores = new ArrayList<Store>();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        // Inicializa os objetos da representação
        for (int i = 0; i < 2; i++) {
            Store store = new Store("store" + (1 + i), bank);
            stores.add(store);
        }

        for (int i = 0; i < 4; i++) {
            Employee employee = new Employee("employee" + (1 + i), stores.get(i % 2), bank);
            employee.setPriority(Thread.MAX_PRIORITY); // Reforça um argumento de prioridade no contexto de tranferência
            employees.add(employee);
        }

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("customer" + (1 + i), bank);
            customer.setPriority(Thread.MIN_PRIORITY); // Faz reforçar um argumento de prioridade de outro agente
            customers.add(customer);
            customer.start();
        }

        for (int i = 0; i < 4; i++) {
            employees.get(i).start();
        }

        // Retém os objetos
        try {
            for (Customer customer : customers) {
                customer.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        try {
            for (Employee employee : employees) {
                employee.join();
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }

        // Imprime no console os registros de transações guardados
        for (Store store : stores) {
            System.out.println("\n\t" + store.getName() + ".account.statement\n");
            System.out.println(store.getAccount().getStatement());
        }
        System.out.println();

        for (Employee employee : employees) {
            System.out.println("\n\t" + employee.getName() + ".salaryAccount.statement\n");
            System.out.println(employee.getSalaryAccount().getStatement());
        }
        System.out.println();

        for (Employee employee : employees) {
            System.out.println("\n\t" + employee.getName() + ".investmentAccount.statement\n");
            System.out.println(employee.getInvestmentAccount().getStatement());
        }
        System.out.println();

        for (Customer customer : customers) {
            System.out.println("\n\t" + customer.getName() + ".account.statement\n");
            System.out.println(customer.getAccount().getStatement());
        }
        System.out.println();

        // Imprime no console o estado final dos balanços
        System.out.println();
        for (Store store : stores) {
            System.out.println(store.getName() + "? " + "R$" + store.getAccount().getBalance());
        }

        System.out.println();
        for (Employee employee : employees) {
            System.out.println(employee.getName() + "? " + "R$" + employee.getSalaryAccount().getBalance());
        }

        System.out.println();
        for (Employee employee : employees) {
            System.out.println(employee.getName() + "? " + "R$" + employee.getInvestmentAccount().getBalance());
        }

        System.out.println();
        for (Customer customer : customers) {
            System.out.println(customer.getName() + "? " + "R$" + customer.getAccount().getBalance());
        }
    }
}
