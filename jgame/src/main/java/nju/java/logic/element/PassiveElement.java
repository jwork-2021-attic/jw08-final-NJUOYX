package nju.java.logic.element;

import nju.java.logic.element.opration.Operation;

public abstract class PassiveElement extends Element{
    @Override
    public void run(){
        while(isRunning()){
            passiveProcessor();
        }
    }

    protected synchronized void await(){
        eSleep(10);
    }

    protected void passiveProcessor() {
        while(!operations.isEmpty()){
            process(operations.poll());
        }
        await();
    }
}
