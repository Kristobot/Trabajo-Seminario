package com.example.crudproductos;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AgregarProductoActivity extends AppCompatActivity {

    private EditText edtNombre, edtDescrip, edtPrecio, edtStock, edtUrl;
    private Button btnGuardar;
    private DatabaseHelper databaseHelper;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);

        edtNombre = findViewById(R.id.edtNombre);
        edtDescrip = findViewById(R.id.edtDescrip);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtStock = findViewById(R.id.edtStock);
        edtUrl = findViewById(R.id.edtUrl);
        btnGuardar = findViewById(R.id.btnGuardar);
        databaseHelper = new DatabaseHelper();

        // Crear un ExecutorService con un solo hilo
        executorService = Executors.newSingleThreadExecutor();

        btnGuardar.setOnClickListener(v -> guardarProducto());
    }

    private void guardarProducto() {
        String nombre = edtNombre.getText().toString().trim();
        String descrip = edtDescrip.getText().toString().trim();
        String precioStr = edtPrecio.getText().toString().trim();
        String stockStr = edtStock.getText().toString().trim();
        String url = edtUrl.getText().toString().trim();

        if (nombre.isEmpty() || descrip.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || url.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.parseDouble(precioStr);
        int stock = Integer.parseInt(stockStr);

        // Ejecutar la inserción en un hilo en segundo plano
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // Llamamos al método de la base de datos para insertar el producto
                    databaseHelper.insertarProducto(nombre, descrip, precio, stock, url);

                    // Usar runOnUiThread para actualizar la interfaz en el hilo principal
                    runOnUiThread(() -> {
                        Toast.makeText(AgregarProductoActivity.this, "Producto agregado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    });

                } catch (Exception e) {
                    e.printStackTrace();

                    // Usar runOnUiThread para manejar el error en la interfaz principal
                    runOnUiThread(() -> {
                        Toast.makeText(AgregarProductoActivity.this, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }
}
