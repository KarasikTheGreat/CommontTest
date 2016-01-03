package Server;

import java.util.HashMap;

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
        storage.addMaterialsToStorage(transistor, 200);
        storage.addMaterialsToStorage(chip, 200);
        storage.addMaterialsToStorage(graphicCore, 200);
        storage.addMaterialsToStorage(fan, 200);
        storage.addMaterialsToStorage(plate, 200);

        HashMap<Materials, Integer> processorRecipe = new HashMap<>();
        processorRecipe.put(transistor, 1);
        processorRecipe.put(chip, 2);
        processorRecipe.put(plate, 2);


        HashMap<Materials, Integer> videocardRecipe = new HashMap<>();
        videocardRecipe.put(graphicCore, 2);
        videocardRecipe.put(fan, 2);
        videocardRecipe.put(transistor, 1);


        Goods processor = new Goods("Processor", processorRecipe);
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
