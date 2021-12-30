package fr.zakyotsu.battleship.game;

public class Location {

    private int x;
    private int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) return false;
        if (obj == this) return true;

        Location other = (Location) obj;
        return this.x == other.x && this.y == other.y;
    }
}
