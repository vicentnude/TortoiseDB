package src;


import java.io.*;
import java.net.Socket;
import java.util.List;

public class Protocol implements Runnable {

    private SocketBuffer socketBuffer;
    private String message;
    private State state;
    private boolean connected;

    public Protocol(SocketBuffer socketBuffer) throws IOException {
        this.socketBuffer = socketBuffer;
        //socketBuffer = new SocketBuffer(clientSocket);
        state = State.STRT;
        connected = true;
    }

    @Override
    public void run() {
        try {
            while(connected){
                switch (state){
                    case STRT:
                        System.out.println("START");
                        break;
                    case SETT:
                        System.out.println("SET VALUE TO KEY");
                        break;
                    case GETT:
                        System.out.println("GETTING VALUE FROM KEY");
                        break;
                    case DELT:
                        System.out.println("DELETING KEY-VALUE");
                        break;
                    case UPDT:
                        System.out.println("UPDATING VALUE FROM KEY");
                        break;
                    case EXST:
                        System.out.println("CHECKING EXISTING KEY-VALUE");
                        break;
                    case EXIT:
                        System.out.println("CLOSING CONNECTION");
                        break;
                }
            }
        }catch (IndexOutOfBoundsException ex){
            System.out.println("Connection with client interrupted");
        }
    }


    //Possible server states
    //Possible server states
    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT }
}
