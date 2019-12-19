package com.tortoisedb;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    private static int PORT = 1212;
    private static String SERVER = "localhost";
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            String server;
            System.out.println("Which is the IP of the server?");
            server = sc.nextLine();
            if (server.equals("")){
                server = SERVER;
            }
            Connect connect             = new Connect(server, PORT);
            Communication communication = new Communication(connect.getSocket());
            communication.startCommunication();
        } catch (IOException exception) {
            System.err.println("ERROR 403: Undefined error." + exception.getMessage());
        }
    }





















    private static boolean checkIfIsNumber(String num){
        String c = "0123456789";
        for(int i=0;i<num.length();i++){
            if (!c.contains(String.valueOf(num.charAt(i)))){
                return false;
            }
        }
        return true;
    }
}
