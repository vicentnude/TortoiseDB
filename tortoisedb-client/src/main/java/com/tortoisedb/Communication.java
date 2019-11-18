package com.tortoisedb;

import java.io.IOException;
import java.net.Socket;

public class Communication {
    private InteractionLogicClient logicClient;
    private String serverMessage;
    private boolean isRunning;
    private TerminalInterface terminalInterface;

    public Communication(Socket socket) throws IOException {
        this.logicClient        = new InteractionLogicClient(socket);
        this.serverMessage      = "";
        this.isRunning          = true;
        this.terminalInterface  = new TerminalInterface();
    }

    public void startCommunication() throws IOException {
        this.displayStartMessage();
        this.logicClient.setUser(terminalInterface.getUser());
        this.readSocket();
    }

    private void displayStartMessage() {
        this.terminalInterface.displayStartMessage("TortoiseDB");
    }

    private void readSocket() throws IOException {
        logicClient.run();
    }
}
