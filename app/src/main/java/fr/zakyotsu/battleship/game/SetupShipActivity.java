package fr.zakyotsu.battleship.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Set;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.utils.Utils;

public class SetupShipActivity extends AppCompatActivity {

    private GridLayout boardView;
    private Spinner boatTypeSpinner, directionSpinner;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);

        activity = this;

        boardView = findViewById(R.id.boardGrid);
        boatTypeSpinner = findViewById(R.id.boatTypeSpinner);
        directionSpinner = findViewById(R.id.directionSpinner);

        Utils.generateGridBoard(boardView, this);
    }



    public void btnDeleteAllBoats(View view) {
        //On supprime tous les bateaux du joueur
        AuthActivity.player.deleteAllBoats();

        //On régénère la grille
        Utils.generateGridBoard(boardView, activity);
    }

    public void btnLaunchGame(View view) {

    }

    public void btnPlaceAuto(View view) {
        Random r = new Random();
        Player pl = AuthActivity.player;

        //On supprime tous les bateaux du joueur
        pl.deleteAllBoats();

        for(Utils.BoatType bt : Utils.BoatType.values()) {
            for(int nbMax = 0; nbMax < bt.getMaxNumber(); nbMax++) {

                //Direction aléatoire
                Utils.Direction dir = Utils.Direction.fromInt(r.nextInt(2));

                //Case aléatoire
                int x = r.nextInt(Utils.GRID_COLUMNS);
                int y = r.nextInt(Utils.GRID_ROWS);

                while(!Utils.checkLocation(x, y, dir, bt.getSize(), pl)) {
                    dir = Utils.Direction.fromInt(r.nextInt(2));
                    x = r.nextInt(Utils.GRID_COLUMNS);
                    y = r.nextInt(Utils.GRID_ROWS);
                }

                Boat boat = new Boat(bt, dir);
                boat.setLocation(new Location(x, y));

                pl.addBoat(boat);
            }
        }
        //On dessine les bateaux du joueur dans la vue concernée
        Utils.drawBoats(pl, activity, boardView);
    }
}