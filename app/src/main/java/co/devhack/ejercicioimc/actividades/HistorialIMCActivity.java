package co.devhack.ejercicioimc.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.linear.EndOffsetItemDecoration;
import com.dgreenhalgh.android.simpleitemdecoration.linear.StartOffsetItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.adaptadores.HistorialIMCAdapdator;
import co.devhack.ejercicioimc.logica.LCalculoIMC;
import co.devhack.ejercicioimc.modelos.HistorialIMC;

public class HistorialIMCActivity extends AppCompatActivity {

    @BindView(R.id.rcvHistorialIMC)
    RecyclerView rcvHistorialIMC;

    @BindView(R.id.toolbarPpal)
    Toolbar toolbarPpal;

    @BindView(R.id.drawerHistorial)
    DrawerLayout drawerHistorial;

    @BindView(R.id.nview)
    NavigationView nview;

    HistorialIMCAdapdator historialIMCAdapdator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_imc);

        ButterKnife.bind(this);

        initAdaptador();
        initRecyclerView();

        setSupportActionBar(toolbarPpal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger_imc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nview.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return nvItemSelected(item);
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                drawerHistorial.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean nvItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemAdicionarIMC:
                initCalcularActivity();
                break;
            case R.id.itemDescripcionIMC:
                Intent intent = new Intent(this, DescripcionIMCActivity.class);
                startActivity(intent);
                break;
            case R.id.itemAcerca:
                dialogo("Acerca de", "Desarrollado por Juan G Gomez - Devhack");
                break;
            case R.id.itemContactenos:
                dialogo("Contactenos", "Contactenos a hola@devhack.co o 3174455555");
                break;
        }

        drawerHistorial.closeDrawers();

        return true;
    }

    private void dialogo(String titulo, String mensaje){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(titulo);
        dialog.setPositiveButton(mensaje, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        historialIMCAdapdator.add(LCalculoIMC.getLstHistorialIMC());
    }

    private void initRecyclerView() {
        rcvHistorialIMC.setLayoutManager(new LinearLayoutManager(this));

        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        rcvHistorialIMC.addItemDecoration(new DividerItemDecoration(drawable));

        rcvHistorialIMC.setAdapter(historialIMCAdapdator);
    }

    private void initAdaptador() {
        if (historialIMCAdapdator == null) {
            historialIMCAdapdator =
                    new HistorialIMCAdapdator(new ArrayList<HistorialIMC>(), this);
        }
    }

    @OnClick(R.id.historialBtnAdicionar)
    public void clickAdicionar() {
        initCalcularActivity();
    }

    private void initCalcularActivity() {
        Intent intent = new Intent(this, CalculoActivity.class);
        startActivity(intent);
    }


}
