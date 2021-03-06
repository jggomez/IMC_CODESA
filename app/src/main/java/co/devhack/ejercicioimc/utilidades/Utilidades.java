package co.devhack.ejercicioimc.utilidades;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import co.devhack.ejercicioimc.R;

/**
 * Created by jggomez on 23-Mar-17.
 */

public class Utilidades {

    public static <T extends AppCompatActivity> void initActivity(T classInicio,
                                                                  Class classDestino) {
        Intent intent = new Intent(classInicio, classDestino);
        classInicio.startActivity(intent);
    }

    public static void dialogoInfo(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.informacion, context);
    }

    public static void dialogoError(String titulo, String mensaje, Context context) {
        dialogo(titulo, mensaje, tipoDialogEnum.error, context);
    }

    private static void dialogo(String titulo, String mensaje, tipoDialogEnum tipoDialog, Context context) {

        AlertDialog.Builder dialog = null;

        switch (tipoDialog) {
            case informacion:
                dialog = new AlertDialog.Builder(context, R.style.DialogInfo);
                break;
            case error:
                dialog = new AlertDialog.Builder(context, R.style.DialogError);
                break;
        }


        dialog.setTitle(titulo);
        dialog.setMessage(mensaje);
        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    private enum tipoDialogEnum {
        error,
        informacion
    }
}
