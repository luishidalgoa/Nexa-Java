package com.luishidalgoa.Nexa.Utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


import java.util.logging.Level;
public class Logger {
    public static java.util.logging.Logger CreateLogger(String filename) {
        java.util.logging.Logger logger= java.util.logging.Logger.getLogger(filename);
        logger.setLevel(Level.ALL);
        CreateFile(logger,filename);
        return logger;
    }
    public static void CreateFile(java.util.logging.Logger logger, String filename){
        FileHandler fileHandler=null;
        try{
            fileHandler = new FileHandler("logs\\"+filename+ ".txt");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        }catch (SecurityException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        logger.addHandler(fileHandler);
    }
}

