package nju.java;

import nju.java.logic.system.engine.ClientEngine;

import java.io.IOException;

public class Rep1 {
    public static void main(String[]strings){
        final String loadFile =  "save_1640832530420.save";
        try {
            ClientEngine clientEngine = new ClientEngine("localhost", 33006, "player1");
            clientEngine.replay(loadFile);
        }catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
