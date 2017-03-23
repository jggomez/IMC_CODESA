package co.devhack.ejercicioimc.modelos;

import android.net.Uri;

/**
 * Created by jggomez on 14-Mar-17.
 */
public class HistorialIMC {

    private String nombreUsuario;
    private String resultadoIMC;
    private Uri pathImage;

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getResultadoIMC() {
        return resultadoIMC;
    }

    public void setResultadoIMC(String resultadoIMC) {
        this.resultadoIMC = resultadoIMC;
    }

    public Uri getPathImage() {
        return pathImage;
    }

    public void setPathImage(Uri pathImage) {
        this.pathImage = pathImage;
    }

}
