package nju.java.logic.system;

import nju.java.logic.element.Element;
import nju.java.logic.element.GAPI;
import nju.java.logic.system.Factory.MultiplayerCreator;
import nju.java.logic.system.Factory.RoundCreator;
import nju.java.logic.system.engine.Engine;
import nju.java.logic.system.position.EPosition;
import nju.java.logic.system.position.Position;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

class Recorder{
    private Map<String, Map<String,Element>> recorder;

    public Recorder(){
        recorder = new HashMap<>();
    }

    public void put(String name, Element element){
        String rootName = getRootName(name);
        Map<String, Element> subrecorder = recorder.get(rootName);
        if(subrecorder == null){
            subrecorder = new HashMap<>();
            subrecorder.put(name, element);
            recorder.put(rootName, subrecorder);
        }else{
            subrecorder.put(name, element);
        }
    }

    public Element get(String name){
        String rootName = getRootName(name);
        Map<String, Element>subrecord = recorder.get(rootName);
        if(subrecord == null){
            return null;
        }else{
            Element res = subrecord.get(name);
            if(res == null){
                for(String s: subrecord.keySet()){
                    return subrecord.get(s);
                }
                assert false;
            }
            return res;
        }
    }

    public void remove(String name){
        String rootName = getRootName(name);
        if(recorder.get(rootName) != null){
            recorder.get(rootName).remove(name);
        }
    }

    public List<Element>toList(){
        List<Element>res = new LinkedList<>();
        recorder.forEach((k,v)->{
            v.forEach((s,e)->{
                res.add(e);
            });
        });
        return res;
    }

    private String getRootName(String name){
        return name.split("_")[0];
    }
}


public class GSystem implements GAPI {

    private Engine engine;
    private String configFilePath;
    private Properties properties;
    private List<EPosition>positionList;
    private Recorder recorder = new Recorder();
    private RoundCreator roundCreator;

    /**
     * GAPI controls the whole game logic
     * @param engine engine as UI
     * @param configFilePath app config file path, in this class it is assumed to be a correct path
    */

    public GSystem(Engine engine, String configFilePath) throws IOException{
        this.engine = engine;
        this.configFilePath = configFilePath;
        initGAPI();
        initEngine();
        initEPosition();
    }

    public GSystem(){

    }

    private void initGAPI() throws IOException {
        FileInputStream fs = new FileInputStream(configFilePath);
        properties = new Properties();
        properties.loadFromXML(fs);
    }

    private void initEngine(){
        String rangeX = properties.getProperty("range_x");
        String rangeY = properties.getProperty("range_x");
        engine.resizeScreen(Integer.parseInt(rangeX),Integer.parseInt(rangeY));
    }

    private void initEPosition(){
        positionList = new ArrayList<>();
        int rangeX = engine.getRangeX();
        int rangeY = engine.getRangeY();
        for(int x = 0;x<rangeX;++x){
            for(int y = 0;y<rangeY;++y){
                positionList.add(new EPosition(x, y));
            }
        }
    }

    public void run(){
        //roundCreator = new RoundCreator(properties,this);
        roundCreator = new MultiplayerCreator(properties, this);
        roundCreator.start();
        //do{
        //    eSleep(400);
        //}while(runningCheck());
        //recorder.toList().forEach(e->e.interrupt());
    }

    private Boolean runningCheck(){
        Element e = getElement("brother");
        if(e == null){
            engine.logOut(-1, "Lost!");
            return false;
        }
        if(roundCreator.isEmpty()) {
            e = getElement("monster");
            if (e == null) {
                engine.logOut(-1, "Success!");
                return false;
            }
        }
        return true;
    }

    private void eSleep(int milliseconds){
        try{
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Position>getRoute(int x, int y, int nx, int ny){
        List<Position>res = new LinkedList<>();

        if(x == nx && y == ny){res.add(new Position(nx, ny));return res;}

        List<int[]>direction = new LinkedList<>();
        if(nx > x){
            direction.add(new int[]{1,0});
        }else if(nx < x){
            direction.add(new int[]{-1,0});
        }
        if(ny > y){
            direction.add(new int[]{0,1});
        }else if(ny < y){
            direction.add(new int[]{0,-1});
        }

        Stack<int[]>stack = new Stack<>();
        stack.add(new int[]{x,y,0});
        while(!stack.isEmpty()){
            int[]top = stack.pop();
            int cx = top[0];
            int cy = top[1];
            int i = top[2];
            while(i<direction.size()){
                int x0 = cx + direction.get(i)[0];
                if(Math.abs(x0-nx)>Math.abs(cx-nx)){ i++; continue;}

                int y0 = cy + direction.get(i)[1];
                if(Math.abs(y0-ny)>Math.abs(cy-ny)){i++; continue;}

                Position p = new Position(x0, y0);
                int index = positionList.indexOf(p);
                if(index<0){continue;}
                EPosition ep = positionList.get(index);
                if((x0 == nx && y0 == ny) ||  ep.getOwner()==null){
                    stack.add(new int[]{cx, cy, i+1});
                    stack.add(new int[]{x0, y0, 0});
                    break;
                }
                i++;
            }
            if(stack.isEmpty()){return null;}
            if(stack.peek()[0] == nx && stack.peek()[1] == ny){
                stack.forEach(s->{
                    res.add(new Position(s[0],s[1]));
                });
                res.remove(0);
                return res;
            }

        }
        return null;
    }

    @Override
    public void display(int x, int y, char character, Color color, Boolean visible) {
        engine.display(x, y, character, color, visible);
    }

    @Override
    public Element tryOccupy(int x, int y, Element requester) {
        int index = positionList.indexOf(new Position(x,y));
        return positionList.get(index).tryOccupy(requester);
    }

    @Override
    public Element exist(int x, int y) {
        int index = positionList.indexOf(new Position(x,y));
        return positionList.get(index).getOwner();
    }

    @Override
    public void release(int x, int y, Element element) {
        int index = positionList.indexOf(new Position(x,y));
        positionList.get(index).release(element);
    }

    @Override
    public String getInput() {
        return engine.getInput();
    }

    @Override
    public String getInput(String playerId){
        return engine.getInput(playerId);
    }

    @Override
    public Element getElement(String name){
        return recorder.get(name);
    }

    @Override
    public void register(String name, Element element) {
        recorder.put(name,element);
    }

    @Override
    public void unregister(String name, Element element) {
        Element e = recorder.get(name);
        if(e == element){
            recorder.remove(name);
        }
    }

    @Override
    public void logOut(int index, String log){
        engine.logOut(index, log);
    }

    @Override
    public void logOut(int index, String log, String player){
        engine.logOut(index, log, player);
    }
}
