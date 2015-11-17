package Client;

import Server.Goods;
import Server.MaterialsWithCounter;
import Server.Order;

import java.util.HashMap;
import java.util.Map;

public class StatsReport {

    public static void printOrdersByClientsWithGrandTotal(int startBalance, Iterable<Order> orderHistory) {
        Map<Integer, Integer> report = new HashMap<>();
        for (Order order: orderHistory) {
            if (report.containsKey(order.getClientsID())){
                int newValue = report.get(order.getClientsID()) + order.getPrice();
                report.replace(order.getClientsID(), newValue);
            } else {
                report.put(order.getClientsID(), order.getPrice());
            }
        }
        String result = "\nOrders By Clients:\nID  |  Order sum  |  Money left\n";
        for (Map.Entry<Integer, Integer> entry: report.entrySet()) {
            result += String.format("%-4d",entry.getKey()) + "|  "
                    + String.format("%-11d", entry.getValue()) + "|  "
                    + String.format("%-10d", (startBalance - entry.getValue())) + "\n";
        }
        System.out.println(result);
    }
    public static void printOrdersByGoods(Iterable<Order> history, Iterable<Goods> goodsList) {
        String result = "\nOrders By Goods:\nName of Product  |  Order count  |  Sell count  |  Money from sells\n";
        for (Goods goods: goodsList) {
            int orderCount = 0;
            int sellCount = 0;
            int money = 0;
            for (Order order: history) {
                if (goods.equals(order.getGoods())) {
                    orderCount++;
                    sellCount += order.getAmountOfGoods();
                    money += order.getPrice();
                }
            }
            result += String.format("%-17s", goods.getName()) + "|  "
                    + String.format("%-13d",orderCount) + "|  "
                    + String.format("%-12d",sellCount) + "|  "
                    + String.format("%-12d",money) + "\n";
        }
        System.out.println(result);
    }
    public static void printMaterialsOnStorage(Iterable<MaterialsWithCounter> storage) {
        String result = "Materials on Storage:\nName           |  Count  |  Price\n";
        for (MaterialsWithCounter materialWithCount: storage) {
            result += String.format("%-15s", materialWithCount.getMaterial().getName()) + "|  "
                    + String.format("%-7d", materialWithCount.getAmount()) + "|  "
                    + String.format("%-7d", materialWithCount.getPrice()) + "\n";
        }
        System.out.println(result);
    }
}
