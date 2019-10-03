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

    public void run() throws IOException {
        endconnection = false;
        try{
            while(!endconnection){
                switch (state){
                    case STRT:
                        protocol.start();
                        break;
                    case SETT:
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

                }
            }
        } catch (NullPointerException ex){
            System.out.println("Lost connection to the client...");
        }
    }

    //Possible server states
    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT }
}
