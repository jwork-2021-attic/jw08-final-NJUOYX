package nju.java.logic.system.engine.utils;

import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Player implements Runnable{
    private Socket socket;
    private String name;
    private Queue<String>inputs;

    public Player(Socket socket, String name){
        this.socket = socket;
        this.name = name;
        this.inputs = new ConcurrentLinkedQueue<>();
    }

    public String getInput() {
        return inputs.poll();
    }

    public void send(Properties properties)throws IOException{
        WebIO.write(properties,this.socket);
    }

    @Override
    public void run(){
        while(this.socket.isConnected()){
            try {
                Properties properties = WebIO.read(this.socket);
                String input = properties.getProperty("input");
                if(input!=null){
                    Log.logOut(this.name+"getInput: "+input);
                    this.inputs.add(input);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public Boolean identify(String name){
        return this.name.equalsIgnoreCase(name);
    }
}
