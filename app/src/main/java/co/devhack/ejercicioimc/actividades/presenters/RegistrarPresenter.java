package co.devhack.ejercicioimc.actividades.presenters;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.devhack.ejercicioimc.actividades.view.IRegistrarView;
import co.devhack.ejercicioimc.modelos.Usuario;

/**
 * Created by jggomez on 22-Mar-17.
 */

public class RegistrarPresenter implements IRegistrarPresenter {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;

    private IRegistrarView view;

    public RegistrarPresenter(IRegistrarView view) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        reference = database.getReference("usuarios");
    }

    @Override
    public void crearCuenta(final String nombres, String email, String password) {

        view.mostrarLoading();

        auth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            final FirebaseUser userUth = task.getResult().getUser();

                            UserProfileChangeRequest.Builder userProfileReq = new UserProfileChangeRequest.Builder();
                            userProfileReq.setDisplayName(nombres);

                            userUth.updateProfile(userProfileReq.build()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reference.child(
                                            userUth.getUid())
                                            .setValue(new Usuario(nombres));
                                    view.goToLogin();
                                }
                            });

                            return;
                        } else {
                            view.mostrarError(task.getException());
                        }

                        view.ocultarLoading();
                    }
                });
    }
}
