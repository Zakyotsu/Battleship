package fr.zakyotsu.battleship.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import fr.zakyotsu.battleship.R;
import fr.zakyotsu.battleship.game.Player;
import fr.zakyotsu.battleship.game.SetupShipActivity;
import fr.zakyotsu.battleship.utils.Requests;
import fr.zakyotsu.battleship.utils.Utils;

//MAIN CLASS
public class AuthActivity extends AppCompatActivity {

    public static Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Requests.resQueue = Volley.newRequestQueue(this);
    }

    public void checkLogin(View view) {
        String username = ((EditText) findViewById(R.id.usernameBox)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordBox)).getText().toString();

        Requests.getLogin(username, password, response -> {
            try {
                JSONObject obj = new JSONObject(response);

                if(obj.getBoolean("response")) {
                    Toast.makeText(this, getString(R.string.auth_success), Toast.LENGTH_SHORT).show();

                    player = new Player(obj.getString("displayname"), obj.getInt("id"));

                    Intent intent = new Intent(this, SetupShipActivity.class);
                    startActivity(intent);

                    finish();

                } else Toast.makeText(this, getString(R.string.auth_failure), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void registerIntent(View view) {
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        EditText usernameBox =  findViewById(R.id.usernameBox);
        EditText displayNameBox =  findViewById(R.id.displayNameBox);
        EditText passwordBox1 =  findViewById(R.id.passwordBox1);
        EditText passwordBox2 =  findViewById(R.id.passwordBox2);

        if(Utils.checkNoSpecialChars(usernameBox) && Utils.checkNoSpecialChars(displayNameBox)) {
            if(Utils.checkBothPasswords(passwordBox1, passwordBox2)) {


            } else
                Toast.makeText(this, "Les deux mots de passe doivent correspondre.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Caractères spéciaux non autorisés dans votre pseudo de connexion et pseudo dans l'application.", Toast.LENGTH_SHORT).show();
    }
    public void forgotPasswordIntent(View view) {

    }

    public void forgotPassword(View view) {

    }


}