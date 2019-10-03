package clientApp.TortoiseDBClient.src;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Protocol {
    private SocketBuffer socketBuffer;
    private BufferedWriter bw;
    private Scanner sc;

    public Protocol(Socket socket) throws IOException {
        this.socketBuffer = new SocketBuffer(socket);
        sc = new Scanner(System.in);
    }


    public String readServerMessage() throws IOException {
        return this.socketBuffer.read_string();
    }

    public void start() {
        try{
            socketBuffer.send_function("STRT");
        } catch (IOException ex) {
            if(ex.getMessage().contains("Connection reset")) System.out.println("Lost connection to client (STRT)...");
            else if(ex.getMessage().contains("Broken pipe")) System.out.println("Lost connection to client (STRT)...");
            else ex.printStackTrace();
        }
    }

    public void set() {
    }

    public void get() {
    }

    public void delete() {
    }

    public void update() {
    }

    public void exist() {
    }

    public void exit() {
    }

    public char nextAction() {
        String readAction;

        System.out.println("\nWhat would you like to do next? (H)it, (B)et, (S)how or Surrende(R)");
        System.out.print("(or you can write '?' to get info): ");

        readAction = sc.next().toLowerCase();

        while (readAction.length() > 1 || !isCorrectInput(readAction.toLowerCase().charAt(0))) {
            if(readAction.charAt(0) == '?') {
                printHelpMessage();
                System.out.println("\nWhat would you like to do next? (S)et, (G)et, (D)elete, (U)pdate, (E)xist or E(X)it");
            }else {
                System.out.println("Incorrect input; write S, G, D, U, E or X (or ?)");
            }
            readAction = sc.next().toLowerCase();
        }

        return readAction.charAt(0);
    }

    private boolean isCorrectInput(char readAction){
        switch(readAction) {
            case 'h':
            case 'b':
            case 's':
            case 'r':
                return true;
        }
        return false;
    }

    private void printHelpMessage() {
        System.out.println("Hit: ask for an extra card");
        System.out.println("Bet: double the current bet");
        System.out.println("Show: show hand to the dealer and stand; this will end the current round");
        System.out.println("Surrender: retire from this round; this will cause a loss");
    }
}
