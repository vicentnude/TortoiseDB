package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;

public class Connect {
    private Socket socket;
    private InetAddress server;

    public Connect(String server, int port) throws IOException {
        this.server = InetAddress.getByName(server);
        this.socket = new Socket(this.server, port);
    }

    public Socket getSocket() {
        return socket;
    }

}
