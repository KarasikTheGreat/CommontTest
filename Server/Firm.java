package Server;

public class Firm {


    private Integer balance;
    private Storage storage;
    private GoodsList goodsList;
    private static final Object lockBalance = new Object();
    private OrderHistory orderHistory;
    private static final Object lockOrderHistory = new Object();

    Firm(int balance) {
        this.balance = balance;
        GoodsCreator goodsCreator = new GoodsCreator();
        storage = goodsCreator.getStorage();
        goodsList = goodsCreator.getGoodsList();
        orderHistory = new OrderHistory();

    }


    public  int getBalance() {
        synchronized (lockBalance) {
            return balance;
        }

    }

    public Iterable<Goods> getGoodsList() {
        return goodsList.getGoodsList();
    }

    public Iterable<MaterialsWithCounter> getMaterialsOnStorage() {
        return storage.getMaterialsOnStorage();

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


    public boolean buyMaterial(MaterialsWithCounter materials) {
        synchronized (lockBalance) {
            if (balance < materials.getPrice()) return false;
            balance -= materials.getPrice();
            storage.addMaterialsToStorage(materials);
        }
        return true;
    }
}
