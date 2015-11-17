package Server;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Goods implements Serializable {
    private String name;
    private Map<Materials, Integer> requiredMaterials;

    public Goods(String name, Map<Materials, Integer> materials) {
        this.name = name;
        requiredMaterials = new HashMap<>(materials);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        int price = 0;
        for (HashMap.Entry<Materials,Integer> materialsWithCounter : requiredMaterials.entrySet()) {
            Materials currentMaterial = materialsWithCounter.getKey();
            price += materialsWithCounter.getValue()*currentMaterial.getPrice();
        }

        return price;
    }

    public Map<Materials, Integer> getRequiredMaterials() {
        return requiredMaterials;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goods)) return false;

        Goods goods = (Goods) o;

        if (!name.equals(goods.name)) return false;
        return requiredMaterials.equals(goods.requiredMaterials);

    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + requiredMaterials.hashCode();
        return result;
    }
}
