package fr.zakyotsu.battleship.game;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import fr.zakyotsu.battleship.utils.Utils;

public class Player {

    private String name;
    private int uid;
    private ArrayList<Boat> boats;
    private HashMap<Location, Utils.Result> moves;

    public Player(String name, int uid) {
        this.name = name;
        this.uid = uid;
        boats = new ArrayList<>();
        moves = new HashMap<>();
    }

    /**
     *
     * @return ArrayList des bateaux
     */
    public ArrayList<Boat> getBoats() {
        return boats;
    }

    /**+
     * Ajouter un bateau
     * @param boat
     */
    public void addBoat(Boat boat) {
        boats.add(boat);
    }

    /**
     * Supprimer tous les bateaux du joueur
     */
    public void deleteAllBoats() {
        boats.clear();
    }

    public HashMap<Location, Utils.Result> getMoves() {
        return moves;
    }

    public int getId() {
        return uid;
    }
}
