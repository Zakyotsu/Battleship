package fr.zakyotsu.battleship.game;

import android.graphics.Color;

import java.util.ArrayList;

import fr.zakyotsu.battleship.utils.Utils;

public class Boat {

    //Les cases où le bateau est
    private ArrayList<Location> locations;

    //Type de bateau pr taille et nbMax
    private Utils.BoatType type;

    //Direction du bateau
    private Utils.Direction dir;

    //Nb de coups
    private int hits;

    //Coulé?
    private boolean isSunk;

    public Boat(Utils.BoatType type, Utils.Direction dir) {
        this.locations = new ArrayList<>();
        this.type = type;
        this.dir = dir;
        this.hits = 0;
        this.isSunk = false;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocation(Location location) {
        //Position initiale
        locations.add(location);

        for(int size = 0; size < type.getSize(); size++) {
            int newX = location.getX();
            int newY = location.getY();

            switch (dir) {
                case HORIZONTAL:
                    newY += size;
                    break;

                case VERTICAL:
                    newX += size;
                    break;
            }
            //Positions calculées selon direction
            locations.add(new Location(newX, newY));
        }
    }

    public Utils.BoatType getType() {
        return type;
    }

    public void setSunk(boolean b) {
        isSunk = b;
    }

    public boolean isSunk() {
        return isSunk;
    }

    public void hit() {
        hits++;
        if(hits >= type.getSize()) isSunk = true;
    }

    public int getNumberOfHits() {
        return hits;
    }
}
