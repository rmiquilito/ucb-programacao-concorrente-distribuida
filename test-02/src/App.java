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
    }
}
