package Server;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Storage {
    private ConcurrentMap<Materials, AtomicInteger> numberOfMaterials;
    private Object blockStorage = new Object();

    Storage() {
        numberOfMaterials = new ConcurrentHashMap<Materials, AtomicInteger>();

    }
    public void addMaterialsToStorage(Materials material, int amount) {

        AtomicInteger prev = numberOfMaterials.putIfAbsent(material, new AtomicInteger(amount));
        if (prev != null) {
            prev.addAndGet(amount);
        }

    }
    public ConcurrentMap<Materials, AtomicInteger> getNumberOfMaterials() {
        return numberOfMaterials;
    }

    public boolean produceGoods(Goods goods, int amount) {
        Map<Materials, Integer> requiredMaterials = goods.getRequiredMaterials();
        synchronized (blockStorage) {
            for (HashMap.Entry<Materials, Integer> materialsWithCounter : requiredMaterials.entrySet()) {
                Materials currentMaterial = materialsWithCounter.getKey();
                if (numberOfMaterials.containsKey(currentMaterial)) {
                    if (numberOfMaterials.get(currentMaterial).intValue() < materialsWithCounter.getValue() * amount) {
                        return false;
                    }
                }
            }
            for (HashMap.Entry<Materials, Integer> materialsWithCounter : requiredMaterials.entrySet()) {
                Materials currentMaterial = materialsWithCounter.getKey();
                int newAmountOnMaterials = numberOfMaterials.get(currentMaterial).intValue() - materialsWithCounter.getValue() * amount;
                numberOfMaterials.put(currentMaterial, new AtomicInteger(newAmountOnMaterials));
            }
        }
        return true;
    }
}
