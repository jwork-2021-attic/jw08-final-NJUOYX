package usys;

import java.lang.System;
import java.util.concurrent.Callable;

public class Unit implements Callable<Boolean> {
    private final int id;

    private Boolean running;

    public Unit(int id){
        this.id = id;
        this.running = true;
    }



    @Override
    public Boolean call() {
        return running;
    }
}
