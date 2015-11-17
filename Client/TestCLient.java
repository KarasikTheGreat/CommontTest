package Client;

import Server.Goods;
import Server.MaterialsWithCounter;
import Server.Order;

public class TestCLient {
    private static final int PORT = 5555;
    private static final String hostname = "localhost";

    public static void main(String[] args) {
        ConnectionProtocol connectionProtocol = new ConnectionProtocol(hostname, PORT);
        int ID = connectionProtocol.getClientsID();
        Customer customer = new Customer(15000, ID, 5, connectionProtocol.getGoodsList());

        while (customer.isStillAlive()) {
            Order order = customer.createOrder();

            if (customer.ableToPayOrder(order)) {

                if (connectionProtocol.handleOrder(order)) {

                    customer.payOrder(order);
                } else customer.orderDeclined();

            } else {

                customer.orderDeclined();
            }

        }
        int money = connectionProtocol.getMoney();
        System.out.println(money);
        StatsReport report = new StatsReport();
        Iterable<Goods> goodsList = connectionProtocol.getGoodsList();

        Iterable<Order> orderHistory = connectionProtocol.getOrderistory();
        Iterable<MaterialsWithCounter> materialsLeft = connectionProtocol.getMaterialsOnStorage();
        connectionProtocol.closeConnection();
        report.printOrdersByGoods(orderHistory, goodsList);
        report.printOrdersByClientsWithGrandTotal(15000, orderHistory);
        report.printMaterialsOnStorage(materialsLeft);

    }
}
