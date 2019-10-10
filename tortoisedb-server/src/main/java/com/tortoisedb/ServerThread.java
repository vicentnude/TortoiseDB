package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;

public class ServerThread implements Runnable {

    private Map<Integer, String> map;
    private Protocol protocol;
    private Socket socket;
    private SocketBuffer socketBuffer;
    private InteractionLogicServer logicServer;

    /**
     * Constructor, creates a new protocol object
     * @param socket the socket used in the communication
     * @throws IOException error creating the socket.
     */
    public ServerThread(Socket socket) throws IOException {
        this.map            = new HashMap<>();
        this.socket         = socket;
        this.logicServer    = new InteractionLogicServer(socket);
    }

    @Override
    public void run() {

    }
}