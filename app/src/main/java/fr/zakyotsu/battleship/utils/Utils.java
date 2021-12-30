package fr.zakyotsu.battleship.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

import androidx.gridlayout.widget.GridLayout;

import org.w3c.dom.Text;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.game.Boat;
import fr.zakyotsu.battleship.game.Location;
import fr.zakyotsu.battleship.game.Player;

public class Utils {

    public static int GRID_ROWS = 10;
    public static int GRID_COLUMNS = 10;

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



    public static boolean checkNoSpecialChars(EditText et) {
        return et.getText().toString().matches("^[a-zA-Z0-9]*$");
    }

    public static boolean checkBothPasswords(EditText et, EditText et1) {
        return et.getText().toString().equals(et1.getText().toString());
    }

    /**
     * Permet de générer la grille du jeu dans le GridLayout correspondant
     * @param activity L'activité correspondante de l'application
     */
    public static void generateGridBoard(GridLayout boardView, Activity activity) {
        TextView box;
        GridLayout.LayoutParams glp = null;
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();

        boardView.removeAllViewsInLayout();

        //Tentative de supprimer les bordures mal faites...
        int boxWidth = displayMetrics.widthPixels/ Utils.GRID_COLUMNS;
        double boxWidthSize = Math.floor(boxWidth/10.0D) * 10;

        int boxHeight = displayMetrics.widthPixels/Utils.GRID_ROWS;
        double boxHeightSize = Math.floor(boxHeight/10.0D) * 10;

        //Mise en place des TextView
        for (int i = 0; i < Utils.GRID_COLUMNS; i++){
            for (int j = 0; j < Utils.GRID_ROWS; j++){
                glp = new GridLayout.LayoutParams(GridLayout.spec(i), GridLayout.spec(j));

                box = new TextView(activity.getApplicationContext());

                //IDs : 00 01 02 03... 10 11 12 13... 20 21 22 23...
                box.setId(Integer.parseInt(i + "" + j));

                box.setTag(i + "," + j);
                box.setMinWidth((int) boxWidthSize);
                box.setMinHeight((int) boxHeightSize);

                box.setOnClickListener(l -> {
                    System.out.println("Case: " + l.getId());
                });

                box.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
                box.setBackgroundResource(R.drawable.borders);
                box.setLayoutParams(glp);

                boardView.addView(box);
            }
        }
    }

    public static boolean checkLocation(int x, int y, Utils.Direction dir, int size, Player pl) {
        for(int i = 0; i < size; i++) {
            int newX = x;
            int newY = y;

            switch(dir) {
                case HORIZONTAL:
                    newY = y + i;
                    //Si Y en dehors de la grille
                    if(newX >= Utils.GRID_COLUMNS || newY >= Utils.GRID_ROWS) return false;

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
                    if(newX >= Utils.GRID_COLUMNS || newY >= Utils.GRID_ROWS) {
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

    public static void drawBoats(Player pl, Activity activity, GridLayout boardView) {
        generateGridBoard(boardView, activity);
        for(Boat boat : pl.getBoats()) {
            for(Location location : boat.getLocations()) {
                int x = location.getX();
                int y = location.getY();
                TextView tv = activity.findViewById(Integer.parseInt(x + "" + y));
                if(tv != null) tv.setBackgroundColor(Color.parseColor(boat.getType().getColor()));
                else Log.d("Utils: ", "No view found for: x=" + x + ", y=" + y);
            }
        }
    }

}
