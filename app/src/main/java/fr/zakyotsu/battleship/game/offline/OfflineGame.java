package fr.zakyotsu.battleship.game.offline;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.game.Boat;
import fr.zakyotsu.battleship.game.Location;
import fr.zakyotsu.battleship.game.Player;
import fr.zakyotsu.battleship.utils.Utils;

public class OfflineGame {

    public Player player, opponent;
    public Activity activity;
    public int idPlayable;
    TextView turnHeader;

    /**
     * Utiliser seulement si partie contre AI
     * @param player
     * @param activity
     */
    public OfflineGame(Player player, Activity activity) {
        this.player = player;
        this.activity = activity;
        this.opponent = new Player("Ordinateur", 0);
        idPlayable = player.getId();
        turnHeader = activity.findViewById(R.id.turnHeader);

        //Tirage des bateaux pour l'ordinateur
        Utils.randomDrawing(opponent);
    }

    public void playerShoot(Location location) {
        //Le joueur doit attendre son tour
        if(idPlayable != player.getId()) return;

        TextView tv = null;

        //Empêcher de tirer sur une même case
        if(player.getMoves().containsKey(location)) return;


        //On cherche à maj la grande grille, vu que c'est pas l'ordi qui tire
        tv = activity.findViewById(Integer.parseInt(location.getX() + "" + location.getY()));

        for(Boat boat : opponent.getBoats()) {
            for(Location bLocation : boat.getLocations()) {
                if(location.getX() == bLocation.getX() && location.getY() == bLocation.getY()) {
                    boat.hit();
                    tv.setBackgroundColor(Color.rgb(255, 0, 0));
                    Toast.makeText(activity.getApplicationContext(), "Vous avez touché l'ennemi!", Toast.LENGTH_SHORT).show();
                    player.getMoves().put(location, Utils.Result.HIT);
                    return;
                }
            }
        }

        //0=computer
        idPlayable = 0;

        tv.setText("x");
        player.getMoves().put(location, Utils.Result.MISS);

        turnHeader.setText(R.string.enemy_turn);
        shootComputer();
    }


    public void shootComputer() {
        TextView tv = null;
        Random r = new Random();
        int x = r.nextInt(Utils.GRID_SIZE);
        int y = r.nextInt(Utils.GRID_SIZE);
        Location location = new Location(x, y);

        //Empêcher de tirer sur une même case
        while(opponent.getMoves().containsKey(location)) {
            x = r.nextInt(Utils.GRID_SIZE);
            y = r.nextInt(Utils.GRID_SIZE);
            location = new Location(x, y);
        }

        //On cherche à maj la petite grille, donc on rajoute "1"
        tv = activity.findViewById(Integer.parseInt("1" + location.getX() + "" + location.getY()));


        for(Boat boat : player.getBoats()) {
            for(Location bLocation : boat.getLocations()) {
                if(location.getX() == bLocation.getX() && location.getY() == bLocation.getY()) {
                    boat.hit();
                    tv.setBackgroundColor(Color.rgb(255, 0, 0));
                    opponent.getMoves().put(location, Utils.Result.HIT);
                    Toast.makeText(activity.getApplicationContext(), "L'ennemi vous a touché!", Toast.LENGTH_SHORT).show();
                    shootComputer();
                    return;
                }
            }
        }
        tv.setText("x");
        opponent.getMoves().put(location, Utils.Result.MISS);
        turnHeader.setText(R.string.your_turn);
        idPlayable = player.getId();
    }

}
