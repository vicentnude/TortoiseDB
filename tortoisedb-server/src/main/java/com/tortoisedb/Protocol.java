package com.tortoisedb;


import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Protocol {

    private SocketBuffer socketBuffer;
    private String message;
    private Scanner sc;
    private boolean connected;

    public Protocol(Socket clientSocket) throws IOException {
        this.socketBuffer = new SocketBuffer(clientSocket);
        connected = true;
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
