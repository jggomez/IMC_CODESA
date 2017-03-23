package co.devhack.ejercicioimc.actividades.view;

/**
 * Created by jggomez on 22-Mar-17.
 */

public interface IRegistrarView {

    void mostrarLoading();

    void ocultarLoading();

    void goToLogin();

    void mostrarError(Exception e);

}
