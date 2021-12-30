package nju.java.logic.element;

import java.awt.Color;

public abstract class ActiveElement extends PassiveElement {

    protected int x;
    protected int y;
    protected char character;
    protected Color color;

    protected String direction = "up";

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public void setColor(int[] color) {
        assert (color.length == 3);
        this.color = new Color(color[0], color[1], color[2]);
    }

    @Override
    public Boolean init(GAPI GAPI) {
        super.init(GAPI);
        Element e = GAPI.tryOccupy(x, y, this);
        if(e != this){
            return false;
        }
        GAPI.register(toString(), this);
        GAPI.display(x, y, character, color, true);
        return true;
    }

    @Override
    public void interrupt() {
        GAPI.unregister(toString(), this);
        GAPI.release(x, y, this);
        GAPI.display(x, y, character, color, false);
        super.interrupt();
    }

    class ActiveProcessor implements Runnable {
        ActiveElement activeElement;
        public ActiveProcessor(ActiveElement activeElement) {
            this.activeElement = activeElement;
            setName(toString());
        }
        @Override
        public void run(){
            while(activeElement.isRunning()) {
                activeElement.activeProcessor();
                frameSleep();
            }
        }
        @Override
        public String toString(){
            return activeElement.toString() + ": active_thread";
        }
    }

    @Override
    public void run() {
        new Thread(new ActiveProcessor(this)).start();
        super.run();
    }

    /**
     * move a pixiel to (nx, ny)
     * @param nx
     * @param ny
     * @return caller if move operation succeed, otherwise return the current occupier
     */
    protected Element moveTo(int nx, int ny) {
        Element e = GAPI.tryOccupy(nx, ny, this);
        if (e == this) {
            GAPI.release(x, y, this);
            GAPI.display(x, y, character, color, false);
            x = nx;
            y = ny;
            GAPI.display(x, y, character, color, true);
        }
        return e;
    }

    protected void attackedDisplay(){
        new Thread(()->{
            GAPI.display(x, y, character,color, false);
            eSleep(50);
            GAPI.display(x, y, character, color, true);
        }).start();
    }

    protected abstract void activeProcessor();

    protected abstract void frameSleep();


}
