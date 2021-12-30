package fr.zakyotsu.battleship.game;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.auth.AuthActivity;
import fr.zakyotsu.battleship.utils.Utils;

public class GameActivity extends AppCompatActivity {

    private GridLayout enemyGameGrid, allyGameGrid;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        activity = this;

        enemyGameGrid = findViewById(R.id.enemyGameGrid);
        allyGameGrid = findViewById(R.id.allyGameGrid);

        Utils.generateGrid(enemyGameGrid, activity, Utils.GridType.ENEMY);
        Utils.generateGrid(allyGameGrid, activity, Utils.GridType.ALLY);
        Utils.drawBoats(AuthActivity.player, activity, allyGameGrid,Utils.GridType.ALLY);
    }


}
