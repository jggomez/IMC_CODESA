package co.devhack.ejercicioimc.utilidades;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jggomez on 15-Mar-17.
 */

public class Cache {

    private static SharedPreferences sharedPreferences = null;

    private static void initCache(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("cache", Context.MODE_PRIVATE);
        }
    }

    public static void add(String llave, String valor, Context context) {
        initCache(context);
        sharedPreferences.edit().putString(llave, valor).apply();
    }

    public static String get(String llave, Context context) {
        initCache(context);
        return sharedPreferences.getString(llave, "");
    }

    public static void removeAll(Context context) {
        initCache(context);
        sharedPreferences.edit().clear().commit();
    }


}
