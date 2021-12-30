package nju.java.logic.system.engine;

import java.awt.*;

public interface Engine {
    void resizeScreen(int rangeX, int rangeY);
    void display(int x, int y, char character, Color color, Boolean visible);
    int getRangeX();
    int getRangeY();
    @Deprecated
    String getInput();
    String getInput(String player);
    @Deprecated
    void logOut(int logIndex, String log);
    void logOut(int logIndex, String log, String player);
}
