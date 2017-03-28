package co.devhack.ejercicioimc.actividades;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.adaptadores.HistorialIMCAdapdator;
import co.devhack.ejercicioimc.logica.LCalculoIMC;
import co.devhack.ejercicioimc.modelos.HistorialIMC;
import co.devhack.ejercicioimc.utilidades.Cache;
import co.devhack.ejercicioimc.utilidades.Constantes;
import co.devhack.ejercicioimc.utilidades.Utilidades;

public class HistorialIMCActivity extends AppCompatActivity {

    // Request Code
    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;

    private String TAG = "HistorialIMCActivity";
    private CharSequence[] arrOpcionesObtenerImagen;
    private Uri urlImagenPerfil;
    private ProgressDialog progressDialog;

    private DatabaseReference db;
    private FirebaseStorage storage;

    @BindView(R.id.rcvHistorialIMC)
    RecyclerView rcvHistorialIMC;

    @BindView(R.id.toolbarPpal)
    Toolbar toolbarPpal;

    @BindView(R.id.drawerHistorial)
    DrawerLayout drawerHistorial;

    @BindView(R.id.nview)
    NavigationView nview;

    private ImageButton nvImagenPerfil;

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
        getSupportActionBar().setTitle(getString(R.string.titulo_lista_imc));

        nview.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        return nvItemSelected(item);
                    }
                }
        );

        arrOpcionesObtenerImagen = new CharSequence[]
                {getResources().getString(R.string.opcionesImagenCamara),
                        getResources().getString(R.string.opcionesImagenGaleria)};

        View headerLayout = nview.getHeaderView(0);

        TextView nvTxtNombre = (TextView) headerLayout.findViewById(R.id.nvTxtNombre);
        nvTxtNombre.setText(Cache.get(Constantes.NOMBRE_KEY, this));

        nvImagenPerfil = (ImageButton) headerLayout.findViewById(R.id.nvImagenPerfil);
        nvImagenPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });

        db = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

    }

    private void cargarImagen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("De donde vas a obtener la imagen ?");
        builder.setItems(arrOpcionesObtenerImagen, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (arrOpcionesObtenerImagen[i].equals(getResources().getString(R.string.opcionesImagenGaleria))) {
                    abrirGaleria();
                } else if (arrOpcionesObtenerImagen[i].equals(getResources().getString(R.string.opcionesImagenCamara))) {
                    abrirCamara();
                } else {
                    dialogInterface.dismiss();
                }
            }
        });

        builder.show();
    }

    private void abrirCamara() {
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentCamara.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentCamara, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(
                Intent.createChooser(intent, getResources().getString(R.string.tituloSeleccionaImagenPerfil))
                , SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == SELECT_PICTURE || requestCode == REQUEST_IMAGE_CAPTURE)
                && resultCode == RESULT_OK) {
            urlImagenPerfil = data.getData();

            Picasso.with(this).load(urlImagenPerfil).into(nvImagenPerfil);

            loadImagen();

        }
    }

    private void loadImagen() {
        nvImagenPerfil.setDrawingCacheEnabled(true);
        nvImagenPerfil.buildDrawingCache();
        Bitmap imagePerfil = nvImagenPerfil.getDrawingCache();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagePerfil.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef =
                storage.getReference("usuarios/" + Cache.get(Constantes.UID_USER_KEY, this));

        final UploadTask uploadTask = storageRef.putBytes(data);

        progressDialog = ProgressDialog.show(this, "Procesando", "Subiendo la foto");

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                try {
                    Map<String, Object> imagenUpdate = new HashMap<>();
                    /*imagenUpdate.put(
                            "/usuarios/"
                                    + Cache.get(Constantes.UID_USER_KEY, getApplicationContext())
                                    + "/urlImagen", taskSnapshot.getDownloadUrl());

                    db.updateChildren(imagenUpdate);*/
                    progressDialog.dismiss();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, e.getMessage());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerHistorial.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean nvItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.itemAdicionarIMC:
                initCalcularActivity();
                break;
            case R.id.itemDescripcionIMC:
                Intent intent = new Intent(this, DescripcionIMCActivity.class);
                startActivity(intent);
                break;
            case R.id.itemAcerca:
                Utilidades.dialogoError("Acerca de", "Desarrollado por Juan G Gomez - Devhack", this);
                break;
            case R.id.itemContactenos:
                Utilidades.dialogoError("Contactenos", "Contactenos a hola@devhack.co o 3174498336", this);
                break;
            case R.id.itemSalir:
                Cache.removeAll(this);
                Utilidades.initActivity(this, LoginActivity.class);
                finish();
                break;
        }

        drawerHistorial.closeDrawers();

        return true;
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
