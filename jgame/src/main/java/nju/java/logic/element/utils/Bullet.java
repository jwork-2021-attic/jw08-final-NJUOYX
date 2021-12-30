package nju.java.logic.element.utils;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.element.opration.Attack;

public class Bullet extends Util {

    public Bullet(int x, int y, String sdir, GAPI GAPI) {
        super(x, y, sdir, GAPI);
        //These should be better
        this.character = 7;
        setColor(new int[]{255, 255, 255});


        this.x = x + dir[0];
        this.y = y + dir[1];
        Element res = GAPI.exist(this.x, this.y);
        if (res == null) {
            init(GAPI);
        } else {
            res.submit(new Attack());
            running = false;
        }

    }


    @Override
    public void activeProcessor() {
        int nx = x + dir[0];
        int ny = y + dir[1];
        Element res = GAPI.exist(nx, ny);
        if (res == null) {
            moveTo(nx, ny);
        } else {
            exposeDisplay(x, y);
            res.submit(new Attack());
            interrupt();
        }
    }

    @Override
    public void passiveProcessor() {
        await();
    }

    @Override
    public void frameSleep() {
        eSleep(50);
    }

    @Override
    public String toString(){
        return "Bullet" + getId();
    }
}
