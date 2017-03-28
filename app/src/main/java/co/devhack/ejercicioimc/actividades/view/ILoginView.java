package co.devhack.ejercicioimc.actividades.view;

/**
 * Created by jggomez on 23-Mar-17.
 */

public interface ILoginView {

    void mostrarLoading();

    void ocultarLoading();

    void goToHistorialIMC();

    void mostrarError(Exception e);

    void saveAuth(String token, String nombre, String uid);

}
