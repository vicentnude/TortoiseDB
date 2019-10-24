package com.tortoisedb;

import java.io.IOException;
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
    private String space = " ";

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
        isRunning = true;
    }

    @Override
    public void run() {
        try {
            while(this.isRunning) {
                state = checkCommand(protocol.getCommand());
                if (state == null) {
                    state = State.DEFA;
                }
                System.out.println(state); //temporal
                switch (this.state) {
                    case STRT:
                        this.readSTRT();
                        break;
                    case DELT:
                        this.deleteKeyValue();
                        break;
                    case EXST:
                        this.checkIfExistKeyvalue();
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
                        this.socket.close();
                        break;
                    case DEFA:
                        System.out.println("Something went wrong");
                        break;
                    default:
                        System.out.println("501 - COMMAND IS NOT RECOGNISED");
                        break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }

    private void deleteKeyValue() {
        try{
            String key;
            this.protocol.readSpace();
            key = this.protocol.readSpace();
            if(key.equals(space))
                this.protocol.error("501 - Malformed command");
            else if(exstInHashMap(key)) {
                deltInHashMap(key);
                this.protocol.delete(key);
            }
            else{
                this.protocol.error("510 - The key is not saved in the DB");
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
            if(key.equals(space))
                this.protocol.error("501 - Malformed command");
            else if(this.exstInHashMap(key)) {
                value = getInHashMap(key);
                this.protocol.get(key, value);
            }
            else{
                this.protocol.error("503 - Key not found. Try SETT "+key+" value");
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
            if(key.equals(space))
                this.protocol.error("501 - Malformed command");
            else {
                this.protocol.readSpace();
                value = this.protocol.getValue();

                if (exstInHashMap(key)) {
                    this.protocol.error("503 - key already exists. Try UPDT " + key + " " + value);
                } else {
                    setInHashMap(key, value);
                    this.protocol.set(key, value);
                }
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
            if(key.equals(space))
                this.protocol.error("501 - Malformed command");
            else {
                this.protocol.readSpace();
                value = this.protocol.getValue();

                if (exstInHashMap(key)) {
                    updtInHashMap(key, value);
                    this.protocol.update(key, value);
                } else {
                    this.protocol.error("503 - Key not found. Try SETT " + key + " " + value);
                }
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

    private void setInHashMap(String k, String v){
        this.map.put(k,v);
    }

    private  void updtInHashMap(String k, String v){
        this.map.replace(k,v);
    }

    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT, DEFA }
}
