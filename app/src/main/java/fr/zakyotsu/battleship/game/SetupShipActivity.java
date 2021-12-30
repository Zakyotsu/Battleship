package fr.zakyotsu.battleship.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import java.util.Random;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.utils.Utils;

public class SetupShipActivity extends AppCompatActivity {

    private GridLayout preparationGrid;
    private Spinner boatTypeSpinner, directionSpinner;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preparation);

        activity = this;

        preparationGrid = findViewById(R.id.preparationGrid);
        boatTypeSpinner = findViewById(R.id.boatTypeSpinner);
        directionSpinner = findViewById(R.id.directionSpinner);

        Utils.generateGrid(preparationGrid, activity, Utils.GridType.PREPARATION);
    }



    public void btnDeleteAllBoats(View view) {
        //On supprime tous les bateaux du joueur
        AuthActivity.player.deleteAllBoats();

        //On régénère la grille de préparation
        Utils.generateGrid(preparationGrid, activity, Utils.GridType.PREPARATION);
    }

    public void btnLaunchGame(View view) {
        Intent intent = new Intent(activity, GameActivity.class);
        startActivity(intent);
        finish();
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
                int x = r.nextInt(Utils.GRID_SIZE);
                int y = r.nextInt(Utils.GRID_SIZE);

                while(!Utils.checkLocation(x, y, dir, bt.getSize(), pl)) {
                    dir = Utils.Direction.fromInt(r.nextInt(2));
                    x = r.nextInt(Utils.GRID_SIZE);
                    y = r.nextInt(Utils.GRID_SIZE);
                }

                Boat boat = new Boat(bt, dir);
                boat.setLocation(new Location(x, y));

                pl.addBoat(boat);
            }
        }
        //On dessine les bateaux du joueur dans la vue concernée
        Utils.drawBoats(pl, activity, preparationGrid, Utils.GridType.PREPARATION);
    }
}