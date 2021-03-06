package Client;

import Server.Materials;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Supplier {
    private List<Materials> materials;

    public Supplier(ConcurrentHashMap<Materials, AtomicInteger> materialsOnStorage) {
        materials = new ArrayList<>();
        for (ConcurrentHashMap.Entry<Materials,AtomicInteger> material : materialsOnStorage.entrySet()) {
            materials.add(material.getKey());
        }
    }

    public Pair<Materials, Integer> createSupply() throws NullPointerException{

        if (materials.size() == 0) {

            throw new NullPointerException();
        }
        Random rnd = new Random();
        int numberOfMaterial = rnd.nextInt(materials.size());
        int counter = rnd.nextInt(4) + 1;
        Materials material = materials.get(numberOfMaterial);
        Pair<Materials,Integer> materialsWithAmount = new Pair<>(material,counter);
        return  materialsWithAmount;
    }
}


