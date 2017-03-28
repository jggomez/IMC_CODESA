package co.devhack.ejercicioimc.actividades.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import co.devhack.ejercicioimc.actividades.view.ILoginView;
import co.devhack.ejercicioimc.modelos.Usuario;
import co.devhack.ejercicioimc.utilidades.Cache;
import co.devhack.ejercicioimc.utilidades.Constantes;

/**
 * Created by jggomez on 23-Mar-17.
 */

public class LoginPresenter implements ILoginPresenter {

    private String TAG = "LoginPresenter";
    private ILoginView view;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private Context context;

    public LoginPresenter(ILoginView view, Context context) {
        this.view = view;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        this.context = context;
    }

    @Override
    public void login(String email, String password) {

        view.mostrarLoading();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                view.ocultarLoading();
                if (task.isSuccessful()) {

                    final FirebaseUser userUth = task.getResult().getUser();

                    task.getResult().getUser().getToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {

                            final String token = task.getResult().getToken();
                            db.getReference("usuarios").child(userUth.getUid())
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            Usuario user = dataSnapshot.getValue(Usuario.class);
                                            view.saveAuth(token, user.getNombreUsuario(), userUth.getUid());
                                            view.goToHistorialIMC();
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });


                        }
                    });

                } else {
                    view.mostrarError(task.getException());
                }
            }
        });

    }

    @Override
    public void isLogging() {
        if (!Cache.get(Constantes.TOKEN_KEY, context).equals("")) {
            view.goToHistorialIMC();
        }
    }

}
