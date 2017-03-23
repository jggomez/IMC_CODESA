package co.devhack.ejercicioimc.actividades;

import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.devhack.ejercicioimc.R;

public class DescripcionIMCActivity extends AppCompatActivity {

    @BindView(R.id.toolbarPpal)
    Toolbar toolbarPpal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion_imc);

        ButterKnife.bind(this);

        setSupportActionBar(toolbarPpal);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
