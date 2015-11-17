package Client;

import Server.Materials;
import Server.MaterialsWithCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Supplier {
    private List<Materials> materials;

    public Supplier(Iterable<MaterialsWithCounter> materialsWithCounter) {
        materials = new ArrayList<>();
        for (MaterialsWithCounter materialWithCounter : materialsWithCounter) {
            materials.add(materialWithCounter.getMaterial());
        }
    }

    public MaterialsWithCounter createSupply() throws NullPointerException{
        Random rnd = new Random();
        if (materials.size() == 0) {

            throw new NullPointerException();
        }
        int numberOfMaterial = rnd.nextInt(materials.size());
        int counter = rnd.nextInt(4) + 1;
        return new MaterialsWithCounter(materials.get(numberOfMaterial), counter);
    }
}


