package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;

public class Protocol {
    private SocketBuffer socketBuffer;


    public Protocol(Socket socket) throws IOException {
        this.socketBuffer = new SocketBuffer(socket);
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
    public void set(char key,String value) {
        try {
            socketBuffer.write_command("SETT");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
            socketBuffer.write_space();
            socketBuffer.write_string(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void increment(char key) {
        try {
            socketBuffer.write_command("INCR");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void incrementBy(char key,String value) {
        try {
            socketBuffer.write_command("INBY");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
            socketBuffer.write_space();
            socketBuffer.write_string(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void decrement(char key) {
        try {
            socketBuffer.write_command("DECR");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void get(char key) {
        try {
            socketBuffer.write_command("GETT");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void delete(char key) {
        try {
            socketBuffer.write_command("DELT");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String read_buffer() {
        String output="";
        try {
            output+=socketBuffer.read_command();
            output+=socketBuffer.read_space();
            output+=socketBuffer.read_string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }
    public void update(char key,String value) {
        try {
            socketBuffer.write_command("UPDT");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
            socketBuffer.write_space();
            socketBuffer.write_string(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exist(char key) {
        try {
            socketBuffer.write_command("EXST");
            socketBuffer.write_space();
            socketBuffer.write_char(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exit() {
        try{
            socketBuffer.write_command("EXIT");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
