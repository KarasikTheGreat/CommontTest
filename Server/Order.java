package Server;

import java.io.Serializable;

public class Order implements Serializable {

    private int clientsID;
    private int amountOfGoods;
    private  Goods goods;


    public Order(int clientsID, Goods goods,int amountOfProducts ) {
        this.clientsID = clientsID;
        this.goods = goods;
        this.amountOfGoods = amountOfProducts;

    }

    public Goods getGoods() {
        return goods;
    }

    public int getClientsID() {
        return clientsID;
    }

    public int getAmountOfGoods() {
        return amountOfGoods;
    }

    public int getPrice() {
        return goods.getPrice() * amountOfGoods;
    }




}
