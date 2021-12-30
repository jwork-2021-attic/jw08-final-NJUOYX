package nju.java.logic.system.engine;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import nju.java.logic.system.engine.utils.Log;
import nju.java.logic.system.engine.utils.OData;
import nju.java.logic.system.engine.utils.WebIO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Timer;

public class ClientEngine extends JFrame implements KeyListener {

    class Loader{
        Queue<OData>inputs;
        Queue<OData>timerInputs;
        Loader(String loadFile)throws IOException, ClassNotFoundException{
            this.inputs = new LinkedList<>();
            this.timerInputs = new LinkedList<>();
            try(ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(loadFile))){
                while(true){
                    inputs.add((OData) objectInputStream.readObject());
                }
            }catch (ClassNotFoundException e){
                throw e;
            }catch (EOFException e){
                //setTimerInputs();
                return ;
            }catch (StreamCorruptedException e){
                //setTimerInputs();
                return;
            }catch (IOException e){
                throw e;
            }
        }
        Boolean finish(){
            return this.inputs.isEmpty() && this.timerInputs.isEmpty();
        }

        String getInput(String player){
            OData oData = timerInputs.poll();
            if(oData==null){
                return null;
            }else if(oData.name.equalsIgnoreCase(player)){
                return oData.input;
            }else{
                return null;
            }
        }

        void timerHandlerAction(ClientEngine clientEngine){
            Timer timer = new Timer();
            while(!this.inputs.isEmpty()) {
                OData oData = this.inputs.poll();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(clientEngine.playerName.equalsIgnoreCase(oData.name)) {
                            clientEngine.sendInput(oData.input);
                        }
                    }
                }, oData.triggerTime);
            }
        }

        void setTimerInputs(){
            Timer timer = new Timer();
            while(!this.inputs.isEmpty()) {
                OData oData = this.inputs.poll();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        timerInputs.add(oData);
                    }
                }, oData.triggerTime);
            }
        }
    }

    private Socket client;
    private String playerName;
    private int rangeY;
    private int rangeX;

    private AsciiPanel terminal;

    private Boolean isReplay = false;

    private final int logXLen = 25;
    private final int logYLen = 3;
    private final int logXGap = 2;
    private final int logYGap = 1;

    public ClientEngine(String serverName, int port ,String playerName)throws IOException{
        this.playerName = playerName;
        this.terminal = null;
        this.client = new Socket(serverName, port);
        Log.logOut("connected to server"+this.client);
        setVisible(true);
    }

    public void run()throws IOException{
        this.isReplay = false;
        register(this.playerName);
        receiver();
    }

    public void replay(String loadFile)throws IOException,ClassNotFoundException{
        this.isReplay = true;
        register(this.playerName);
        Loader loader = new Loader(loadFile);
        loader.timerHandlerAction(this);
        receiver();
    }

    private void keyPressedRp(Loader loader){
        Log.logOut("key press init");
        while(true) {
            String input = loader.getInput(this.playerName);
            Log.logOut("key input " + input);
            sendInput(input);
        }
        //Log.logOut("key press finish");
    }

    private void register(String playerName)throws IOException{
        Properties properties = new Properties();
        properties.setProperty("player",playerName);
        WebIO.write(properties,this.client);
        Log.logOut("registered!");
    }

    private void resizeScreen(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.terminal = new AsciiPanel(rangeX+ logXLen, rangeY, AsciiFont.TALRYTH_15_15);
        add(this.terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    private void display(int x, int y, char character, Color color, Boolean visible) {
        this.terminal.write(character,x,y,visible?color:Color.BLACK);
        repaint();
    }

    private void logOut(int logIndex, String log){
        int totalRows = rangeY/logYLen;
        if(logIndex<0){
            logIndex = totalRows+logIndex;
        }
        int cursorX = rangeX + logXGap;
        int cursorY = logIndex * logYLen+logYGap;
        terminal.clear((char)0, rangeX, cursorY, logXLen,logYLen);
        terminal.write(log, cursorX, cursorY);
        repaint();
    }


    private void solveCommand(Properties properties)throws IOException{
        String function = properties.getProperty("function");
        switch (function){
            case "resizeScreen":{
                int rangeX = Integer.valueOf(properties.getProperty("arg0"));
                int rangeY = Integer.valueOf(properties.getProperty("arg1"));
                resizeScreen(rangeX, rangeY);
            }break;
            case "display":{
                int x = Integer.valueOf(properties.getProperty("arg0"));
                int y = Integer.valueOf(properties.getProperty("arg1"));
                int character = Integer.valueOf(properties.getProperty("arg2"));
                Color color = new Color(Integer.valueOf(properties.getProperty("arg3")));
                Boolean visible = Boolean.valueOf(properties.getProperty("arg4"));
                display(x, y, (char)character, color, visible);
            }break;
            case "logOut":{
                int index = Integer.valueOf(properties.getProperty("arg0"));
                String log = properties.getProperty("arg1");
                logOut(index, log);
            }
            default:break;
        }
    }

    private void receiver()throws IOException{
        while(true){
            Properties properties = WebIO.read(this.client);
            solveCommand(properties);
        }
    }


    private String resolveKeyCode(int keyCode){
        String res = null;
        switch (keyCode) {
            case KeyEvent.VK_UP:
                res = "up";
                break;
            case KeyEvent.VK_DOWN:
                res = "down";
                break;
            case KeyEvent.VK_LEFT:
                res = "left";
                break;
            case KeyEvent.VK_RIGHT:
                res = "right";
                break;
            case KeyEvent.VK_SPACE:
                res = "attack";
                break;
            case KeyEvent.VK_C:
                res = "change_weapon";
                break;
            default:
                break;
        }
        return res;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    private void sendInput(String input){
        if(input!=null) {
            Properties properties = new Properties();
            properties.setProperty("input",input);
            try {
                WebIO.write(properties, this.client);
                Log.logOut("send string: "+ input);
            }catch (IOException err){
                err.printStackTrace();
            }
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        if(!this.isReplay){
            String res = resolveKeyCode(e.getKeyCode());
            sendInput(res);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
