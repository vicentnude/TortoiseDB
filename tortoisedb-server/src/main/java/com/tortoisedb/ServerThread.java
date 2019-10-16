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
            String command = logicServer.protocol.getCommand();
            System.out.println(command);
            switch(command){
                case "STRT":
                    this.logicServer.protocol.readSpace();
                    String clientUser = logicServer.protocol.readSocket(); //Search if user exists
                    System.out.println(clientUser);
                    logicServer.protocol.start();
                case "DELT":
                    this.deleteKeyValue();
                    break;
                case "EXST":
                    this.checkIfExistKeyvalue();
                    break;
                case "GETT":
                    this.getHashMapValue();
                    break;
                case "SETT":
                    this.setValueAndKeyInHashMap();
                    break;
                case "UPDT":
                    this.updateValueUsingKey();
                    break;
                default:
                    System.out.println("ERROR: COMMAND IS NOT RECOGNISED");
                    break;
            }
        } catch (IOException ex) {
            System.err.println("Can't read socketBuffer: " + ex.getMessage());
        }
    }

    private void deleteKeyValue() {
        try{
            deltInHashMap(this.logicServer.protocol.getKey());
        }catch (Exception e) {
            //TODO: send error message saying it cant delete key
        }
    }

    private void checkIfExistKeyvalue() throws IOException {
        boolean existKeyValue;
        String key, value;

        key     = this.logicServer.protocol.getKey();
        value   = this.logicServer.protocol.getValue();

        try{
            existKeyValue = exstInHashMap(key, value);
            this.logicServer.protocol.writeBoolean(existKeyValue);
        }catch (Exception e) {
            //TODO: send error message saying it cant do it using this key
        }
    }

    private void getHashMapValue() throws IOException {
        String key, value;

        key = this.logicServer.protocol.getKey();

        try{
            value = getInHashMap(key);
            this.logicServer.protocol.writeValue(value);
        }catch (Exception e) {
            //TODO: send error message
        }
    }

    private void setValueAndKeyInHashMap() throws IOException {
        String key, value;

        try {
            value   = this.logicServer.protocol.getValue();
            key     = this.logicServer.protocol.getKey();
            setInHashMap(key, value);
        }catch (Exception e) {
            //TODO: send error message saying cant save value using key
        }
    }

    private void updateValueUsingKey() throws IOException {
        String key, value;

        try{
            value   = this.logicServer.protocol.getValue();
            key     = this.logicServer.protocol.getKey();
            updtInHashMap(key, value);
        }catch (Exception e){
            //TODO: send error message saying that the value cant be updated.
        }
    }

    private void deltInHashMap(String k){
        this.map.remove(k);
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
}
