import java.io.IOException;
import java.net.Socket;

public class Petitions implements Runnable {
    private static int PORT = 1212;
    private static int NUM_FUNC = 4;
    private static String SERVER = "localhost";
    private int numberOfThreads;
    private Socket socket;
    private Protocol protocol;

    public Petitions() throws IOException {
        this.socket          = new Socket(SERVER, PORT);
        this.protocol        = new Protocol(this.socket);
        this.numberOfThreads = 10000;
    }

    @Override
    public void run() {
        int petition;

        for (int i = 0; i < this.numberOfThreads; i++) {
            try {

                petition = this.generateRandomIntegerBetween(NUM_FUNC, 1);

                switch (petition) {
                    case 1:
                        this.setKeyValue();
                        break;
                    case 2:
                        this.updateValue();
                        break;
                    case 3:
                        this.deleteKey();
                        break;
                    case 4:
                        this.checkIfExist();
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.protocol.exit();
    }

    private void deleteKey() throws IOException {
        char key = this.generateRandomChar();

        this.protocol.exist(key);
        final String exists = this.protocol.read_buffer();

        if (exists.charAt(exists.length() - 1) == '1') {
            this.protocol.delete(key);
            System.out.println(this.protocol.read_buffer());
        }
    }

    private void updateValue() throws IOException {
        char key    = this.generateRandomChar();
        char value  = this.generateRandomChar();

        this.protocol.exist(key);
        final String exists = this.protocol.read_buffer();

        if (exists.charAt(exists.length() - 1) == '1') {
            this.protocol.update(key, String.valueOf(value));
            System.out.println(this.protocol.read_buffer());
        }
    }

    private void setKeyValue() throws IOException {
        char key    = this.generateRandomChar();
        char value  = this.generateRandomChar();
        this.protocol.set(key, String.valueOf(value));
        System.out.println(this.protocol.read_buffer());
    }

    private void checkIfExist() throws IOException {
        char key = this.generateRandomChar();
        this.protocol.exist(key);
        final String exists = this.protocol.read_buffer();
    }

    private int generateRandomIntegerBetween(int sup, int inf) {
         return (int)(Math.random() * sup + inf);
    }

    private char generateRandomChar() {
        return (char) ((Math.random() * 26 ) + 'a');
    }
}
