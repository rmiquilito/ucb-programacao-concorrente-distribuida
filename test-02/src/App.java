import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        Bank bank = new Bank();
        ArrayList<Store> stores = new ArrayList<Store>();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        ArrayList<Customer> customers = new ArrayList<Customer>();

        for (int i = 0; i < 2; i++) {
            Store store = new Store("store" + (1 + i), bank);
            stores.add(store);

            for (int j = 0; j < 2; j++) {
                Employee employee = new Employee("employee" + ((1 + i) + j), store, bank);
                employees.add(employee);
                employee.setPriority(Thread.MAX_PRIORITY);
            }
        }

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("customer" + (1 + i), bank);
            customers.add(customer);
            customer.setPriority(Thread.MIN_PRIORITY);
            customer.start();
        }

        for (int i = 0; i < (2 * 2); i++) {
            employees.get(i).start();
        }

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
