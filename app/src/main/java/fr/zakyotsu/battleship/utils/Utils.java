package fr.zakyotsu.battleship.utils;

import android.app.Activity;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;

import java.util.Random;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.game.Boat;
import fr.zakyotsu.battleship.game.offline.OfflineGameActivity;
import fr.zakyotsu.battleship.game.Location;
import fr.zakyotsu.battleship.game.Player;

public class Utils {

    public static int GRID_SIZE = 10;

    public enum Direction {
        HORIZONTAL, VERTICAL;

        public static Direction fromInt(int x) {
            return x == 0 ? HORIZONTAL : VERTICAL;
        }
    }

    public enum BoatType {
        BATTLESHIP(4, 1, "#4DB8FF"),
        SUBMARINE(3, 2, "#FF66FF"),
        CRUISER(2, 3, "#00FF00"),
        PATROL(1, 4, "#FFFF00");

        private final int size, maxNumber;
        private final String color;
        BoatType(int size, int maxNumber, String color) {
            this.size = size;
            this.maxNumber = maxNumber;
            this.color = color;
        }

        public int getSize(){
            return size;
        }

        public int getMaxNumber(){
            return maxNumber;
        }

        public String getColor() {
            return color;
        }
    }

    public enum GridType {
        ENEMY, ALLY, PREPARATION
    }

    public enum Result {
        MISS, HIT
    }

    /**
     * Permet de générer la grille du jeu
     * @param activity L'activité correspondante de l'application
     * @param view Le GridLayout où sera dessiné la grille
     * @param type Le type de Grille, ici ALLY, PREPARATION, ou ENEMY.
     */
    public static void generateGrid(GridLayout view, Activity activity, GridType type) {
        TextView box;
        GridLayout.LayoutParams glp;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        view.removeAllViewsInLayout();

        double displaySize = 0.0D;
        switch(type) {
            case ALLY:
                displaySize = displayMetrics.widthPixels / 2.0D;
                break;
            case ENEMY:
            case PREPARATION:
                displaySize = displayMetrics.widthPixels;
                break;
        }
        double boxSize = Math.floor((displaySize / Utils.GRID_SIZE) / 10.0D) * 10;

        //Mise en place des TextView
        for (int i = 0; i < Utils.GRID_SIZE; i++){
            for (int j = 0; j < Utils.GRID_SIZE; j++){
                glp = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                box = new TextView(activity.getApplicationContext());

                //Si petite grille, l'ID aura un '1' devant
                switch(type) {
                    case ALLY:
                        box.setId(Integer.parseInt("1" + i + "" + j));
                        break;
                    case ENEMY:
                    case PREPARATION:
                        box.setId(Integer.parseInt(i + "" + j));
                        break;
                }

                box.setWidth((int) boxSize);
                box.setHeight((int) boxSize);

                box.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
                box.setBackgroundResource(R.drawable.borders);
                box.setLayoutParams(glp);

                view.addView(box);
            }
        }
    }

    /**
     * Tirage des bateaux aléatoirement
     */
    public static void randomDrawing(Player p) {
        Random r = new Random();

        //On supprime tous les bateaux du joueur
        p.deleteAllBoats();

        for(Utils.BoatType bt : Utils.BoatType.values()) {
            for(int nbMax = 0; nbMax < bt.getMaxNumber(); nbMax++) {

                //Direction aléatoire
                Utils.Direction dir = Utils.Direction.fromInt(r.nextInt(2));

                //Case aléatoire
                int x = r.nextInt(Utils.GRID_SIZE);
                int y = r.nextInt(Utils.GRID_SIZE);

                while(!checkLocation(x, y, dir, bt.getSize(), p)) {
                    dir = Utils.Direction.fromInt(r.nextInt(2));
                    x = r.nextInt(Utils.GRID_SIZE);
                    y = r.nextInt(Utils.GRID_SIZE);
                }

                Boat boat = new Boat(bt, dir);
                boat.setLocation(new Location(x, y));

                p.addBoat(boat);
            }
        }
    }

    /**
     * Vérifie qu'une coordonnée est libre ou non
     * @param x X
     * @param y Y
     * @param dir Direction
     * @param size Taille
     * @param pl Joueur
     * @return Vrai si les coordonnées sont libres, Faux sinon
     */
    public static boolean checkLocation(int x, int y, Utils.Direction dir, int size, Player pl) {
        for(int i = 0; i < size; i++) {
            int newX = x;
            int newY = y;

            switch(dir) {
                case HORIZONTAL:
                    newY = y + i;
                    //Si Y en dehors de la grille
                    if(newX >= Utils.GRID_SIZE || newY >= Utils.GRID_SIZE) return false;

                    //On vérifie que X,Y ne soient pas déjà sur une case occupée
                    for(Boat b : pl.getBoats()) {
                        for (Location l : b.getLocations()) {
                            if (newX == l.getX() && newY == l.getY()) return false;
                        }
                    }
                    break;

                case VERTICAL:
                    newX = x + i;
                    //Si X en dehors de la grille
                    if(newX >= Utils.GRID_SIZE || newY >= Utils.GRID_SIZE) {
                        return false;
                    }

                    //On vérifie que X,Y ne soient pas déjà sur une case occupée
                    for(Boat b : pl.getBoats()) {
                        for (Location l : b.getLocations()) {
                            if (newX == l.getX() && newY == l.getY()) return false;
                        }
                    }
                    break;
            }
        }
        //Si toutes les vérifications passent, les coordonnées sont libres.
        return true;
    }

    /**
     * Dessine les bateaux dans la grille définie
     * @param pl Joueur
     * @param activity Activité
     * @param view La vue de la grille
     * @param type Le type de grille
     */
    public static void renderBoats(Player pl, Activity activity, GridLayout view, GridType type) {
        //Régénération de la grille
        generateGrid(view, activity, type);

        for(Boat boat : pl.getBoats())
            for(Location location : boat.getLocations()) {
                int x = location.getX();
                int y = location.getY();
                TextView tv = null;
                switch(type) {
                    case ALLY:
                        tv = activity.findViewById(Integer.parseInt("1" + x + "" + y));
                        break;
                    case ENEMY:
                    case PREPARATION:
                        tv = activity.findViewById(Integer.parseInt(x + "" + y));
                        break;
                }

                if(tv != null) tv.setBackgroundColor(Color.parseColor(boat.getType().getColor()));
            }
    }




    /*
    public static void checkWinner() {
        //Récupération du nb total de bateaux
        int maxBoats = 0;
        for(BoatType type : BoatType.values()) {
            maxBoats += type.getMaxNumber();
        }

        //Vérification du nb de bateaux coulés
        for(Player pl : OfflineGameActivity.players) {
            int nbSunk = 0;
            for(Boat b : pl.getBoats()) {
                if(b.isSunk()) nbSunk++;
            }
            if(nbSunk == maxBoats) {

            }
        }
    }*/






    public static boolean checkNoSpecialChars(EditText et) {
        return et.getText().toString().matches("^[a-zA-Z0-9]*$");
    }

    public static boolean checkBothPasswords(EditText et, EditText et1) {
        return et.getText().toString().equals(et1.getText().toString());
    }
}
