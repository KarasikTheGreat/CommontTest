import Server.Goods;
import Server.Order;

import java.util.List;
import java.util.Random;

public class Customer {
    private int customersBalance;
    private int customerID;
    private int countOfDeclines;
    private int maxCountOfDeclines;
    private boolean stillAlive;
    private List<Goods> goodsList;

    public Customer(int customersBalance, int customerID,  int maxCountOfDeclines, List<Goods> goodsList) {
        this.customersBalance = customersBalance;
        this.customerID = customerID;
        countOfDeclines = 0;
        this.maxCountOfDeclines = maxCountOfDeclines;
        stillAlive = true;
        this.goodsList = goodsList;
        if (goodsList.size()== 0) {
            stillAlive = false;
        }
    }
    public Order createOrder() {
        Random rnd = new Random();
        int numberOfProduct = rnd.nextInt(goodsList.size());
        int i = 0;
        Goods goods = goodsList.iterator().next();
        for (Goods g : goodsList) {
            goods = g;
            if (i == numberOfProduct)
                break;
            i++;
        }
        return new Order(customerID, goods, rnd.nextInt(2) + 1);
    }
    public boolean ableToPayOrder(Order order) {
        return customersBalance > order.getPrice();
    }
    public void payOrder(Order order) {
        customersBalance -= order.getPrice();
        if (customersBalance == 0) {
            stillAlive = false;
        }
    }
    public void orderDeclined() {
        countOfDeclines++;
        if (countOfDeclines >= maxCountOfDeclines) {
            stillAlive = false;

        }
    }
    public boolean isStillAlive() {
        return stillAlive;
    }
}
