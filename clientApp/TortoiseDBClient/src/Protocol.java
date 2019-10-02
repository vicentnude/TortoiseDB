package clientApp.TortoiseDBClient.src;

import java.io.IOException;
import java.net.Socket;

    public class Protocol {
    private SocketBuffer socketBuffer;

    public Protocol(Socket socket) throws IOException {
        this.socketBuffer = new SocketBuffer(socket);
    }


    public String readServerMessage() throws IOException {
        return this.socketBuffer.read_string();
    }
}
