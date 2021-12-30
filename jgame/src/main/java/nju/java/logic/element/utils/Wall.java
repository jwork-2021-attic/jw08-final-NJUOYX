package nju.java.logic.element.utils;

import nju.java.logic.element.GAPI;
import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;

public class Wall extends Util{

    private int hp = 5;

    public Wall(int x, int y, String sdir, GAPI GAPI){
        super(x, y, sdir+"-left", GAPI);
        this.x = x + dir[0];
        this.y = y + dir[1];
        setCharacter((char)177);
        setColor(new int[]{255, 0, 255});
        running = init(GAPI);
    }

    @Override
    protected void activeProcessor() {
    }

    @Override
    protected void frameSleep() {
        eSleep(1000);
    }

    @Override
    public String toString(){
        return "Wall"+getId();
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            hp--;
            if(hp<0) {
                interrupt();
            }
        }
    }
}
