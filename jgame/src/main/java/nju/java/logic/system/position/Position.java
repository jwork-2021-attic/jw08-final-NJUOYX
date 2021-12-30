package nju.java.logic.system.position;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int hashCode() {
        String s = String.format("(%d,%d)", x, y);
        return s.hashCode();
    }
    @Override
    public boolean equals(Object other) {
        if(other instanceof Position) {
            Position op = (Position)other;
            return op.x == x && op.y == y;
        }else{
            return false;
        }
    }
}
