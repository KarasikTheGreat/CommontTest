package Server;

import java.io.Serializable;

public class MaterialsWithCounter implements Serializable {
    private int amount;
    private Materials material;

    public MaterialsWithCounter( Materials material,int amount) {

        this.material = material;
        this.amount = amount;
    }

    public int getPrice() {
        return material.getPrice()*amount;
    }

    public Materials getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MaterialsWithCounter)) return false;

        MaterialsWithCounter that = (MaterialsWithCounter) o;

        if (amount != that.amount) return false;
        return material.equals(that.material);

    }

    @Override
    public String toString() {
        return getMaterial() +" : " + getAmount();
    }

    @Override
    public int hashCode() {
        int result = amount;
        result = 31 * result + material.hashCode();
        return result;
    }
}
