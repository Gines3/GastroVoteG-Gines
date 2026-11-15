package es.dam.gastrovote.rodriguezmorales;
//Ginés Rodríguez Morales 2 DAM
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.RadioGroup;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
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

    private boolean[] baresValorados = new boolean[8];
    private boolean logroDesbloqueado = false;

    private TextView tvProgresoRuta;
    private ProgressBar pbRuta;

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
    // Puntuacion y opinion de la resena
    private RatingBar rbPuntuacion;
    private TextView tvOpinionPuntuacion;

    // Tamaño ración
     private RadioGroup rgTamano;
    // Calidad-precio
    private SeekBar sbCalidadPrecio;
    private TextView tvCalidadPrecio;
    private CheckBox cbVegetariana;
    private CheckBox cbSinGluten;
    private CheckBox cbPicante;

    private EditText etBar;
    private EditText etTapa;
    private EditText etPrecio;
    private Switch swMeGusta;
    private TextView tvMeGusta;
    private CheckBox cbEstudiante;
    private TextView tvResumen;
    private EditText etComentario;
    private TextView tvContadorCaracteres;

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

        tvProgresoRuta = findViewById(R.id.tvProgresoRuta);
        pbRuta = findViewById(R.id.pbRuta);


        etBar = findViewById(R.id.etBar);
        etTapa = findViewById(R.id.etTapa);
        etPrecio = findViewById(R.id.etPrecio);
        swMeGusta = findViewById(R.id.swMeGusta);
        tvMeGusta = findViewById(R.id.tvMeGusta);
        cbEstudiante = findViewById(R.id.cbEstudiante);
        tvResumen = findViewById(R.id.tvResumen);
        //ampliacion 0/200
        etComentario = findViewById(R.id.etComentario);
        tvContadorCaracteres = findViewById(R.id.tvContadorCaracteres);

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
        rbPuntuacion = findViewById(R.id.rbPuntuacion);
        tvOpinionPuntuacion = findViewById(R.id.tvOpinionPuntuacion);
        rgTamano = findViewById(R.id.rgTamano);
        cbVegetariana = findViewById(R.id.cbVegetariana);
        cbSinGluten = findViewById(R.id.cbSinGluten);
        cbPicante = findViewById(R.id.cbPicante);
        //sb = seekbar, tv = textView
        sbCalidadPrecio = findViewById(R.id.sbCalidadPrecio);
        tvCalidadPrecio = findViewById(R.id.tvCalidadPrecio);
        btnFavorito = findViewById(R.id.btnFavorito);

        Button btnLimpiar = findViewById(R.id.btnLimpiar);
        Button btnPublicarResena = findViewById(R.id.btnPublicarResena);

        etComentario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvContadorCaracteres.setText(s.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        sbCalidadPrecio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCalidadPrecio.setText(getString(R.string.calidad_precio_valor, progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


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

        btnPublicarResena.setOnClickListener(v -> publicarResena());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());

        rbPuntuacion.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {

            if (rating == 0) {
                tvOpinionPuntuacion.setText(R.string.opinion_sin_puntuar);
            } else if (rating <= 2) {
                tvOpinionPuntuacion.setText(R.string.opinion_mejorable);
            } else if (rating <= 3) {
                tvOpinionPuntuacion.setText(R.string.opinion_correcta);
            } else if (rating <= 4) {
                tvOpinionPuntuacion.setText(R.string.opinion_muy_buena);
            } else {
                tvOpinionPuntuacion.setText(R.string.opinion_espectacular);
            }
        });
        //para que siempre muestre el primer bar al iniciar
        actualizarBar();
    }

    private void publicarResena() {
        if (rbPuntuacion.getRating() == 0) {
            Toast.makeText(this, R.string.toast_puntuar, Toast.LENGTH_SHORT).show();
            return;
        }
        double precioBase;

        try {
            precioBase = Double.parseDouble(preciosBase[barActual].replace(',', '.'));
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.toast_precio_valido, Toast.LENGTH_SHORT).show();
            return;
        }

        double multiplicador = 1;
        int tamanoSeleccionado = rgTamano.getCheckedRadioButtonId();
        String tamano;

        if (tamanoSeleccionado == R.id.rbMediaRacion) {
            multiplicador = 2;
            tamano = getString(R.string.tamano_media_racion);
        } else if (tamanoSeleccionado == R.id.rbRacion) {
            multiplicador = 3;
            tamano = getString(R.string.tamano_racion);
        } else {
            tamano = getString(R.string.tamano_tapa);
        }

        double precio = precioBase * multiplicador;

        double descuento = 0;

        if (cbEstudiante.isChecked()) {
            descuento = precio * 0.10;
        }

        double precioFinal = precio - descuento;

        String meGusta = swMeGusta.isChecked()
                ? getString(R.string.si)
                : getString(R.string.no);
        String etiquetas = "";

        if (cbVegetariana.isChecked()) {
            etiquetas += getString(R.string.vegetariana) + ", ";
        }

        if (cbSinGluten.isChecked()) {
            etiquetas += getString(R.string.sin_gluten) + ", ";
        }

        if (cbPicante.isChecked()) {
            etiquetas += getString(R.string.picante) + ", ";
        }

        if (etiquetas.isEmpty()) {
            etiquetas = getString(R.string.etiquetas_ninguna);
        } else {
            etiquetas = etiquetas.substring(0, etiquetas.length() - 2);
        }


        String comentario = etComentario.getText().toString().trim();
        //si no escribe reseña, saldra que no escribio reseña
        if (comentario.isEmpty()) {
            comentario = getString(R.string.etiquetas_ninguna);
        }


        DecimalFormat formato = new DecimalFormat(
                "0.00",
                new DecimalFormatSymbols(new Locale("es", "ES"))
        );


        String resumen = getString(
                R.string.resumen_resena,
                nombresTapas[barActual],
                nombresBares[barActual],
                tamano,
                formato.format(precioBase),
                formato.format(precio),
                formato.format(descuento),
                formato.format(precioFinal),
                formato.format(rbPuntuacion.getRating()),
                sbCalidadPrecio.getProgress(),
                meGusta,
                etiquetas,
                comentario
        );

        tvResumen.setText(resumen);

        Toast.makeText(this, R.string.toast_resena_publicada, Toast.LENGTH_SHORT).show();
        actualizarProgresoRuta();
    }

     private void limpiarFormulario() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_limpiar_titulo)
                .setMessage(R.string.dialog_limpiar_mensaje)
                .setPositiveButton(R.string.dialog_limpiar_confirmar, (dialog, which) -> {

                    rbPuntuacion.setRating(0);

                    rgTamano.check(R.id.rbTapa);

                    sbCalidadPrecio.setProgress(5);

                    cbVegetariana.setChecked(false);
                    cbSinGluten.setChecked(false);
                    cbPicante.setChecked(false);

                    swMeGusta.setChecked(false);
                    tvMeGusta.setText(R.string.me_gusta_inicial);

                    cbEstudiante.setChecked(false);

                    etComentario.setText("");

                    tvOpinionPuntuacion.setText(R.string.opinion_sin_puntuar);

                    tvResumen.setText(R.string.resumen_inicial);

                    Toast.makeText(this, R.string.toast_formulario_limpio,
                            Toast.LENGTH_SHORT).show();

                })
                .setNegativeButton(R.string.dialog_limpiar_cancelar, null)
                .show();
    }
    private void actualizarProgresoRuta() {

        baresValorados[barActual] = true;

        int cantidadBares = 0;

        for (boolean valorado : baresValorados) {
            if (valorado) {
                cantidadBares++;
            }
        }

        pbRuta.setProgress(cantidadBares);

        tvProgresoRuta.setText(
                getString(R.string.progreso_ruta, cantidadBares)
        );

        if (cantidadBares == 3 && !logroDesbloqueado) {

            logroDesbloqueado = true;

            new AlertDialog.Builder(this)
                    .setTitle(R.string.logro_titulo)
                    .setMessage(R.string.logro_mensaje)
                    .setPositiveButton(R.string.aceptar, null)
                    .show();
        }
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
