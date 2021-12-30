package fr.zakyotsu.battleship.game;

import java.util.ArrayList;

public class Player {

    private String name;
    private int uid;

    private ArrayList<Boat> boats;

    public Player(String name, int uid) {
        this.name = name;
        this.uid = uid;
        boats = new ArrayList<>();
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
}
