package src;

import com.tortoisedb.InteractionLogicServer;
import com.tortoisedb.Protocol;
import com.tortoisedb.SocketBuffer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Scanner;

public class ServerThread implements Runnable {

    private Map<String, String> hm;
    private Protocol protocol;
    private Socket socket;
    private SocketBuffer socketBuffer;
    private InteractionLogicServer logicServer;

    /**
     * Constructor, creates a new protocol object
     * @param socket the socket used in the communication
     * @throws IOException error creating the socket.
     */
    public ServerThread(Socket socket) throws IOException {
        this.hm            = new ConcurrentHashMap<String, String>();
        this.socket         = socket;
        this.logicServer    = new InteractionLogicServer(socket);
    }

    /**
     * --------------------------------------------------------------------------------------------------------
     */
    public void loop() {

        System.out.println("Welcome to Toroise DB");
        run();
        System.out.println("SYSTEM WILL SHUTDOWN, HAVE A NICE DAY");
    }
    public  void run(){
        Scanner sc=new Scanner(System.in);
        String option=" ";
        while(!option.contains("EXIT")){
            System.out.print("TortoiseDB: ");
            option=sc.nextLine();
            try{
                menu(option);
            } catch (Exception e){
                if (!option.contains("EXIT")){
                    System.out.println("ERROR: COMMAND HAS NOT REQUIRED NUMBER OF OPERATORS");
                }
            }

        }

    }
    public void menu(String option){
        String command=option.split("\\s+")[0];
        String k=option.split("\\s+")[1];
        String v="";
        System.out.println(command);
        switch(command){
            case "DELT":
                try{
                    deltInHashMap(k);
                }catch (Exception e){
                    System.out.println(" ERROR: KEY NOT FOUND ");
                }
                break;
            case "EXST":
                try{
                    v=option.split("\\s+")[2];
                    System.out.println(exstInHashMap(k,v));
                }catch (Exception e){
                    System.out.println(" ERROR: KEY & VALUE REQUIRED  ");
                }
                break;
            case "GETT":

                try{
                    System.out.println(getInHashMap(k));
                }catch (Exception e){
                    System.out.println(" ERROR: KEY NOT FOUND ");
                }
                break;
            case "SETT":
                try{
                    v=option.split("\\s+")[2];
                    setInHashMap(k,v);
                }catch (Exception e){
                    System.out.println(" ERROR: KEY & VALUE REQUIRED  ");
                }
                break;
            case "UPDT":
                try{
                    v=option.split("\\s+")[2];
                    updtInHashMap(k,v);
                }catch (Exception e){
                    System.out.println(" ERROR: KEY & VALUE REQUIRED ");
                }
                break;
            default:
                System.out.println("ERROR: COMMAND IS NOT RECOGNISED");
                break;
        }
    }
    public  void deltInHashMap(String k){
        hm.remove(k);
    }
    public  boolean exstInHashMap(String k, String v){
        return hm.containsKey(k);//hm.containsValue(v);
    }
    public  String getInHashMap(String k){
        return hm.get(k).toString();
    }
    public  void setInHashMap(String k, String v){
        hm.put(k,v);
    }
    public  void updtInHashMap(String k, String v){
        hm.replace(k,v);

    }

    /**
     * --------------------------------------------------------------------------------------------------------
     */
}