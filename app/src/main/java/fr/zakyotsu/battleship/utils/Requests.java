package fr.zakyotsu.battleship.utils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;

public class Requests {

    private static final String LOCATION_URL = "http://192.168.1.11";
    private static final String LOCATION_URL_FOLDER = "/battleship/";

    //Variable définie dans le AuthActivity.
    public static RequestQueue resQueue;

    public static void getLogin(String username, String password, final VolleyCallback volleyCallback) {
        String url = getURL("login.php?username=" + username + "&password=" + password);
        StringRequest strReq = new StringRequest(Request.Method.GET, url, volleyCallback::onSuccess, Throwable::printStackTrace);
        resQueue.add(strReq);
    }

    private static String getURL(String arg) {
        return LOCATION_URL + LOCATION_URL_FOLDER + arg;
    }
}

