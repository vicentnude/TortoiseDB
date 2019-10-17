package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InteractionLogicClient {
    //All possible client states
    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, EXIT,DEFA}

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
        try{
            while(this.isRunning){
                switch (state){
                    case STRT:
                        this.protocol.start(user);
                        System.out.println(this.protocol.getCommand());
                        this.state = State.EXIT;
                        break;
                    case SETT:
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){

                            this.protocol.set(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println("Key & Value correctly set.");
                        }
                        else{
                            System.out.println("Expected format:<command><space><char><space><string>");
                        }
                        break;
                    case GETT:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.get(newCommand.charAt(5));
                        }
                        else{
                            System.out.println("Expected format:<command><space><char>");
                        }
                        break;
                    case DELT:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.delete(newCommand.charAt(5));
                        }
                        else{
                            System.out.println("Expected format:<command><space><char>");
                        }
                        break;
                    case UPDT:
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){

                            this.protocol.update(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println("Key & Value correctly updated.");
                        }
                        else{
                            System.out.println("Expected format:<command><space><char><space><string>");
                        }
                        break;
                    case EXST:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.exist(newCommand.charAt(5));
                        }
                        else{
                            System.out.println("Expected format:<command><space><char>");
                        }
                        break;
                    case EXIT:
                        this.protocol.exit();
                        this.isRunning = false;
                        break;
                    case DEFA:
                        System.out.println("No existe este comando");
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
            this.terminalInterface.printErrorMessage("Unable to execute application!");
            this.isRunning = false;
        }
    }

    public String readServerMessage() throws IOException {
        return protocol.readServerMessage();
    }
    public void setUser(String user){
        this.user = user;
    }
    public State checkCommand(String command){
        for(State s:State.values()){
            if(s.name().equals(command))
               return s;
        }
        return null;
    }
}
