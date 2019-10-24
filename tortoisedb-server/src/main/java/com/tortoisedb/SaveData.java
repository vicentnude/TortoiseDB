package com.tortoisedb;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SaveData {

    private File file;
    private Map<String, String> map;
    //Write
    private FileWriter writer;
    //Read
    private BufferedReader reader;

    public SaveData(){



    }

    public void writeDataToFile(String path, Map<String, String> data) throws IOException {
        file = new File(path);
        file.createNewFile();

        writer = new FileWriter(file);


        //Save hashmap values
        //writer.append()

        writer.flush();
        writer.close();
    }

    public void readDataFromFile(String path) throws IOException {
        map = new ConcurrentHashMap<>();
        String row;

        file = new File(path);
        if (file.isFile()){
            reader = new BufferedReader(new FileReader(path));
            while ((row = reader.readLine()) != null){
                String[] data = row.split(",");

                for (String value: data){
                    //Put values
                }

            }
        }else{
            //TODO Handle file does not exist
        }



    }

}
