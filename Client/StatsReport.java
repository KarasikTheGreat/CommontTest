package Client;

import Server.Goods;
import Server.Materials;
import Server.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

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
        String result = "\nOrders by clients:\nID  |  Order sum  |  Money left\n";
        for (Map.Entry<Integer, Integer> entry: report.entrySet()) {
            result += String.format("%-4d",entry.getKey()) + "|  "
                    + String.format("%-11d", entry.getValue()) + "|  "
                    + String.format("%-10d", (startBalance - entry.getValue())) + "\n";
        }
        System.out.println(result);
    }
    public static void printOrdersByGoods(Iterable<Order> history, Iterable<Goods> goodsList) {
        String result = "\nOrders by goods:\nName of Product  |Orders amount  |Sells amount  |  Profit\n";
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
    public static void materialsLeftInStorage(ConcurrentHashMap<Materials, AtomicInteger> materialsLeft) {
        String result = "Materials left in storage:\nName           | Amount  |  Summary price\n";
        for (ConcurrentHashMap.Entry<Materials,AtomicInteger> material : materialsLeft.entrySet()) {
            Materials materials = material.getKey();
            int currentMaterialPrice = material.getValue().intValue() * materials.getPrice();
            result += String.format("%-15s", material.getKey()) + "|  "
                    + String.format("%-7d", material.getValue().intValue()) + "|  "
                    + String.format("%-7d", currentMaterialPrice) + "\n";
        }
        System.out.println(result);
    }
}
