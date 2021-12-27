package usys.status;

public class StatusFactory {

    public static RunningStatus runningStatus(){
        return new RunningStatus();
    }

    public static StoppedStatus stoppedStatus(){
        return new StoppedStatus();
    }

}
