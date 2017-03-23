package co.devhack.ejercicioimc.actividades.presenters;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.devhack.ejercicioimc.actividades.view.IRegistrarView;

/**
 * Created by jggomez on 22-Mar-17.
 */

public class RegistrarPresenter implements IRegistrarPresenter {

    private FirebaseAuth auth;

    private IRegistrarView view;

    public RegistrarPresenter(IRegistrarView view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void crearCuenta(String nombres, String email, String password) {

        view.mostrarLoading();

        auth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        view.ocultarLoading();
                        if (task.isSuccessful()) {
                            view.goToLogin();
                            return;
                        }

                        view.mostrarError(task.getException());
                    }
                });
    }
}
