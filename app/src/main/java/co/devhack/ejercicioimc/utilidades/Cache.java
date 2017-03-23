package co.devhack.ejercicioimc.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jggomez on 15-Mar-17.
 */

public class Cache {

    public static void add(String llave, String valor, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(llave, valor).apply();
    }

    public static String get(String llave, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
        return sharedPreferences.getString(llave, "");
    }


}
