package co.devhack.ejercicioimc.logica;

import java.util.ArrayList;
import java.util.List;

import co.devhack.ejercicioimc.modelos.HistorialIMC;

/**
 * Created by jggomez on 14-Mar-17.
 */

public class LCalculoIMC {

    private static List<HistorialIMC> lstHistorialIMC
            = new ArrayList<>();

    public static void addHistorial(HistorialIMC historialIMC) {
        lstHistorialIMC.add(historialIMC);
    }

    public static double calcular(double peso, double estatura) {
        return peso / Math.pow(estatura, 2);
    }

    public static List<HistorialIMC> getLstHistorialIMC() {
        return lstHistorialIMC;
    }

}
