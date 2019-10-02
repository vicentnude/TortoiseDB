package clientApp.TortoiseDBClient.src;

import java.io.IOException;

public class Client {
    private static int PORT = 1212;
    private static String SERVER = "localhost";

    public static void main(String[] args) {
        try {
            Connect connect     = new Connect(SERVER, PORT);
            Communication communication = new Communication(connect.getSocket());
            communication.startCommunication();
        } catch (IOException exception) {
            System.err.println("Error connecting to the server " + exception.getMessage());
        }
    }
}
