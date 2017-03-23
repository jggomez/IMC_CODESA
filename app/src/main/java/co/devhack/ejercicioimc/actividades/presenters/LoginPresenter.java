package co.devhack.ejercicioimc.actividades.presenters;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import co.devhack.ejercicioimc.actividades.view.ILoginView;

/**
 * Created by jggomez on 23-Mar-17.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;
    private FirebaseAuth auth;

    public LoginPresenter(ILoginView view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password) {

        view.mostrarLoading();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                view.ocultarLoading();
                if (task.isSuccessful()) {
                    view.goToHistorialIMC();
                    return;
                }

                view.mostrarError(task.getException());
            }
        });

    }

}
