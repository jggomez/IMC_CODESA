package co.devhack.ejercicioimc.modelos;

/**
 * Created by jggomez on 23-Mar-17.
 */

public class Usuario {

    private String nombreUsuario;
    private String urlImagen;

    public Usuario() {

    }

    public Usuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}
