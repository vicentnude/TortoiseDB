package com.tortoisedb;


import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Protocol {

   private SocketBuffer socketBuffer;
    private String message;
    private boolean connected;

    public Protocol(Socket clientSocket) throws IOException {
        this.socketBuffer = new SocketBuffer(clientSocket);
        connected = true;
    }

    public String getCommand() throws IOException {
        return this.socketBuffer.read_command();
    }
    public String readSpace() throws IOException {
        return this.socketBuffer.read_space();
    }
    public String readSocket() throws IOException {
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
        try {
            this.socketBuffer.write_command("STRS");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String key,String value) {
        try {
            socketBuffer.write_command("ACKN");
            socketBuffer.write_space();
            socketBuffer.write_string("Key:"+key+" & value:"+value+" correctly sett.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get(String key,String value) {
        try {
            socketBuffer.write_command("RETN");
            socketBuffer.write_space();
            socketBuffer.write_string("key:"+key+" value:"+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String key,String value) {
        try {
            socketBuffer.write_command("ACKN");
            socketBuffer.write_space();
            socketBuffer.write_string("Key:"+key+" & value:"+value+" correctly deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(String key,String value) {
        try {
            socketBuffer.write_command("ACKN");
            socketBuffer.write_space();
            socketBuffer.write_string("Key:"+key+" & value:"+value+" correctly updated.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exist(String exists) {
        try {
            socketBuffer.write_command("EXSA");
            socketBuffer.write_space();
            socketBuffer.write_string(exists);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
