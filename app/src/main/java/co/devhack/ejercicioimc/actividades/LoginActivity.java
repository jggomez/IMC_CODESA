package co.devhack.ejercicioimc.actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.actividades.presenters.ILoginPresenter;
import co.devhack.ejercicioimc.actividades.presenters.LoginPresenter;
import co.devhack.ejercicioimc.actividades.view.ILoginView;
import co.devhack.ejercicioimc.modelos.HistorialIMC;
import co.devhack.ejercicioimc.utilidades.Utilidades;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    private ProgressDialog progressDialog;
    private ILoginPresenter loginPresenter;

    @BindView(R.id.txtEmail)
    EditText txtEmail;

    @BindView(R.id.txtPassword)
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
    }

    @OnClick(R.id.btnLoginRegistrarse)
    public void clickRegistrarse() {
        Utilidades.initActivity(this, RegistrarActivity.class);
    }

    @OnClick(R.id.btnIngresar)
    public void clickIngresar() {
        loginPresenter.login(txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public void mostrarLoading() {
        progressDialog =
                ProgressDialog.show(this,
                        getString(R.string.titulo_cargando),
                        getString(R.string.procesando));
    }

    @Override
    public void ocultarLoading() {
        progressDialog.dismiss();
    }

    @Override
    public void goToHistorialIMC() {
        Utilidades.initActivity(this, HistorialIMCActivity.class);
        finish();
    }

    @Override
    public void mostrarError(Exception e) {
        Utilidades.dialogoError(getString(R.string.titulo_error), e.getMessage(), this);
    }
}
