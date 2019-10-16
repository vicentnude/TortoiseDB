package com.tortoisedb;

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

    public void start(String user) {
        try {
            socketBuffer.write_command("STRT");
            socketBuffer.write_space();
            socketBuffer.write_string(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getCommand() throws IOException {
        return this.socketBuffer.read_command();
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
}
