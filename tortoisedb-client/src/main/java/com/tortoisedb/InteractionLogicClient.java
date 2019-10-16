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
                        this.protocol.set();
                        break;
                    case GETT:
                        this.protocol.get();
                        break;
                    case DELT:
                        this.protocol.delete();
                        break;
                    case UPDT:
                        this.protocol.update();
                        break;
                    case EXST:
                        this.protocol.exist();
                        break;
                    case EXIT:
                        this.protocol.exit();
                        this.isRunning = false;
                        break;
                    case DEFA:
                        System.out.println("No existe este comando");
                        break;
                }
                newCommand = sc.nextLine();
                state = checkCommand(newCommand);
                if(state == null){
                    state = State.DEFA;
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
