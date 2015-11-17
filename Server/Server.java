package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    static final int PORT = 5555;
    private static Firm firm;
    private static ServerSocket serverSocket;
    private static Integer lastClientsID = 0;
    private static final Object lockID = new Object();

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Incorrect arguments. Please enter start balance of firm");
            System.exit(1);
        }
        int startBalance = 0;
        try {
            startBalance = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Incorrect input cant parse int from arguments");
            System.exit(1);
        }
        firm = new Firm(startBalance);
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Can't use port " + PORT);
            System.exit(1);
        }
        while (serverSocket.isBound()) {
            try {
                handleClient(serverSocket.accept());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void handleClient(Socket socket) {
        new Thread(() -> {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                loop:
                while (true) {
                    try {
                        Commands command = (Commands) objectInputStream.readObject();
                        switch (command) {

                            case HANDLE_ORDER:
                                Order order = (Order) objectInputStream.readObject();
                                objectOutputStream.writeObject(firm.handleOrder(order));
                                break;

                            case BUY_MATERIAL:
                                MaterialsWithCounter material = (MaterialsWithCounter) objectInputStream.readObject();
                                objectOutputStream.writeObject(firm.buyMaterial(material));
                                break;

                            case GET_CLIENTS_ID:
                                synchronized (lockID) {
                                    objectOutputStream.writeObject(lastClientsID);
                                    lastClientsID++;
                                }
                                break;
                            case GET_BALANCE:
                                objectOutputStream.writeObject(firm.getBalance());
                                break;

                            case GET_GOODS_LIST:
                                objectOutputStream.writeObject(firm.getGoodsList());
                                break;
                            case GET_ORDER_HISTORY:
                                objectOutputStream.writeObject(firm.getOrderHistory());
                            case GET_MATERIALS_ON_STORAGE:
                                objectOutputStream.writeObject(firm.getMaterialsOnStorage());
                                break;

                            case CLOSE_CONNECTION:
                                objectInputStream.close();
                                objectOutputStream.close();
                                socket.close();
                                break loop;
                        }
                    }catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

}