package com.company;

import java.util.logging.*;

/**
 * Clasa urmatoare creeaza un logger care va scrie in fisierul creeat de catre constructor exceptia care va fi primita de catre metoda LogException
 */
public class MyLogger {
    private final Logger logger;

    public MyLogger(Logger logger) {
        this.logger = logger;
        try{
            FileHandler fh= new FileHandler("DataBaseLogFile.txt",true);
            SimpleFormatter formatter=new SimpleFormatter();
            fh.setFormatter(formatter);
            fh.setLevel(Level.FINE);
            logger.addHandler(fh);
            logger.setLevel(Level.FINE);
        }catch (Exception e){
            logger.log(Level.INFO,"Exception::",e);
        }
    }

    public void LogException(Exception e) {
        logger.log(Level.FINE,"Exception::",e);
    }




}







