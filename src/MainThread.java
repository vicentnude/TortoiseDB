import java.io.IOException;
import java.net.ServerSocket;

/**
 * MainThread class it  start the server and waits to clients connections.
 *
 * @author Vitor Carvalho and tortoiseDB
 */
public class MainThread {
    private ServerSocket socket;
    private ServerThread applicationHandler;
    private boolean running;

    /**
     * Constructor of the class
     * @param port port that the server is allocated
     * @throws IOException error creating the socket.
     */
    public MainThread(int port) throws IOException {
        this.socket = new ServerSocket(port);
        this.applicationHandler = new ServerThread(this.socket);
        this.running = true;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startServer() throws IOException {
        while (this.running) {
            System.out.println("Server running...");
            //a new thread
            Thread thread = new Thread(this.applicationHandler);
            thread.start();
        }
    }

    public void close() throws IOException {
        this.socket.close();
    }
}