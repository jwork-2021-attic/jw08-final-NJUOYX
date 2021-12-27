package usys;

import usys.status.RunningStatus;
import usys.status.Status;
import usys.status.StatusFactory;
import usys.status.StoppedStatus;
import usys.util.FpsTimer;
import usys.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

/**
 * 作为单例存在，并且要作为主线程运行，是整个游戏的基础引擎
 * 状态转变：stopped->running, running->stopped
 */
public class UnitManager {
    private static UnitManager INSTANCE = null;

    private ExecutorService executor;

    private List<Unit>tasks;

    private Status status;

    private FpsTimer fpsTimer;

    private UnitManager(){
        this.executor = Executors.newCachedThreadPool();
        this.tasks = new LinkedList<>();
        this.status = StatusFactory.stoppedStatus();
        fpsTimer = new FpsTimer(1);
    }


    public static UnitManager getInstance(){
        if(INSTANCE == null){
            INSTANCE = new UnitManager();
        }
        return INSTANCE;
    }

    public void run(int fps){
        this.status = statusChange(this.status, StatusFactory.runningStatus());
        if(this.status instanceof RunningStatus){
            fpsTimer.resetTimer(fps);
            statusSpin((RunningStatus) this.status);
        }else{
            Log.log("unexpected status when running");
        }
    }

    private Status statusChange(Status currentStatus, RunningStatus runningStatus){
        if(currentStatus instanceof StoppedStatus){
            return runningStatus;
        }else{
            return currentStatus;
        }
    }

    private void statusSpin(RunningStatus runningStatus){
        while(this.status == runningStatus){
            this.fpsTimer.startTimer(new TimerTask() {
                @Override
                public void run() {
                    triggerTasks();
                }
            });
            this.fpsTimer.resetTimer();
        }
    }

    private void triggerTasks(){
        List<Future<Boolean>>results = new LinkedList<>();
        List<Unit>removes = new LinkedList<>();
        this.tasks.forEach(task->results.add(triggerTask(task)));
        for(int i = 0;i<results.size();++i){
            try {
                Boolean res = results.get(i).get();
                if(!res){
                    removes.add(tasks.get(i));
                }
            } catch (InterruptedException e) {
                Log.log("UnitManager is forced exited");
                System.exit(0);
            } catch (ExecutionException e) {
                Log.log("exception occurred when try to get result of a task");
                e.printStackTrace();
                System.exit(-1);
            }
        }
        tasks.removeAll(removes);
    }

    private Future<Boolean> triggerTask(Unit task){
        return executor.submit(task);
    }

}
