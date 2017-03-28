package co.devhack.ejercicioimc.actividades;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.fragmentos.CalculoFragment;
import co.devhack.ejercicioimc.modelos.HistorialIMC;

public class CalculoActivity extends AppCompatActivity
        implements CalculoFragment.OnCalculoInteractionListener {

    @BindView(R.id.toolbarPpal)
    Toolbar toolbarPpal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculo);

        ButterKnife.bind(this);

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_calculo, CalculoFragment.newInstance());
        transaction.commit();

        setSupportActionBar(toolbarPpal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.titulo_calcular_imc));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void totalCalculo(double imc) {
        //Toast.makeText(this, "EL IMC es:" + imc, Toast.LENGTH_LONG).show();
    }

    @Override
    public void adicionarIMC(HistorialIMC historialIMC) {
        finish();
    }
}
