package Server;

import java.util.ArrayList;
import java.util.List;

public class GoodsList {
    private List<Goods> goodsList;

    public GoodsList() {
        goodsList = new ArrayList<>();

    }


    public void addGoods(Goods goods) {
        goodsList.add(goods);
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }
}

