package fr.zakyotsu.battleship.game.offline;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.game.Location;
import fr.zakyotsu.battleship.utils.Utils;

public class OfflineGameActivity extends AppCompatActivity {

    private GridLayout enemyGameGrid, allyGameGrid;
    private Activity activity;
    private OfflineGame offlineGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activity = this;
        offlineGame = new OfflineGame(AuthActivity.player, activity);
        enemyGameGrid = findViewById(R.id.enemyGameGrid);
        allyGameGrid = findViewById(R.id.allyGameGrid);

        Utils.generateGrid(enemyGameGrid, activity, Utils.GridType.ENEMY);
        Utils.generateGrid(allyGameGrid, activity, Utils.GridType.ALLY);

        Utils.renderBoats(AuthActivity.player, activity, allyGameGrid,Utils.GridType.ALLY);

        setupListeners();
    }

    /**
     * Mettre les listeners pour les TextView
     */
    public void setupListeners() {
        for (int i = 0; i < Utils.GRID_SIZE; i++) {
            for (int j = 0; j < Utils.GRID_SIZE; j++) {
                TextView tv = findViewById(Integer.parseInt(i + "" + j));
                tv.setOnClickListener(view -> {
                    int id = view.getId();
                    String idString = id+"";
                    if(idString.length() == 1) {
                        idString = "0" + idString;
                    }
                    int x = Integer.parseInt(idString.charAt(0)+"");
                    int y = Integer.parseInt(idString.charAt(1)+"");

                    offlineGame.playerShoot(new Location(x, y));
                });
            }
        }
    }

}
