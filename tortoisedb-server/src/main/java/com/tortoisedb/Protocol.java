package com.tortoisedb;


import java.io.*;
import java.net.Socket;

public class Protocol {

    private SocketBuffer socketBuffer;
    private String message;
    private boolean connected;

    public Protocol(Socket clientSocket) throws IOException {
        this.socketBuffer = new SocketBuffer(clientSocket);
        connected = true;
    }

    public String getCommand() throws IOException {
        return this.socketBuffer.read_string();
    }

    public String getKey() throws IOException {
        return this.socketBuffer.read_string();
    }

    public String getValue() throws IOException {
        return this.socketBuffer.read_string();
    }

    public void writeBoolean(boolean booleanToWrite) throws IOException {

    }

    public void writeValue(String value) throws IOException {

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
