package com.tortoisedb;

import com.tortoisedb.InteractionLogicServer;
import com.tortoisedb.Protocol;
import com.tortoisedb.SocketBuffer;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerThread implements Runnable {

    private Map<String, String> map;
    //private Protocol protocol;
    private Socket socket;
    private SocketBuffer socketBuffer;
    private InteractionLogicServer logicServer;
    private MyLoggerHandler clientLogger;
    private State state;
    private boolean isRunning;

    /**
     * Constructor, creates a new protocol object
     * @param socket the socket used in the communication
     * @throws IOException error creating the socket.
     */
    public ServerThread(Socket socket) throws IOException {
        this.map            = new ConcurrentHashMap<>();
        this.socket         = socket;
        //this.clientLogger   = new MyLoggerHandler(this.getClass().getName());
        this.logicServer    = new InteractionLogicServer(socket, this.clientLogger);
        this.state          = State.STRT;
        isRunning = true;

        if(this.checkIfNeedsToReadData()) {
            this.readDataFromFile();
        }
    }

    private boolean checkIfNeedsToReadData() {
        return true;
    }

    private void readDataFromFile() {

    }

    @Override
    public void run() {
        try {
            while(this.isRunning) {
                state = checkCommand(logicServer.protocol.getCommand());
                if (state == null) {
                    state = State.DEFA;
                }
                System.out.println(state);
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
                        break;
                    case DEFA:
                        System.out.println("Something went wrong");
                        break;
                    default:
                        System.out.println("ERROR: COMMAND IS NOT RECOGNISED");
                        break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }

    private void deleteKeyValue() {
        try{
            this.logicServer.protocol.readSpace();
            deltInHashMap(this.logicServer.protocol.readSpace());
        }catch (Exception e) {
            //TODO: send error message saying it cant delete key
        }
    }

    private void checkIfExistKeyvalue() throws IOException {
        boolean existKeyValue;
        String key, value;

        this.logicServer.protocol.readSpace();
        key     = this.logicServer.protocol.readSpace();
        //this.logicServer.protocol.readSpace();
        //value   = this.logicServer.protocol.getValue();

        try{
            existKeyValue = exstInHashMap(key, "notImportant"); //Have to clean code
            System.out.println(existKeyValue);
            //this.logicServer.protocol.writeBoolean(existKeyValue);
        }catch (Exception e) {
            //TODO: send error message saying it cant do it using this key
            //should return if exists
        }
    }

    private void getHashMapValue() throws IOException {
        String key, value;

        this.logicServer.protocol.readSpace();
        key = this.logicServer.protocol.readSpace();

        try{
            value = getInHashMap(key);
            this.logicServer.protocol.writeValue(value);
            System.out.println(value);
        }catch (Exception e) {
            //TODO: send error message
            //should return the value of the key or null if not existed
        }
    }

    private void setValueAndKeyInHashMap() throws IOException {
        String key, value;

        try {
            this.logicServer.protocol.readSpace();
            key   = this.logicServer.protocol.readSpace();
            this.logicServer.protocol.readSpace();
            value     = this.logicServer.protocol.getValue();
            setInHashMap(key, value);
        }catch (Exception e) {
            //TODO: send error message saying cant save value using key
            //should return if value existed or not. so sett or update
        }
    }

    private void updateValueUsingKey() throws IOException {
        String key, value;
        //update not working in the database
        try{
            this.logicServer.protocol.readSpace();
            key     = this.logicServer.protocol.readSpace();
            this.logicServer.protocol.readSpace();
            value   = this.logicServer.protocol.getValue();
            updtInHashMap(key, value);
        }catch (Exception e){
            //TODO: send error message saying that the value cant be updated.
            //should return if the value got correctly updated
        }
    }

    private void deltInHashMap(String k){
        this.map.remove(k);
        //should return Done or not found to the client
    }

    private boolean exstInHashMap(String k, String v){
        return this.map.containsKey(k);//hm.containsValue(v);
    }

    private String getInHashMap(String k){
        return this.map.get(k);
    }

    private void setInHashMap(String k, String v){
        this.map.put(k,v);
    }

    private  void updtInHashMap(String k, String v){
        this.map.replace(k,v);
    }

    public State checkCommand(String command){
        for(State s:State.values()){
            if(s.name().equals(command))
                return s;
        }
        return null;
    }

    private void readSTRT(){
        try {
            this.logicServer.protocol.readSpace();
            String clientUser = logicServer.protocol.readSocket(); //Search if user exists
            System.out.println("user:" + clientUser);
            logicServer.protocol.start();
        }
        catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }
    private enum State{ STRT, SETT, GETT, DELT, UPDT, EXST, EXIT,DEFA }
}
