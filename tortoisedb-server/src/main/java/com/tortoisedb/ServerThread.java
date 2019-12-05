package com.tortoisedb;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread implements Runnable {

    private Map<String, String> map;
    private Protocol protocol;
    private MyLoggerHandler clientLogger;
    private State state;
    private boolean isRunning;
    private Socket socket;
    private String user;

    private int counter;
    private int interval;

    /**
     * Constructor, creates a new protocol object
     * @param socket the socket used in the communication
     * @throws IOException error creating the socket.
     */
    public ServerThread(Socket socket) throws IOException {
        this.map            = new ConcurrentHashMap<>();
        //this.clientLogger   = new MyLoggerHandler(this.getClass().getName());
        this.protocol       = new Protocol(socket);
        this.state          = State.STRT;
        this.socket         = socket;

        this.counter = 0;
        this.interval = 10;

        isRunning = true;
    }

    @Override
    public void run() {
        try {
            while(this.isRunning) {
                counter();
                state = checkCommand(protocol.getCommand());
                if (state == null) {
                    state = State.DEFA;
                }
                System.out.println(state); //temporal
                switch (this.state) {
                    case STRT:
                        this.readSTRT();
                        retrieveHashMap();
                        break;
                    case DELT:
                        this.deleteKeyValue();
                        break;
                    case EXST:
                        this.checkIfExistKeyvalue();
                        break;
                    case INCR:
                        this.incrValue();
                        break;
                    case INCRBY:
                        this.incrValuebyKey();
                        break;
                    case DECR:
                        this.decrKeyValue();
                        break;
                    case GETT:
                        this.getHashMapValue();
                        break;
                    case SETT:
                        this.setValueAndKeyInHashMap();
                        break;
                    case UPDT:
                        this.updateValueUsingKey();
                        break;
                    case EXIT:
                        this.isRunning = false;
                        //saveHashMap();
                        this.socket.close();
                        break;
                    case DEFA:
                        System.out.println("ERROR 503: Undefined error.");
                        break;
                    default:
                        System.out.println("ERROR 501: wrong typed command.");
                        break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }
    private void incrValue() {
        try{
            String key;
            this.protocol.readSpace();
            key = this.protocol.readSpace();
            if(exstInHashMap(key)) {
                incrByInHashMap(key,1);

            }
            else{
                this.protocol.error("The key is not saved in the Database");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void incrValuebyKey() {
        try{
            String key,increment;
            this.protocol.readSpace();
            key = this.protocol.readSpace();
            increment = this.protocol.readSpace();
            if(exstInHashMap(key)) {
                incrByInHashMap(key,Integer.parseInt(increment));

            }
            else{
                this.protocol.error("The key is not saved in the Database");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void decrKeyValue() {
        try{
            String key,increment;
            this.protocol.readSpace();
            key = this.protocol.readSpace();
            increment = this.protocol.readSpace();
            if(exstInHashMap(key)) {
                incrByInHashMap(key,-1);

            }
            else{
                this.protocol.error("The key is not saved in the Database");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteKeyValue() {
        try{
            String key;
            this.protocol.readSpace();
            key = this.protocol.readSpace();
            if(exstInHashMap(key)) {
                deltInHashMap(key);
                this.protocol.delete(key);
            }
            else{
                this.protocol.error("The key is not saved in the Database");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkIfExistKeyvalue() throws IOException {
        String key;

        this.protocol.readSpace();
        key     = this.protocol.readSpace();

        try{
            String exists = "0";
            if(exstInHashMap(key)){
                exists = "1";
            }
            this.protocol.exist(exists);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHashMapValue() throws IOException {
        String key, value;

        this.protocol.readSpace();
        key = this.protocol.readSpace();

        try{
            if(this.exstInHashMap(key)) {
                value = getInHashMap(key);
                this.protocol.get(key, value);
            }
            else{
                this.protocol.error("ERROR 502: Unexpected command.Key not found. Try SETT "+key+" value");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValueAndKeyInHashMap() throws IOException{
        String key,value;
        try {

            this.protocol.readSpace();
            key   = this.protocol.readSpace();
            this.protocol.readSpace();
            value     = this.protocol.getValue();

            if(exstInHashMap(key)){
                this.protocol.error("ERROR 502: Unexpected command.This key already exists. Try UPDT "+key+" "+value);
            }
            else{
                setInHashMap(key, value);
                this.protocol.set(key,value);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateValueUsingKey() throws IOException{
        String key, value;
        try{
            this.protocol.readSpace();
            key     = this.protocol.readSpace();
            this.protocol.readSpace();
            value   = this.protocol.getValue();

            if(exstInHashMap(key)) {
                updtInHashMap(key, value);
                this.protocol.update(key, value);
            }
            else{
                this.protocol.error("ERROR 502: Unexpected command.Key not found. Try SETT "+key+" "+value);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private State checkCommand(String command){
        for(State s:State.values()){
            if(s.name().equals(command))
                return s;
        }
        return null;
    }

    private void readSTRT() throws IOException{
        try {
            this.protocol.readSpace();
            String clientUser = this.protocol.readSocket(); //TODO Search if user exists
            user=clientUser;
            System.out.println("user:" + clientUser+" connected");
            this.protocol.start();
        }
        catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }
    private void deltInHashMap(String k){ this.map.remove(k); }

    private boolean exstInHashMap(String k){ return this.map.containsKey(k); }

    private String getInHashMap(String k){ return this.map.get(k); }
    

    private void incrByInHashMap(String k, int i){ this.map.put(k,this.map.get(k)+i); }

    private void setInHashMap(String k, String v){
        this.map.put(k,v);
    }

    private  void updtInHashMap(String k, String v){
        this.map.replace(k,v);
    }

    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT,DEFA,INCR,INCRBY,DECR }

    private void saveHashMap() {
        try {
            //Saving of object in a file
            FileOutputStream file = new FileOutputStream(user+"HM.db");
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.map);

            out.close();
            file.close();

            System.out.println(user+"HM.db"+" was created successfully");

        } catch (IOException ex) {
            System.out.println("Database can't be stored");
        }
    }

    private void retrieveHashMap() {
        try {
            // Reading the object from a file
            FileInputStream file = new FileInputStream(user+"HM.db");
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            this.map = (Map) in.readObject();

            in.close();
            file.close();
            System.out.println(user+"HM.db"+" was created successfully");
        } catch (IOException ex) {
            System.out.println("Database for current user not found.A new will be created a the end of the session");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

    }

    private void counter(){
        counter = (counter+1) % interval;
        if (counter <= 0)
            this.saveHashMap();
    }


}
