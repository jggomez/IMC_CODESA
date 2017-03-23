package co.devhack.ejercicioimc.actividades;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.actividades.presenters.IRegistrarPresenter;
import co.devhack.ejercicioimc.actividades.presenters.RegistrarPresenter;
import co.devhack.ejercicioimc.actividades.view.IRegistrarView;
import co.devhack.ejercicioimc.utilidades.Utilidades;

public class RegistrarActivity extends AppCompatActivity implements IRegistrarView {

    private ProgressDialog progressDialog;

    @BindView(R.id.txtNombres)
    EditText txtNombres;

    @BindView(R.id.txtRegistroLogin)
    EditText txtRegistroLogin;

    @BindView(R.id.txtRegistroPassword)
    EditText txtRegistroPassword;

    private IRegistrarPresenter presenterRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        ButterKnife.bind(this);

        presenterRegistrar = new RegistrarPresenter(this);

    }

    @OnClick(R.id.btnRegistrarse)
    public void clickRegistrarse() {
        presenterRegistrar.crearCuenta(
                txtNombres.getText().toString(),
                txtRegistroLogin.getText().toString(),
                txtRegistroPassword.getText().toString());
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
    public void goToLogin() {
        Utilidades.initActivity(this, LoginActivity.class);
        finish();
    }

    @Override
    public void mostrarError(Exception e) {
        Utilidades.dialogoError(getString(R.string.titulo_error), e.getMessage(), this);
    }
}
