package nju.java.logic.element.utils;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.element.opration.Attack;

public class Mine extends Util{

    public Mine(int x, int y, String sdir, GAPI GAPI){
        super(x, y, sdir+"-left", GAPI);
        this.x = x+dir[0];
        this.y = y+dir[1];
        setCharacter((char)4);
        setColor(new int[]{255,0,0});
        init(GAPI);
        GAPI.release(this.x, this.y, this);

        if(GAPI.exist(this.x, this.y) == null){
            GAPI.display(this.x, this.y,this.character,this.color,true);
            running = true;
        }else{
            running = false;
        }
    }

    @Override
    protected void activeProcessor() {
        Element e = GAPI.exist(x,y);
        if(e != null){
            exposeDisplay(x, y);
            e.submit(new Attack());
            interrupt();
        }
    }

    @Override
    protected void frameSleep() {
        eSleep(10);
    }

}
