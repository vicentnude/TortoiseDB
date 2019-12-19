package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InteractionLogicClient {

    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, INCR, DECR, LOAD,INBY, DEBY, SADD, SREM, SAVE, HELP, EXIT, DEFA}

    private Protocol protocol;
    private boolean isRunning;
    private State state;
    private TerminalInterface terminalInterface;
    private String user;
    private Scanner sc;
    private String newCommand;

    public InteractionLogicClient(Socket socket) throws IOException{
        this.protocol           = new Protocol(socket);
        this.state              = State.STRT;
        this.terminalInterface  = new TerminalInterface();
        this.isRunning          = true;
        sc = new Scanner(System.in);

    }

    public void run() throws IOException {
        int position;
        try{
            while(this.isRunning){
                switch (state){
                    case STRT:
                        this.protocol.start(user);
                        this.protocol.getCommand();
                        System.out.println("Type help if you want to know how to start");
                        this.state = State.EXIT;
                        break;
                    case LOAD:
                        this.protocol.load();
                        break;
                    case SETT:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.set(key, value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case GETT:
                        position = newCommand.indexOf(" ");
                        if(position == 4) {
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 60 ){
                                System.out.println("The key are too long.");
                            }
                            else {
                                this.protocol.get(key);
                                System.out.println(this.protocol.read_buffer());
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case INBY:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.incrementBy(key,value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DEBY:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.decrementBy(key,value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case INCR:
                        position = newCommand.indexOf(" ");
                        if(position == 4) {
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 60 ){
                                System.out.println("The key are too long.");
                            }
                            else {
                                this.protocol.increment(key);
                                System.out.println(this.protocol.read_buffer());
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DECR:
                        position = newCommand.indexOf(" ");
                        if(position == 4) {
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 60 ){
                                System.out.println("The key are too long.");
                            }
                            else {
                                this.protocol.decrement(key);
                                System.out.println(this.protocol.read_buffer());
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DELT:
                        position = newCommand.indexOf(" ");
                        if(position == 4) {
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 60){
                                System.out.println("The key/value are too long.");
                            }
                            else {
                                this.protocol.delete(key);
                                System.out.println(this.protocol.read_buffer());
                            }

                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case UPDT:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1) {
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.update(key,value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case EXST:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 60){
                                System.out.println("The key/value are too long.");
                            }
                            else {
                                this.protocol.exist(key);
                                String exists = this.protocol.read_buffer();
                                if(exists.charAt(exists.length()-1) == '1'){
                                    System.out.println("The key exists.");
                                }
                                else{
                                    System.out.println("The key does not exists.");
                                }
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case SADD:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.sadd(key,value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case SREM:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 60 || value.length() >= 60){
                                    System.out.println("The key/value are too long.");
                                }
                                else {
                                    this.protocol.srem(key,value);
                                    System.out.println(this.protocol.read_buffer());
                                }
                            }
                            else{
                                System.out.println("ERROR 402: Unexpected format.");
                            }
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case SAVE:
                        if(newCommand.length() == 4) {
                            this.protocol.save();
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case HELP:
                        if(newCommand.length() == 4) {
                            help();
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case EXIT:
                        if(newCommand.length() == 4) {
                            this.protocol.exit();
                            this.isRunning = false;
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DEFA:
                        System.out.println("ERROR 401: wrong typed command.");
                        break;
                }
                if(this.isRunning) {
                    newCommand = sc.nextLine();
                    if (newCommand.length() < 4) {
                        state = null;
                    } else {
                        state = checkCommand(newCommand.substring(0, 4));
                    }
                    if (state == null) {
                        state = State.DEFA;
                    }
                }
            }
        } catch (NullPointerException ex){
            this.terminalInterface.printErrorMessage("ERROR 403: Undefined error.");
            this.isRunning = false;
        }
    }

    private void help() {
        System.out.println("Welcome to Tortoise DB\n");
        System.out.println("Commands:\n\n");

        System.out.println("SETT <SP> <key> <SP> <string>: ");
        System.out.print("When a sett message is sent the client wants to save a key value in the DB. In case that the key already exist in the DB, this function returns an error and the user can update (UPDT) the function if he wants to\n\n");

        System.out.println("GETT <SP> <key>: ");
        System.out.print("The gett function returns the value from a key provided by the client.\n\n");

        System.out.println("DELT <SP> <key>: ");
        System.out.print("Delete the key, value using the key provided by the client.\n\n");

        System.out.println("UPDT <SP> <key> <SP> <string>: ");
        System.out.print("Update the value using the key provided by the client.\n\n");

        System.out.println("EXST <SP> <key> : ");
        System.out.print("Checks if the key exist.\n\n");

        System.out.println("INCR <SP> <key>:  ");
        System.out.print("The incr function adds one to the value from a key provided by the client.\n\n");

        System.out.println("DECR <SP> <key>: ");
        System.out.print("The decr  function subtract one to the value from a key provided by the client.\n\n");

        System.out.println("INCRBY <SP> <key> <SP> <int>: ");
        System.out.print("The incrby  function adds the given integer to the value from the key provided by the client.\n\n");

        System.out.println("SADD <SP> <key> <SP> <string>: ");
        System.out.print("Appends the value to a list given by the key, if the key doesn't exist it creates a new list and adds the value.\n\n");

        System.out.println("SREM <SP> <key> <SP> <string>: ");
        System.out.print("removes the value from the list given by the key.\n\n");

        System.out.println("SAVE:  ");
        System.out.println("saves the database to server.\n");

        System.out.println("LOAD:  ");
        System.out.println("load the database from server.\n");

        System.out.println("EXIT:  ");
        System.out.println("closes the connection between the client and the server. End the client application.");
        System.out.println("");

    }

    public void setUser(String user){
        this.user = user;
    }
    public State checkCommand(String command){
        command = command.toUpperCase();
        for(State s:State.values()){
            if(s.name().equals(command))
               return s;
        }
        return null;
    }
}
