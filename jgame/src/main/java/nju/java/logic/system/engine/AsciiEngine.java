package nju.java.logic.system.engine;
/*
import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import nju.java.logic.system.engine.utils.OData;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class AsciiEngine extends JFrame implements Engine, KeyListener {
    private AsciiPanel terminal;
    private int rangeX;
    private int rangeY;
    private final int logXLen = 25;
    private final int logYLen = 3;
    private final int logXGap = 2;
    private final int logYGap = 1;

    private String file;
    private Boolean load;

    private long startTime;

    private Queue<Integer>press_input_queue;

    private FileOutputStream fileOutputStream;
    private ObjectOutputStream objectOutputStream;

    public static AsciiEngine createAsciiEngine(){
        return new AsciiEngine(randomFile("gameSave","dt"),false);
    }

    public static AsciiEngine createAsciiEngine(String file, Boolean load){
        return new AsciiEngine(file, load);
    }

    private AsciiEngine(String file, Boolean load){
        super();
        this.startTime = System.currentTimeMillis();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        rangeX = logXLen;
        rangeY = logYLen;
        terminal = new AsciiPanel(rangeX, rangeY, AsciiFont.TALRYTH_15_15);
        this.load = load;
        this.file = file;
        press_input_queue = new LinkedList<>();

        if(this.load){
            triggerInput(loadInput());
        }else{
            initSave();
        }

    }

    private static String randomFile(String prefix,String suffix){
        return prefix + System.currentTimeMillis() +"."+suffix;
    }

    @Override
    public void resizeScreen(int rangeX, int rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        terminal = new AsciiPanel(rangeX+ logXLen, rangeY, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        addKeyListener(this);
        repaint();
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        terminal.write(character,x,y,visible?color:Color.BLACK);
        repaint();
    }

    @Override
    public void logOut(int logIndex, String log){
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

    @Override
    public void logOut(int logIndex, String log, String player) {

    }

    @Override
    public int getRangeX() {
        return rangeX;
    }

    @Override
    public int getRangeY() {
        return rangeY;
    }

    private void initSave(){
        try{
            this.fileOutputStream = new FileOutputStream(this.file, true);
            this.objectOutputStream = new ObjectOutputStream(fileOutputStream);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void saveInput(int keyCode){
        try {
            objectOutputStream.writeObject(new OData(System.currentTimeMillis() - this.startTime, keyCode));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private Queue<OData> loadInput(){
        Queue<OData> res = new LinkedList<>();
        try(FileInputStream fileInputStream = new FileInputStream(this.file)){
            try(ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
                 while(true){
                     res.add((OData) objectInputStream.readObject());
                 }
            }
        }catch (StreamCorruptedException e){
          return res;
        } catch (EOFException e) {
            return res;
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        return res;
    }

    private void triggerInput(Queue<OData> oDataQueue){
        Timer timer = new Timer();
        while(!oDataQueue.isEmpty()){
            OData odata = oDataQueue.poll();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    press_input_queue.add(odata.keyCode);
                }
            },odata.triggerTime);
        }
    }

    @Override
    public String getInput(String player){return getInput();}

    @Override
    public String getInput() {
        Integer e = press_input_queue.poll();
        if (e != null) {
            String res;
            switch (e) {
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
                    res = null;
                    break;
            }
            return res;
        } else {
            return null;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!this.load){
            press_input_queue.add(e.getKeyCode());
            saveInput(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
*/