package Client;

import Server.*;
import javafx.util.Pair;
import sun.net.ConnectionResetException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionProtocol {
    private String hostname;
    private int port;
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public ConnectionProtocol(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        connect();
    }

    private void connect() {
        boolean connected = false;
        while (!connected) {
            try {
                socket = new Socket(hostname, port);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                connected = true;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean handleOrder(Order order) {
        try {
            objectOutputStream.writeObject(Commands.HANDLE_ORDER);
            objectOutputStream.writeObject(order);
            return (Boolean) objectInputStream.readObject();
        } catch (IOException e) {
            connect();
            return handleOrder(order);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sellMaterial(Pair<Materials, Integer> materialsWithCounter) {
        try {
            objectOutputStream.writeObject(Commands.BUY_MATERIAL);
            objectOutputStream.writeObject(materialsWithCounter);
            return (Boolean) objectInputStream.readObject();
        } catch (ConnectionResetException e) {
            connect();
            return sellMaterial(materialsWithCounter);
        } catch (IOException e) {
            connect();
            return sellMaterial(materialsWithCounter);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getClientsID() {
        try {
            objectOutputStream.writeObject(Commands.GET_CLIENTS_ID);
            return (Integer) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getMoney() {
        try {
            objectOutputStream.writeObject(Commands.GET_BALANCE);
            return (Integer) objectInputStream.readObject();
        } catch (IOException e) {
            connect();
            return getMoney();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Iterable<Goods> getGoodsList() {
        try {
            objectOutputStream.writeObject(Commands.GET_GOODS_LIST);
            return (Iterable<Goods>) objectInputStream.readObject();
        } catch (IOException e) {
            connect();
            return getGoodsList();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public Iterable<Order> getOrderistory() {
        try {
            objectOutputStream.writeObject(Commands.GET_ORDER_HISTORY);
            return (Iterable<Order>) objectInputStream.readObject();
        } catch (IOException e) {
            connect();
            return getOrderistory();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }

    public ConcurrentHashMap<Materials, AtomicInteger> getMaterialsOnStorage() {
        try {
            objectOutputStream.writeObject(Commands.GET_MATERIALS_ON_STORAGE);
            return (ConcurrentHashMap<Materials, AtomicInteger>) objectInputStream.readObject();
        } catch (IOException e) {
            connect();
            return getMaterialsOnStorage();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            objectOutputStream.writeObject(Commands.CLOSE_CONNECTION);

        } catch (IOException e) {
            connect();
            closeConnection();
        }
        try {
            objectOutputStream.close();
            objectInputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
