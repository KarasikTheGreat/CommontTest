package Server;

import java.util.ArrayList;
import java.util.List;

public class GoodsCreator {
    private Storage storage;
    private GoodsList goodsList;

    public GoodsCreator() {
        Materials transistor = new Materials("Transistor", 900);
        Materials chip = new Materials("Chip", 1500);
        Materials graphicCore = new Materials("Graphic core", 3000);
        Materials fan = new Materials("Fan", 650);
        Materials plate = new Materials("Plate", 420);

        storage = new Storage();
        storage.addNewMaterial(transistor, 100);
        storage.addNewMaterial(chip, 100);
        storage.addNewMaterial(graphicCore, 100);
        storage.addNewMaterial(fan, 100);
        storage.addNewMaterial(plate, 100);

        List<MaterialsWithCounter> proccesorRecipe = new ArrayList<>();
        proccesorRecipe.add(new MaterialsWithCounter(transistor, 3));
        proccesorRecipe.add(new MaterialsWithCounter(chip, 3));
        proccesorRecipe.add(new MaterialsWithCounter(plate,1));

        List<MaterialsWithCounter> videocardRecipe = new ArrayList<>();
        videocardRecipe.add(new MaterialsWithCounter(graphicCore, 2));
        videocardRecipe.add(new MaterialsWithCounter(fan, 2));
        videocardRecipe.add(new MaterialsWithCounter(transistor, 2));

        Goods processor = new Goods("Processor", proccesorRecipe);
        Goods videoCard = new Goods("Videocard", videocardRecipe);

        goodsList = new GoodsList();
        goodsList.addGoods(processor);
        goodsList.addGoods(videoCard);


    }

    public Storage getStorage() {
        return storage;
    }

    public GoodsList getGoodsList() {
        return goodsList;
    }
}
