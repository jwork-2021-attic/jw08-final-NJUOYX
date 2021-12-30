package nju.java.logic.element;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

import nju.java.logic.element.opration.Operation;

public abstract class Element extends Thread{

    protected Boolean running;

    protected GAPI GAPI;

    protected Queue<Operation> operations = new ConcurrentLinkedQueue<>();

    public Boolean init(GAPI GAPI) {
        running = true;
        this.GAPI = GAPI;
        setName(toString());
        return true;
    }

    public Boolean isRunning() {
        return running;
    }

    @Override
    public void interrupt() {
        running = false;
        eSleep(500);
    }

    public void submit(Operation operation) {
        operations.add(operation);
    }

    protected void eSleep(int milliseconds){
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void process(Operation operation){}

}
