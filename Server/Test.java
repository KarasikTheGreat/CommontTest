//package Server;
//
//import Client.Customer;
//import Client.Supplier;
//
//public class Test {
//    public static void main(String[] args) {
//        Firm firm = new Firm(0);
//        System.out.println(firm.getBalance());
//        Customer customer = new Customer(10000000, 0, 5, firm.getGoodsList());
//        Order order = customer.createOrder();
//        firm.handleOrder(order);
//        System.out.println(order.getGoods() + ":" + order.getAmountOfGoods());
//        System.out.println(firm.getMaterialsOnStorage());
//        System.out.println(firm.getBalance());
//
//        System.out.println(customer.isStillAlive());
//        Supplier supplier = new Supplier(firm.getMaterialsOnStorage());
//        MaterialsWithCounter supply = supplier.createSupply();
//        String supp = supply.getMaterial() + ": " + supply.getAmount();
//        System.out.println(supp);
//        firm.buyMaterial(supply);
//        System.out.println(firm.getMaterialsOnStorage());
//        System.out.println(firm.getBalance());
//    }
//}
