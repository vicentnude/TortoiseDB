package com.tortoisedb;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class InteractionLogicServer {
    private BufferedWriter bw;
    public Protocol protocol;
    private boolean endconnection, running;
    private State state;
    private MyLoggerHandler loggerHandler;

    public InteractionLogicServer(Socket clientSocket, MyLoggerHandler loggerHandler) throws IOException {
        this.protocol       = new Protocol(clientSocket);
        this.running        = true;
        this.state          = State.STRT;
        this.loggerHandler  = loggerHandler;
    }

    public void run() throws IOException {
        try{
            while(!this.running){
                switch (this.state){
                    case STRT:
                        protocol.start();
                        System.out.println("out of strt");
                        break;
                    case SETT:
                        System.out.println("done");
                        protocol.set();
                        break;
                    case GETT:
                        protocol.get();
                        break;
                    case DELT:
                        protocol.delete();
                        break;
                    case UPDT:
                        protocol.update();
                        break;
                    case EXST:
                        protocol.exist();
                        break;
                    case EXIT:
                        protocol.exit();
                        endconnection = true;
                        break;
                    case DEFA:
                        System.out.println("Something went wrong");
                        break;
                }
                state = checkCommand(protocol.getCommand());
                if(state == null){
                    state = State.DEFA;
                }
                System.out.println(state);
            }
        } catch (NullPointerException ex){
            System.out.println("Lost connection to the client...");

        }
    }

    //Possible server states
    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT,DEFA }

    public State checkCommand(String command){
        for(State s:State.values()){
            if(s.name().equals(command))
                return s;
        }
        return null;
    }
}
