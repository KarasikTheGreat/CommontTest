package Client;


import Server.Goods;
import Server.Materials;
import Server.Order;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final int PORT = 5555;
    private static final String hostname = "localhost";
    private static int activeCustomers = 0;
    private static final Object lockActiveCustomers = new Object();
    private static boolean atLeastOneCustomerAlive = true;

    public static void main(String[] args) {
        if (args.length != 5) {
            System.out.println("Incorrect arguments. Please enter amount of costumers, starting balance," +
                    " maximum amount of declines, amount of suppliers, frequency of requests");
            System.exit(1);
        }
        int amountOfCostumers = 0;
        int startBalance = 0;
        int maxDeclines = 0;
        int amountOfSuppliers = 0;
        int frequencyOfRequests = 0;
        try {
            amountOfCostumers = Integer.parseInt(args[0]);
            startBalance = Integer.parseInt(args[1]);
            maxDeclines = Integer.parseInt(args[2]);
            amountOfSuppliers = Integer.parseInt(args[3]);
            frequencyOfRequests = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input");
            System.exit(1);
        }
        for (int i = 0; i < amountOfCostumers; i++) {

            createAndProcessCustomer(startBalance, maxDeclines);
        }
        for (int i = 0; i < amountOfSuppliers; i++) {
            createAndProcessSupplier(frequencyOfRequests);
        }
        while (atLeastOneCustomerAlive) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {

            }
        }
        ConnectionProtocol connection = new ConnectionProtocol(hostname, PORT);

        int money = connection.getMoney();
        Iterable<Goods> goodsList = connection.getGoodsList();
        Iterable<Order> orderHistory = connection.getOrderistory();
        ConcurrentHashMap<Materials, AtomicInteger> materialsLeft = connection.getMaterialsOnStorage();
        connection.closeConnection();

        StatsReport reporter = new StatsReport();
        reporter.printOrdersByClientsWithGrandTotal(startBalance, orderHistory);
        reporter.printOrdersByGoods(orderHistory, goodsList);
        reporter.materialsLeftInStorage(materialsLeft);



        System.out.println("Remainder of firm balance is " + money);


    }

    public static void createAndProcessCustomer(int startBalance, int maxDeclines) {
        new Thread(() -> {
            synchronized (lockActiveCustomers) {
                activeCustomers++;
            }
            ConnectionProtocol connection = new ConnectionProtocol(hostname, PORT);
            int id = connection.getClientsID();
            Iterable<Goods> goodsList = connection.getGoodsList();
            Customer customer = new Customer(startBalance, id, maxDeclines, goodsList);
            while (customer.isStillAlive()) {
                Order order = customer.createOrder();
                if (customer.ableToPayOrder(order)) {
                    if (connection.handleOrder(order)) {
                        customer.payOrder(order);
                    } else customer.orderDeclined();
                } else customer.orderDeclined();
            }
            connection.closeConnection();
            synchronized (lockActiveCustomers) {
                activeCustomers--;
                if (activeCustomers <= 0) {
                    atLeastOneCustomerAlive = false;
                }
            }

        }).start();

    }

    public static void createAndProcessSupplier(int frequencyOfRequests) {
        new Thread(() -> {
            ConnectionProtocol connection = new ConnectionProtocol(hostname, PORT);
            ConcurrentHashMap<Materials, AtomicInteger> materials = connection.getMaterialsOnStorage();
            Supplier supplier = new Supplier(materials);
            while (atLeastOneCustomerAlive) {
                Pair<Materials, Integer> supply = supplier.createSupply();
                connection.sellMaterial(supply);
                try {
                    Thread.sleep(frequencyOfRequests);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            connection.closeConnection();
        }).start();
    }

}