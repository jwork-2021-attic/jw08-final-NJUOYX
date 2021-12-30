package nju.java.logic.element.utils;

import nju.java.logic.element.GAPI;
import nju.java.logic.element.ActiveElement;

import java.awt.*;

public abstract class Util extends ActiveElement {

    protected int[] dir;
    GAPI GAPI;

    public Util(int x, int y, String sdir, GAPI GAPI) {
        this.x = x;
        this.y = y;
        this.GAPI = GAPI;
        setDir(sdir);
    }

    private void setDir(String sdir) {
        dir = new int[]{0,0};
        switch (sdir){
            case"up":dir[1] = -1;break;
            case"down":dir[1] = 1;break;
            case"left":dir[0] = -1;break;
            case"right":dir[0] = 1;break;
            case"up-left":case"left-right":dir[0] = -1;dir[1] = -1;break;
            case"up-right":case"right-left":dir[0] = 1;dir[1] = -1;break;
            case"down-left":case"left-left":dir[0] = -1;dir[1] = 1;break;
            case"down-right":case"right-right":dir[0] = 1;dir[1] = 1;break;
        }
    }

    protected void exposeDisplay(int tx , int ty){
        new Thread(()->{
            GAPI.display(tx, ty, (char)15, Color.yellow,true);
            frameSleep();
            GAPI.display(tx, ty, (char)0, null, false);
        }).start();
    }
}
