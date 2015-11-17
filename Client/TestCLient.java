package Client;

import Server.Materials;
import javafx.util.Pair;

public class TestCLient {
    private static final int PORT = 5555;
    private static final String hostname = "localhost";

    public static void main(String[] args) {
        ConnectionProtocol connectionProtocol = new ConnectionProtocol(hostname, PORT);
        int ID = connectionProtocol.getClientsID();
        Supplier supplier = new Supplier(connectionProtocol.getMaterialsOnStorage());
        System.out.println(connectionProtocol.getMaterialsOnStorage());
        Pair<Materials, Integer> pair = supplier.createSupply();
        System.out.println(pair);

        connectionProtocol.sellMaterial(pair);

        System.out.println(connectionProtocol.getMoney());


    }
}
