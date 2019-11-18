package com.tortoisedb;

import java.util.Scanner;

public class TerminalInterface {
    private Scanner scanner;
    public String user;

    public TerminalInterface() {
        this.scanner = new Scanner(System.in);
    }

    public void displayStartMessage(String dataBase) {
        String message  = "User: ";
        this.displayMessage(message); //Todo: change this to saver user
        user            = askString();
        message         = "You are now connected to database " + dataBase + " as user " + user;
        this.displayMessage(message);
    }

    public void displayMessage(String messsage) {
        System.out.println(messsage);
    }

    public String askString() { return scanner.nextLine(); }

    public void printErrorMessage(String errorMessage) { System.err.println(errorMessage); }

    public String getUser(){ return user; }
}
