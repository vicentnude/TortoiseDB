package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class InteractionLogicClient {

    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, EXIT, DEFA,INCR,DECR,INBY}

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
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case GETT:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.get(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case INBY:
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){

                            this.protocol.set(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case INCR:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.get(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DECR:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.get(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case DELT:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.delete(newCommand.charAt(5));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case UPDT:
                        if(newCommand.charAt(4) == ' ' && newCommand.charAt(6) == ' ' && newCommand.length()>7){

                            this.protocol.update(newCommand.charAt(5),newCommand.substring(7));
                            System.out.println(this.protocol.read_buffer());
                        }
                        else{
                            System.out.println("ERROR 402: Unexpected format.");
                        }
                        break;
                    case EXST:
                        if(newCommand.charAt(4) == ' ' && newCommand.length()>5) {
                            this.protocol.exist(newCommand.charAt(5));
                            String exists = this.protocol.read_buffer();
                            if(exists.charAt(exists.length()-1) == '1'){
                                System.out.println("The key exists.");
                            }
                            else{
                                System.out.println("The key does not exists.");
                            }
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
