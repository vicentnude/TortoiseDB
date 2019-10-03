package clientApp.TortoiseDBClient.src;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Protocol {
    private SocketBuffer socketBuffer;
    private Scanner sc;

    public Protocol(Socket socket) throws IOException {
        this.socketBuffer = new SocketBuffer(socket);
    }

    public String readServerMessage() throws IOException {
        return this.socketBuffer.read_string();
    }

    public void start() {
    }

    public void set() {
    }

    public void get() {
    }

    public void delete() {
    }

    public void update() {
    }

    public void exist() {
    }

    public void exit() {
    }

    private void printHelpMessage() {
        System.out.println("Set: Set the value");
        System.out.println("Get: Get the value from a key");
        System.out.println("Delete: Delete the value from a key");
        System.out.println("Update: Update a value from a key");
        System.out.println("Exist: Show if exist a key-value");
        System.out.println("Exit: Close connection to the database");
    }
}
