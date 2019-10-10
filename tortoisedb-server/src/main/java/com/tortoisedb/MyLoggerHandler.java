package com.tortoisedb;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLoggerHandler {
    private Logger logger;
    private static FileHandler file;

    public MyLoggerHandler(String logName) throws IOException {
        logName = logName.isEmpty() ? this.getClass().getName() : logName;
        String fileRute = "log/clientLog/" + logName + ".log";
        this.logger = Logger.getLogger(logName);

        file = new FileHandler(fileRute, true);
        this.logger.addHandler(file);
        this.logger.setUseParentHandlers(false);

        SimpleFormatter formatter = new SimpleFormatter();
        file.setFormatter(formatter);
    }

    public void writeStringInLogFile(String message) {
        this.logger.info(message);
    }

    public void close() {
        file.close();
    }
}
