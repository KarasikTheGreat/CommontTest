package Server;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Goods implements Serializable {
    private String name;
    private List<MaterialsWithCounter> requiredMaterials;

    public Goods(String name, List<MaterialsWithCounter> materials) {
        this.name = name;
        requiredMaterials = new ArrayList<>(materials);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        int price = 0;
        for (MaterialsWithCounter materialsWithCounter : requiredMaterials) {
            price += materialsWithCounter.getPrice();
        }

        return price;
    }

    public Iterable<MaterialsWithCounter> getRequiredMaterials() {
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
