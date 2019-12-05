package com.tortoisedb;


import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Protocol {

   private SocketBuffer socketBuffer;

    public Protocol(Socket clientSocket) throws IOException {
        this.socketBuffer = new SocketBuffer(clientSocket);
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
            socketBuffer.write_string("Key:"+key+" & value:"+value+" correctly set.");
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
    public void increment(String key,String value) {
        try {
            socketBuffer.write_command("RETN");
            socketBuffer.write_space();
            socketBuffer.write_string("key:"+key+" value:"+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void incrementBy(String key,String value) {
        try {
            socketBuffer.write_command("RETN");
            socketBuffer.write_space();
            socketBuffer.write_string("key:"+key+" value:"+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void decrement(String key,String value) {
        try {
            socketBuffer.write_command("RETN");
            socketBuffer.write_space();
            socketBuffer.write_string("key:"+key+" value:"+value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(String key) {
        try {
            socketBuffer.write_command("ACKN");
            socketBuffer.write_space();
            socketBuffer.write_string("Key:"+key+" correctly deleted.");
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

    public void error(String mns){
        try {
            socketBuffer.write_command("ERRO");
            socketBuffer.write_space();
            socketBuffer.write_string(mns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
