package nju.java;

import nju.java.logic.system.GSystem;
import nju.java.logic.system.engine.ServerEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Server {

    public static void main(String args[]) {
        final String configFile = "config.xml";

        try {
            Properties properties = new Properties();
            properties.loadFromXML(new FileInputStream(configFile));
            ServerEngine engine = new ServerEngine(33006);
            engine.run(Integer.valueOf(properties.getProperty("player_num")));
            new GSystem(engine, configFile).run();
        }catch (IOException e){
            System.err.println("config.xml is broken!");
            e.printStackTrace();
        }
    }
}
