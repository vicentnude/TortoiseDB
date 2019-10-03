package src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;

public class InteractionLogicServer {

    private BufferedWriter bw;
    private Protocol protocol;
    private boolean endconnection, connected;
    private State state;

    public InteractionLogicServer(Socket clientSocket) throws IOException {
        this.protocol = new Protocol(clientSocket);
        connected = true;
        state = State.STRT;
    }
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
