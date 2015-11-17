package Server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderHistory implements Serializable {
    private List<Order> ordersList;

    public OrderHistory() {
        ordersList = new ArrayList<>();
    }

    public void addOrder(Order order) {
        ordersList.add(order);
    }

    public Iterable<Order> getOrderHistory() {
        return ordersList;
    }



}
