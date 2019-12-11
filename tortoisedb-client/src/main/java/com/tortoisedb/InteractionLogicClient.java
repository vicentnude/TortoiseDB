package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InteractionLogicClient {

    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, INCR, DECR, INBY, SADD, SREM, SAVE, EXIT, DEFA}

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
                        System.out.println(this.protocol.getCommand());
                        this.state = State.EXIT;
                        break;
                    case SETT:
                        position = newCommand.indexOf(" ");
                        if(position == 4){
                            newCommand = newCommand.substring(position+1,newCommand.length());
                            position = newCommand.indexOf(" ");
                            if(position != -1){
                                String key = newCommand.substring(0,position);
                                String value = newCommand.substring(position+1,newCommand.length());
                                if(key.length() >= 40 || value.length() >= 40){
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
                            if(key.length() >= 40 ){
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
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){

                            this.protocol.incrementBy(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case INCR:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.increment(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DECR:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.decrement(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DELT:
                        position = newCommand.indexOf(" ");
                        if(position == 4) {
                            String key = newCommand.substring(position+1,newCommand.length());
                            if(key.length() >= 40){
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
                                if(key.length() >= 40 || value.length() >= 40){
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
                            if(key.length() >= 40){
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
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){
                            this.protocol.sadd(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case SREM:
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){
                            this.protocol.srem(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println(this.protocol.read_buffer());
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
