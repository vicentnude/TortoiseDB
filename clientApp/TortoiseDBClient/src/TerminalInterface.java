import java.util.Scanner;

public class TerminalInterface {
    private Scanner scanner;

    public TerminalInterface() {
        this.scanner = new Scanner(System.in);
    }


    public void displayStartMessage(String dataBase) {
        String message  = "User: ";
        this.displayMessage(message); //Todo: change this to saver user
        String user     = askString();
        message         = "You are now connected to databse " + dataBase + " as user " + user;
        this.displayMessage(message);
    }

    public void displayMessage(String messsage) {
        System.out.println(messsage);
    }

    public int askInt() {
        return this.scanner.nextInt();
    }

    public String askString() {
        return scanner.nextLine();
    }
}
