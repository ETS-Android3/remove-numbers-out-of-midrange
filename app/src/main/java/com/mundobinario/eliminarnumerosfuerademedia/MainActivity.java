package com.mundobinario.eliminarnumerosfuerademedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etSerieNum;
    Button btAceptar, btAfinar;
    TextView tvResultado;
    String stSerieNmeros;
    // creamos un array para llenarlo con el resultado:
    List<Integer> listaResultado = new ArrayList<>();
    List<Integer> respaldoLista = new ArrayList<>();
    int media1, media2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSerieNum = findViewById(R.id.etNumerosIntroducidos);
        btAceptar = findViewById(R.id.btAceptar);
        btAfinar = findViewById(R.id.btAfinar);
        tvResultado = findViewById(R.id.tvResultado);

        btAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // obtenemos el array de numeros del tv
                stSerieNmeros = etSerieNum.getText().toString();
                if (stSerieNmeros.contains(",")) {
                    String[] arrayNumerosOriginal = stSerieNmeros.split(",");
                    for (int i = 0; i < arrayNumerosOriginal.length; i++) {
                        arrayNumerosOriginal[i] = arrayNumerosOriginal[i].trim();
                    }
                    if (arrayNumerosOriginal.length < 3) {
                        Toast.makeText(MainActivity.this, "mínimo 3 numeros", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    buscaResultado(arrayNumerosOriginal);
                } else {
                    Toast.makeText(MainActivity.this, "por favor, separe por comas", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btAfinar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaResultado == null) {
                    Toast.makeText(MainActivity.this, "Introduce antes la lista de números", Toast.LENGTH_SHORT).show();
                } else {
                    String[] arrayNumerosOriginal = new String[listaResultado.size()];
                    for (int i = 0; i < listaResultado.size(); i++) {
                        arrayNumerosOriginal[i] = "" + listaResultado.get(i);
                    }

                    buscaResultado(arrayNumerosOriginal);
                }

            }
        });

    }

    private void buscaResultado(String[] arrayNumerosOriginal) {

        // creamos copia por si el siguinete resultado da indice 0, recuperarlo
        respaldoLista.clear();
        if (listaResultado != null)
            respaldoLista.addAll(listaResultado.subList(0, listaResultado.size()));

        // haya media del array
        int total = 0;

        try {
            for (int i = 0; i < arrayNumerosOriginal.length; i++) {
                total += Integer.parseInt(arrayNumerosOriginal[i]);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this,
                    "Escriba correctamente:\n -Números sin espacios\n" +
                            " -Números sin comas ni puntos, ni nada que no sea números",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (arrayNumerosOriginal.length != 0) {
            media1 = total / arrayNumerosOriginal.length;
        } else {
            Toast.makeText(this, "Introduce una nueva cantidad", Toast.LENGTH_SHORT).show();
            return;
        }

        // crear nuevo array con mismo numero de indices

        int[] segundoArray = new int[arrayNumerosOriginal.length];

        // llenar el segundo array con el resultado abs de media - el numero

        int absMediaMenosIndice;
        for (int i = 0; i < arrayNumerosOriginal.length; i++) {
            absMediaMenosIndice = Math.abs(media1 - Integer.parseInt(arrayNumerosOriginal[i]));
            segundoArray[i] = absMediaMenosIndice;
        }

        // hayar la media2 del segundo array

        total = 0;
        for (int i = 0; i < segundoArray.length; i++) {
            total += segundoArray[i];
        }
        media2 = total / segundoArray.length;

        // elimina del primer array los numeros mayores a media1 + media2 o menores a media1 - media2
        int mediaSup = media1 + media2;
        int mediaInf = media1 - media2;

        listaResultado.clear();
        for (int i = 0; i < arrayNumerosOriginal.length; i++) {
            if (!(Integer.parseInt(arrayNumerosOriginal[i]) >= mediaSup)
                    && !(Integer.parseInt(arrayNumerosOriginal[i]) <= mediaInf)) {
                listaResultado.add(Integer.parseInt(arrayNumerosOriginal[i]));
            }
        }

        String resultado = "";
        String coma = ", ";
        if (listaResultado.size() == 0)
            // aqui se puede poner una bandera para parar el while si llegó al mínimo reslutado sin 0 indices
            listaResultado.addAll(respaldoLista.subList(0, respaldoLista.size()));
        for (int i = 0; i < listaResultado.size(); i++) {
            if (i == listaResultado.size() - 1) coma = "";
            resultado = resultado + listaResultado.get(i) + coma;
        }

        tvResultado.setText(resultado);

    }


}