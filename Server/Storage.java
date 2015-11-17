package Server;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Storage {
    private ConcurrentMap<Materials, AtomicInteger> numberOfMaterials;
    private Object blockStorage = new Object();

    Storage() {
        numberOfMaterials = new ConcurrentHashMap<Materials, AtomicInteger>();

    }

    public void addNewMaterial(Materials material, int amount) {
        numberOfMaterials.put(material, new AtomicInteger(amount));
    }


    public void addMaterialsToStorage(MaterialsWithCounter materialWithCounter) {
        Materials material = materialWithCounter.getMaterial();
        int amount = materialWithCounter.getAmount();
        AtomicInteger prev = numberOfMaterials.putIfAbsent(material, new AtomicInteger(amount));
        if (prev != null) {
            prev.addAndGet(amount);
        }

    }


    public Iterable<MaterialsWithCounter> getMaterialsOnStorage() {
        return numberOfMaterials.entrySet().stream().map(entry -> new MaterialsWithCounter(entry.getKey(), entry.getValue().intValue())).collect(Collectors.toList());
    }


    public boolean produceGoods(Goods goods, int amount) {
        Iterable<MaterialsWithCounter> requiredMaterials = goods.getRequiredMaterials();

        {
            for (MaterialsWithCounter materialsWithCounter : requiredMaterials) {
                Materials currentMaterial = materialsWithCounter.getMaterial();

                if (numberOfMaterials.containsKey(currentMaterial)) {

                    if (numberOfMaterials.get(currentMaterial).intValue() < materialsWithCounter.getAmount() * amount) {
                        return false;
                    }

                }
            }
        }


        for (MaterialsWithCounter materials : requiredMaterials) {
            Materials currentMaterial = materials.getMaterial();
            int newAmountOnMaterials = numberOfMaterials.get(currentMaterial).intValue() - materials.getAmount() * amount;
            numberOfMaterials.put(currentMaterial, new AtomicInteger(newAmountOnMaterials));
        }
        return true;

    }


}
