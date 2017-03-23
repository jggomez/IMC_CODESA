package co.devhack.ejercicioimc.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.devhack.ejercicioimc.R;
import co.devhack.ejercicioimc.logica.LCalculoIMC;
import co.devhack.ejercicioimc.modelos.HistorialIMC;
import co.devhack.ejercicioimc.utilidades.Cache;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalculoFragment.OnCalculoInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalculoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculoFragment extends Fragment {

    // Request Code
    private static final int SELECT_PICTURE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 200;

    private CharSequence[] arrOpcionesObtenerImagen;
    private Uri urlImagenPerfil;

    @BindView(R.id.formularioTxtNombre)
    EditText txtNombre;

    @BindView(R.id.formularioTxtPeso)
    EditText txtPeso;

    @BindView(R.id.formularioTxtEstatura)
    EditText txtEstatura;

    @BindView(R.id.lblResultadoIMC)
    TextView lblResultadoIMC;

    @BindView(R.id.CalculoFragmentBtnAddImage)
    ImageButton CalculoFragmentBtnAddImage;

    private OnCalculoInteractionListener mListener;
    private double imc;

    public CalculoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CalculoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculoFragment newInstance() {
        CalculoFragment fragment = new CalculoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculo, container, false);

        ButterKnife.bind(this, view);

        arrOpcionesObtenerImagen = new CharSequence[]
                {getResources().getString(R.string.opcionesImagenCamara),
                        getResources().getString(R.string.opcionesImagenGaleria)};

        Picasso.with(getActivity()).load(R.mipmap.ic_launcher).into(CalculoFragmentBtnAddImage);

        return view;
    }

    @OnClick(R.id.btnCalcularIMC)
    public void clickCalcularIMC() {
        double peso = Double.parseDouble(txtPeso.getText().toString());
        double estatura = Double.parseDouble(txtEstatura.getText().toString());

        imc = LCalculoIMC.calcular(peso, estatura);

        lblResultadoIMC.setText("Su IMC es de : " + Math.round(imc));

        mListener.totalCalculo(imc);
    }

    @OnClick(R.id.btnAdicionarIMC)
    public void clickAdicionarIMC() {

        HistorialIMC historialIMC = new HistorialIMC();
        historialIMC.setNombreUsuario(txtNombre.getText().toString());
        historialIMC.setResultadoIMC(lblResultadoIMC.getText().toString());
        historialIMC.setPathImage(urlImagenPerfil);

        LCalculoIMC.addHistorial(historialIMC);

        mListener.adicionarIMC(historialIMC);
    }

    @OnClick(R.id.CalculoFragmentBtnAddImage)
    public void clickBtnAddImag() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
        if (intentCamara.resolveActivity(getActivity().getPackageManager()) != null) {
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

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK) {
            urlImagenPerfil = data.getData();
            Picasso.with(getActivity()).load(urlImagenPerfil).into(CalculoFragmentBtnAddImage);
        }

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            urlImagenPerfil = data.getData();
            Picasso.with(getActivity()).load(urlImagenPerfil).into(CalculoFragmentBtnAddImage);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCalculoInteractionListener) {
            mListener = (OnCalculoInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnCalculoInteractionListener {

        void totalCalculo(double imc);

        void adicionarIMC(HistorialIMC historialIMC);
    }
}
