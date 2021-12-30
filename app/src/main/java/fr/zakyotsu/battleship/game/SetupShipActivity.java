package fr.zakyotsu.battleship.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.game.offline.OfflineGameActivity;
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

    public void btnPlaceAuto(View view) {
        Player pl = AuthActivity.player;

        //Tirage aléatoire des bateaux
        Utils.randomDrawing(pl);

        //On dessine les bateaux du joueur dans la vue concernée
        Utils.renderBoats(pl, activity, preparationGrid, Utils.GridType.PREPARATION);
    }

    public void btnLaunchGame(View view) {
        if(AuthActivity.player.getBoats().size() == 0) {
            Toast.makeText(this, "Positionnez vos bateaux avant!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(activity, OfflineGameActivity.class);
        startActivity(intent);
        finish();
    }
}