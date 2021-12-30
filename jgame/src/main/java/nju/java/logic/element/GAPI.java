package nju.java.logic.element;

import nju.java.logic.system.position.Position;

import java.awt.Color;
import java.util.List;
import java.util.Queue;

public interface GAPI {
    /**
     * draw a pixel on the screen
     * @param x position x
     * @param y position y
     * @param character
     * @param color rgb color
     * @param visible if visible is true, then display,
     *                otherwise erase this position pixel from the screen
     */
    void display(int x ,int y, char character, Color color, Boolean visible);

    /**
     * this method allows an element to occupy a position
     * @param x position x
     * @param y position y
     * @param requester the method caller element
     * @return return the caller element if succeeded, otherwise return the current
     * occupier of the position
     */
    Element tryOccupy(int x , int y, Element requester);

    /**
     * this method is to check whether a position is taken or not
     * @param x position x
     * @param y position y
     * @return return null if this position has no owner,otherwise return the owner.
     */
    Element exist(int x, int y);

    /**
     * release a position only if the parameter element is the owner of the position
     * @param x position x
     * @param y position y
     * @param element owner of the position
     */
    void release(int x, int y, Element element);

    /**
     * get user input
     * @return the typical string of some operations
     */
    String getInput();

    /**
     * for multiplayer mode
     * @param playerId
     * @return
     */
    String getInput(String playerId);
    /**
     * this method can find a typical element by its name
     * @param name
     * @return return the typical element if it exists and name is correct,otherwise returns null
     */
    Element getElement(String name);

    /**
     * register an element so that other element could find it
     * @param name
     * @param element
     */
    void register(String name ,Element element);

    /**
     * unregister an element
     * @param name
     * @param element only the owner of the name could unregister the name
     */
    void unregister(String name,Element element);

    @Deprecated
    void logOut(int index, String log);

    void logOut(int index, String log, String player);

    /**
     * get the position route from x,y to nx,ny
     * @param x current x
     * @param y current y
     * @param nx new x
     * @param ny new y
     * @return position queue exclude {x,y}and include {nx, ny}
     */
    List<Position> getRoute(int x, int y, int nx, int ny);
}
