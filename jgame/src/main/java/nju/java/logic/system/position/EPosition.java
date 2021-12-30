package nju.java.logic.system.position;

import nju.java.logic.element.Element;

public class EPosition extends Position{

    private Element owner = null;

    public EPosition(int x, int y){
        super(x, y);
    }

    public Boolean getOccupied(){return owner != null;}

    public Element getOwner(){return owner;}

    public synchronized Element tryOccupy(Element owner){
        if(this.owner == null || this.owner == owner){
            this.owner = owner;
        }
        return this.owner;
    }

    public synchronized void release(Element owner){
        if(this.owner!=null && this.owner==owner){
            this.owner = null;
        }
    }

}
