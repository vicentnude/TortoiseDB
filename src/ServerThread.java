package src;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;

public class ServerThread implements Runnable {

    private Map<Integer, String> map;
    private Protocol protocol;
    private Socket socket;
    private SocketBuffer socketBuffer;

    /**
     * Constructor, creates a new protocol object
     * @param socket the socket used in the communication
     * @throws IOException error creating the socket.
     */
    public ServerThread(Socket socket) throws IOException {
        this.map            = new HashMap<>();
        this.socket         = socket;
        this.socketBuffer   = new SocketBuffer(this.socket);
        this.protocol       = new Protocol(this.socketBuffer);
    }

    @Override
    public void run() {

    }
}