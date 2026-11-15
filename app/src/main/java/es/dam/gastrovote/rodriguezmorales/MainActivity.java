package es.dam.gastrovote.rodriguezmorales;
//Ginés Rodríguez Morales 2 DAM

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Arrays para almacenar
    // los datos de los bares y sus tapas
    private String[] nombresBares;
    private String[] nombresTapas;
    private String[] descripcionesTapas;
    private String[] preciosBase;
    private String[] valoraciones;

    //para guardar si esta marcado como favorito
     private boolean[] favoritos = new boolean[8];

    //posicion del bar actual
     private int barActual = 0;

    // Elementos del selector de bares
    private Button btnBarAnterior;
    private Button btnBarSiguiente;
    private TextView tvNombreBar;
    private TextView tvPosicionBar;

    // Información de la tapa seleccionada
    private TextView tvNombreTapa;
    private TextView tvDescripcionTapa;
    private TextView tvPrecioBase;
    private ImageButton btnFavorito;
    private RatingBar rbMediaTapa;

    private EditText etBar;
    private EditText etTapa;
    private EditText etPrecio;
    private Switch swMeGusta;
    private TextView tvMeGusta;
    private CheckBox cbEstudiante;
    private TextView tvResumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnFavorito = findViewById(R.id.btnFavorito);

        //cargo datos del array.xml
        nombresBares = getResources().getStringArray(R.array.bares);
        nombresTapas = getResources().getStringArray(R.array.tapas);
        descripcionesTapas = getResources().getStringArray(R.array.descripciones_tapas);
        preciosBase = getResources().getStringArray(R.array.precios_base);
        valoraciones = getResources().getStringArray(R.array.valoraciones);

        etBar = findViewById(R.id.etBar);
        etTapa = findViewById(R.id.etTapa);
        etPrecio = findViewById(R.id.etPrecio);
        swMeGusta = findViewById(R.id.swMeGusta);
        tvMeGusta = findViewById(R.id.tvMeGusta);
        cbEstudiante = findViewById(R.id.cbEstudiante);
        tvResumen = findViewById(R.id.tvResumen);

        // Conectar los elementos del selector
        btnBarAnterior = findViewById(R.id.btnBarAnterior);
        btnBarSiguiente = findViewById(R.id.btnBarSiguiente);
        tvNombreBar = findViewById(R.id.tvNombreBar);
        tvPosicionBar = findViewById(R.id.tvPosicionBar);

        // Conectar los elementos de la tapa
        tvNombreTapa = findViewById(R.id.tvNombreTapa);
        tvDescripcionTapa = findViewById(R.id.tvDescripcionTapa);
        tvPrecioBase = findViewById(R.id.tvPrecioBase);
        rbMediaTapa = findViewById(R.id.rbMediaTapa);
        btnFavorito = findViewById(R.id.btnFavorito);

        Button btnLimpiar = findViewById(R.id.btnLimpiar);
        Button btnPublicarResena = findViewById(R.id.btnPublicarResena);

        //llenar el corazon , favorito llamando al metodo actualizarIcono
        btnFavorito.setOnClickListener(v -> {
            favoritos[barActual] = !favoritos[barActual];
            actualizarIconoFavorito();
        });
        // Seleccionar el bar anterior
        btnBarAnterior.setOnClickListener(v -> {
            barActual--;

            if (barActual < 0) {
                barActual = nombresBares.length - 1;
            }

            actualizarBar();
        });
        // Seleccionar el siguiente bar
        btnBarSiguiente.setOnClickListener(v -> {
            barActual++;

            if (barActual >= nombresBares.length) {
                barActual = 0;
            }

            actualizarBar();
        });
        swMeGusta.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                tvMeGusta.setText(R.string.me_gusta_activado);
            } else {
                tvMeGusta.setText(R.string.me_gusta_inicial);
            }
        });

        cbEstudiante.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(this, R.string.toast_carnet, Toast.LENGTH_SHORT).show();
            }
        });

        btnPublicarResena.setOnClickListener(v -> calcularPrecio());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
        //para que siempre muestre el primer bar al iniciar
        actualizarBar();
    }

    private void calcularPrecio() {
        String bar = etBar.getText().toString().trim();
        String tapa = etTapa.getText().toString().trim();
        String precioTexto = etPrecio.getText().toString().trim();

        if (TextUtils.isEmpty(bar) || TextUtils.isEmpty(tapa)) {
            Toast.makeText(this, R.string.toast_bar_tapa, Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(precioTexto)) {
            Toast.makeText(this, R.string.toast_precio_valido, Toast.LENGTH_SHORT).show();
            return;
        }

        double precio;
        try {
            precio = Double.parseDouble(precioTexto.replace(',', '.'));
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.toast_precio_valido, Toast.LENGTH_SHORT).show();
            return;
        }

        if (precio <= 0 || Double.isInfinite(precio) || Double.isNaN(precio)) {
            Toast.makeText(this, R.string.toast_precio_valido, Toast.LENGTH_SHORT).show();
            return;
        }

        double descuento = 0;
        if (cbEstudiante.isChecked()) {
            descuento = precio * 0.10;
        }

        double precioFinal = precio - descuento;
        String meGusta = swMeGusta.isChecked()
                ? getString(R.string.si)
                : getString(R.string.no);

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols(new Locale("es", "ES"));
        DecimalFormat formato = new DecimalFormat("0.00", simbolos);

        String resumen;
        if (cbEstudiante.isChecked()) {
            resumen = getString(R.string.resumen_con_descuento,
                    tapa, bar, formato.format(precio), formato.format(descuento),
                    formato.format(precioFinal), meGusta);
        } else {
            resumen = getString(R.string.resumen_sin_descuento,
                    tapa, bar, formato.format(precio), formato.format(precioFinal), meGusta);
        }

        tvResumen.setText(resumen);
        Toast.makeText(this, R.string.toast_precio_calculado, Toast.LENGTH_SHORT).show();
    }

    private void limpiarFormulario() {
        etBar.setText("");
        etTapa.setText("");
        etPrecio.setText("");
        swMeGusta.setChecked(false);
        cbEstudiante.setChecked(false);
        tvMeGusta.setText(R.string.me_gusta_inicial);
        tvResumen.setText(R.string.precio_final_inicial);

        Toast.makeText(this, R.string.toast_formulario_limpio, Toast.LENGTH_SHORT).show();
    }
    private void actualizarIconoFavorito() {
        if (favoritos[barActual]) {
            btnFavorito.setImageResource(R.drawable.ic_corazon_lleno);
        } else {
            btnFavorito.setImageResource(R.drawable.ic_corazon_vacio);
        }
    }
    // Actualizar la información del bar seleccionado
    private void actualizarBar() {
        tvNombreBar.setText(nombresBares[barActual]);
        tvPosicionBar.setText(getString(R.string.posicion_bar, barActual + 1, nombresBares.length));
        tvNombreTapa.setText(nombresTapas[barActual]);
        tvDescripcionTapa.setText(descripcionesTapas[barActual]);
        tvPrecioBase.setText(getString(R.string.precio_base, preciosBase[barActual]));
        rbMediaTapa.setRating(Float.parseFloat(valoraciones[barActual]));

        actualizarIconoFavorito();
    }

}
