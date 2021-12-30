package nju.java;

import nju.java.logic.system.engine.ClientEngine;

import java.io.IOException;

public class GClient {
    public static void main(String[]strings){
        try {
            ClientEngine clientEngine = new ClientEngine("localhost", 33006, "player0");
            clientEngine.run();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
