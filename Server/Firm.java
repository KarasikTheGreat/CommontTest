package Server;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Firm {


    private Integer balance;
    private Storage storage;
    private GoodsList goodsList;
    private static final Object lockBalance = new Object();
    private OrderHistory orderHistory;
    private static final Object lockOrderHistory = new Object();

    public Firm(int balance) {
        this.balance = balance;
        GoodsCreator goodsCreator = new GoodsCreator();
        storage = goodsCreator.getStorage();
        goodsList = goodsCreator.getGoodsList();
        orderHistory = new OrderHistory();

    }


    public int getBalance() {
        synchronized (lockBalance) {
            return balance;
        }

    }

    public Iterable<Goods> getGoodsList() {
        return goodsList.getGoodsList();
    }

    public ConcurrentMap<Materials, AtomicInteger> getMaterialsOnStorage() {
        return storage.getNumberOfMaterials();

    }

    public Iterable<Order> getOrderHistory() {
        return orderHistory.getOrderHistory();
    }

    public boolean handleOrder(Order order) {
        boolean goodsProduced = storage.produceGoods(order.getGoods(), order.getAmountOfGoods());
        if (goodsProduced) {
            synchronized (lockBalance) {
                balance += order.getPrice();
            }
            synchronized (lockOrderHistory) {
                orderHistory.addOrder(order);
            }

        }
        return goodsProduced;
    }


    public boolean buyMaterial(Materials material, int amount) {
        synchronized (lockBalance) {
            if (balance < material.getPrice() * amount) return false;
            balance -= material.getPrice()*amount;
            storage.addMaterialsToStorage(material,amount);
        }
        return true;
    }
}
