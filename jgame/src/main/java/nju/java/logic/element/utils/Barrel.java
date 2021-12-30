package nju.java.logic.element.utils;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;

public class Barrel extends Util{

    public Barrel(int x ,int y, String sdir, GAPI GAPI){
        super(x, y, sdir+"-left", GAPI);
        this.x = x + dir[0];
        this.y = y + dir[1];
        setCharacter((char)10);
        setColor(new int[]{100, 0, 0});
        running = init(GAPI);
    }

    @Override
    protected void activeProcessor() {

    }

    @Override
    protected void frameSleep() {
        eSleep(200);
    }

    @Override
    public String toString(){
        return "Barrel"+getId();
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            exposeDisplay(x, y);
            int cx = x;
            int cy = y;
            expose(cx,cy+1);
            expose(cx,cy-1);
            expose(cx+1,cy+1);
            expose(cx+1,cy);
            expose(cx+1,cy-1);
            expose(cx-1,cy+1);
            expose(cx-1,cy);
            expose(cx-1,cy-1);
            interrupt();
        }
    }

    private void expose(int nx , int ny){
        Element element = GAPI.exist(nx,ny);
        if(element != null){
            element.submit(new Attack());
        }
        exposeDisplay(nx, ny);
    }
}
