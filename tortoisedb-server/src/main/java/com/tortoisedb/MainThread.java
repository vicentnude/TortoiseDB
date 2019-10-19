package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;




/**
 * MainThread class it  start the server and waits to clients connections.
 *
 * @author Vitor Carvalho and tortoiseDB
 */
public class MainThread {
    private ServerSocket serverSocket;
    private boolean running;
    private Socket socket;

    /**
     * Constructor of the class
     * @param port port that the server is allocated
     * @throws IOException error creating the socket.
     */
    public MainThread(int port) throws IOException {
        this.serverSocket   = new ServerSocket(port);
        this.running        = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startServer() throws IOException {
        while (this.running) {
            System.out.println("Server running...");

            this.socket = serverSocket.accept();
            new Thread(new com.tortoisedb.ServerThread(this.socket)).start();
        }
    }

    public void close() throws IOException {
        this.socket.close();
    }
}