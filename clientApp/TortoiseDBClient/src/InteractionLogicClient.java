package clientApp.TortoiseDBClient.src;

import java.io.IOException;
import java.net.Socket;

public class InteractionLogicClient {

    private Protocol protocol;
    private boolean endconnection;
    private State state;

    public InteractionLogicClient(Socket socket) throws IOException{
        this.protocol = new Protocol(socket);
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
            System.out.println("Lost connection to the server...");
        }
    }

    public String readServerMessage() throws IOException {
        return protocol.readServerMessage();
    }
    //All possible client states
    private enum State{STRT, SETT, GETT, DELT, UPDT, EXST, EXIT}
}
