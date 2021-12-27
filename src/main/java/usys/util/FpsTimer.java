package usys.util;

import java.util.Timer;
import java.util.TimerTask;

public class FpsTimer {
    private int fps;
    private long sleepTime;
    private Boolean timeUp;
    private Timer timer;

    public FpsTimer(int fps){
        this.timer = new Timer();
        resetTimer(fps);
    }

    public void resetTimer(int fps){
        this.fps = fps;
        this.sleepTime = 1000/this.fps;
        resetTimer();
    }

    public void resetTimer(){
        this.timeUp = false;
    }

    public void startTimer(TimerTask timerTask){
        timer.schedule(timerTask,this.sleepTime);
    }

    public Boolean isTimeUp(){
        return this.timeUp;
    }

}
