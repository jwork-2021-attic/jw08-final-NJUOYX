package nju.java.logic.element;

import java.awt.Color;

public class Barrier extends PassiveElement {
    private int[][] barriers;

    private char character;

    private Color color;

    public void setBarriers(int[][] barriers) {
        this.barriers = barriers;
    }

    public void setColor(int[] color) {
        this.color = new Color(color[0], color[1], color[2]);
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    @Override
    public Boolean init(GAPI GAPI) {
        super.init(GAPI);
        for (int i = 0; i < barriers.length; i++) {
            Element e = GAPI.tryOccupy(barriers[i][0], barriers[i][1], this);
            assert (e == this);
            GAPI.display(barriers[i][0], barriers[i][1], character, color, true);
        }
        GAPI.register(toString(),this);
        return true;
    }

    @Override
    public void interrupt(){
        super.interrupt();
        for(int i = 0; i < barriers.length; i++) {
            GAPI.release(barriers[i][0], barriers[i][1], this);
            GAPI.display(barriers[i][0], barriers[i][1],character, color, false);
        }
    }

    @Override
    public String toString(){
        return "barrier_"+getId();
    }
}
