import java.io.IOException;

public class SpeedTest {
    public static void main(String args[])  {
        try {
            Petitions petitions = new Petitions();

            for (int i = 0;  i < 10000; i++)  {
                Thread tx           = new Thread(petitions);
                tx.start();
            }
        } catch (IOException ex) {
            System.err.println("Can't start the server test: " + ex.getMessage());
        }
    }
}
