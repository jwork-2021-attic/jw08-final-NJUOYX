package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;
import nju.java.logic.system.position.Position;

import java.util.List;
import java.util.Queue;

public class KingMonster extends Brother{

    private int range;

    private List<Position> route = null;

    public void setRange(int range){this.range = range;}

    /**
     * set attack direction
     * @param tx
     * @param ty
     * @return whether in the range of attack
     */
    private Boolean setTargetDirection(int tx, int ty){
        if(tx == x && Math.abs(ty-y)<range){
            direction = (ty>y)?"down":"up";
            return true;
        }else if(ty ==y && Math.abs(tx-x)<range){
            direction = (tx>x)?"right":"left";
            return true;
        }else{
            return false;
        }
    }

    private void strategy(){
        Brother brother = (Brother) GAPI.getElement("brother");
        if(brother == null){
            return ;
        }
        int nx = brother.getX();
        int ny = brother.getY();
        while(route == null || route.isEmpty() || route.get(route.size()-1).x != nx || route.get(route.size()-1).y != ny){
            route = GAPI.getRoute(x, y, nx, ny);
        }

        if(setTargetDirection(nx, ny)){
            attack();
        }
        Position p = route.get(0);
        route.remove(0);
        moveTo(p.x, p.y);
    }

    @Override
    public void activeProcessor() {
        strategy();
    }

    @Override
    public void process(Operation operation){
        if(operation instanceof Attack){
            hp--;
            if(hp>0){
                attackedDisplay();
            }else{
                interrupt();
            }
        }
    }

    @Override
    public String toString(){
        return "monster_king"+getId();
    }

    @Override
    public void frameSleep(){
        eSleep(250);
    }
}
