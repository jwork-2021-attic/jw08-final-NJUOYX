package nju.java.logic.element;

import nju.java.logic.element.opration.Attack;
import nju.java.logic.element.opration.Operation;
import nju.java.logic.element.opration.ToBrotherAttack;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Monster extends ActiveElement {

    protected void attack(Element target) {
        target.submit(new Attack());
    }

    /**
     *
     * @return four possible position and the first is closer to brother
     */
    protected int[] moveStrategy() {
        Brother brother = (Brother) GAPI.getElement("brother");
        if(brother == null){
            return new int[]{x,y};
        }
        int bx = brother.getX();
        int by = brother.getY();
        int [][]direction = new int [][]{{1,0},{-1,0},{0,-1},{0,1}};
        List<int[]>result = new LinkedList<>();
        for(int i = 0;i< direction.length;++i){
            int nx = x + direction[i][0];
            int ny = y + direction[i][1];
            if(Math.abs(nx-bx)+Math.abs(ny-by) < Math.abs(x-bx)+ Math.abs(y-by)){
                result.add(new int[]{nx, ny});
            }
        }
        int len = result.size();
        Random random = new Random();
        return result.get(random.nextInt(len));
    }


    protected void strategy() {
        int[] np = moveStrategy();
        Element element = moveTo(np[0],np[1]);
        if(element != this){
            element.submit(new ToBrotherAttack());
        }
    }


    @Override
    public void process(Operation operation) {
        if (operation instanceof Attack) {
            interrupt();
        }
    }

    @Override
    public void activeProcessor() {
        strategy();
    }

    @Override
    public void frameSleep() {
        eSleep(250);
    }

    @Override
    public String toString() {
        return "monster_" + getId();
    }
}
