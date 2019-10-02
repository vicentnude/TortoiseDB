package clientApp.TortoiseDBClient.src;

import java.io.IOException;
import java.net.Socket;

public class Communication {
    private Protocol protocol;
    private String serverMessage;
    private boolean isRunning;
    private TerminalInterface terminalInterface;

    public Communication(Socket socket) throws IOException {
        this.protocol           = new Protocol(socket);
        this.serverMessage      = "";
        this.isRunning          = true;
        this.terminalInterface  = new TerminalInterface();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void startCommunication() throws IOException {
        this.displayStartMessage();
        this.readSocket();
    }

    private void displayStartMessage() {
        this.terminalInterface.displayStartMessage("TortoiseDB");
    }

    private void getClientAction() throws IOException {

    }

    private void readSocket() throws IOException {
        while (this.isRunning()) {
            this.serverMessage = this.protocol.readServerMessage();

            switch (this.serverMessage) {
                //TODO: put server messages case here!..
            }

            this.getClientAction();
        }
    }
}
