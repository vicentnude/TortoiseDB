package clientApp.TortoiseDBClient.src;

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
        message         = "You are now connected to database " + dataBase + " as user " + user;
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

    public void printHelpMessage() {
        System.out.println("Set: Set the value");
        System.out.println("Get: Get the value from a key");
        System.out.println("Delete: Delete the value from a key");
        System.out.println("Update: Update a value from a key");
        System.out.println("Exist: Show if exist a key-value");
        System.out.println("Exit: Close connection to the database");
    }

    public void printErrorMessage(String errorMessage) {
        System.err.println(errorMessage);
    }
}
