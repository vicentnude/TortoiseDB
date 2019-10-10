package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;

public class InteractionLogicClient {
    //All possible client states
    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, EXIT}

    private Protocol protocol;
    private boolean isRunning;
    private State state;
    private TerminalInterface terminalInterface;

    public InteractionLogicClient(Socket socket) throws IOException{
        this.protocol           = new Protocol(socket);
        this.state              = State.STRT;
        this.terminalInterface  = new TerminalInterface();
        this.isRunning          = true;
    }

    public void run() throws IOException {
        try{
            while(this.isRunning){
                switch (state){
                    case STRT:
                        this.protocol.start();
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
                        this.isRunning = true;
                        break;

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
}
