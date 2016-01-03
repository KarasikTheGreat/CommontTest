package Server;

import java.io.Serializable;

public class Materials implements Serializable {
    private String name;
    private int price;

    public Materials(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Materials)) return false;

        Materials materials = (Materials) o;

        if (price != materials.price) return false;
        return name.equals(materials.name);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result ;
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
