package es.dam.gastrovote.rodriguezmorales;
//Ginés Rodríguez Morales 2 DAM

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

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

        etBar = findViewById(R.id.etBar);
        etTapa = findViewById(R.id.etTapa);
        etPrecio = findViewById(R.id.etPrecio);
        swMeGusta = findViewById(R.id.swMeGusta);
        tvMeGusta = findViewById(R.id.tvMeGusta);
        cbEstudiante = findViewById(R.id.cbEstudiante);
        tvResumen = findViewById(R.id.tvResumen);

        Button btnLimpiar = findViewById(R.id.btnLimpiar);
        Button btnCalcular = findViewById(R.id.btnCalcular);

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

        btnCalcular.setOnClickListener(v -> calcularPrecio());
        btnLimpiar.setOnClickListener(v -> limpiarFormulario());
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
}
