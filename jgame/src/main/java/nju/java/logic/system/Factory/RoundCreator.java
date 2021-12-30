package nju.java.logic.system.Factory;

import nju.java.logic.element.*;

import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class RoundCreator extends Thread{
    protected Properties properties;
    protected GAPI gapi;
    private Boolean empty = false;

    public RoundCreator(Properties properties, GAPI gapi){
        this.properties = properties;
        this.gapi = gapi;
    }

    public Boolean isEmpty(){
        return empty;
    }

    @Override
    public void run(){
        barrierInit().start();
        brotherInit().start();
        Queue<ActiveElement> monsters = monsterCreate();
        while(!monsters.isEmpty()){
            ActiveElement monster = monsters.poll();
            Boolean res = monster.init(gapi);
            if(!res){
                monsters.add(monster);
            }else{
                monster.start();
            }
            eSleep(1000);
        }
        empty = true;
    }

    private void eSleep(int milliseconds){
        try{
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    protected Element barrierInit(){
        String barrierFile = properties.getProperty("barrier");
        Element barrier = ElementFactory.getElement(barrierFile);
        barrier.init(gapi);
        return barrier;
    }

    private Element brotherInit(){
        String brotherFile = properties.getProperty("brother");
        Brother brother = (Brother)ElementFactory.getElement(brotherFile);
        String strX = properties.getProperty("brother_x");
        String strY = properties.getProperty("brother_y");
        brother.setX(Integer.parseInt(strX));
        brother.setY(Integer.parseInt(strY));
        brother.init(gapi);
        return brother;
    }

    private Queue<ActiveElement> monsterCreate(){
        String[] monsterFile = properties.getProperty("monster").split(",");
        String[] monsterNum = properties.getProperty("monster_num").split(",");
        String strX = properties.getProperty("monster_x");
        String strY = properties.getProperty("monster_y");
        int x = Integer.parseInt(strX);
        int y = Integer.parseInt(strY);
        Queue<ActiveElement> queue = new LinkedList<>();
        for(int i = 0;i<monsterFile.length;++i) {
            int mnum = Integer.parseInt(monsterNum[i]);
            for (int j = 0; j < mnum; ++j) {
                ActiveElement monster = (ActiveElement) ElementFactory.getElement(monsterFile[i]);
                monster.setX(x);
                monster.setY(y);
                queue.add(monster);
            }
        }
        return queue;
    }

}
