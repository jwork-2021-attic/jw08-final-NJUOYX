package nju.java.logic.system.Factory;

import nju.java.logic.element.Brother;
import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class MultiplayerCreator extends RoundCreator{
    public MultiplayerCreator(Properties properties, GAPI gapi) {
        super(properties, gapi);
    }

    @Override
    public void run(){
        barrierInit().start();
        playersInit().forEach(player->player.start());
    }

    private List<Element> playersInit(){
        List<Element>res = new LinkedList<>();
        int playerNum = Integer.valueOf(properties.getProperty("player_num"));
        for(int i = 0;i<playerNum;++i) {
            String brotherFile = properties.getProperty("player");
            Brother player = (Brother) ElementFactory.getElement(brotherFile);
            String playerId = "player"+i;
            String strX = properties.getProperty(playerId+"_x");
            String strY = properties.getProperty(playerId+"_y");
            player.setX(Integer.parseInt(strX));
            player.setY(Integer.parseInt(strY));
            player.setPlayerId(playerId);
            player.init(gapi);
            res.add(player);
        }
        return res;
    }
}
