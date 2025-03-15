package com.example.crudproductos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EditarProductoActivity extends AppCompatActivity {
    EditText edtNombre, edtDescrip, edtPrecio, edtStock, edtUrl;
    Button btnActualizar, btnCancelar, btnEliminar;
    DatabaseHelper databaseHelper;
    int productoId;
    ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);

        edtNombre = findViewById(R.id.edtNombre);
        edtDescrip = findViewById(R.id.edtDescrip);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtStock = findViewById(R.id.edtStock);
        edtUrl = findViewById(R.id.edtUrl);

        btnActualizar = findViewById(R.id.btnActualizar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btnEliminar);
        databaseHelper = new DatabaseHelper();

        // Crear un ExecutorService con un solo hilo
        executorService = Executors.newSingleThreadExecutor();

        // Obtener datos enviados desde el RecyclerView
        Intent intent = getIntent();
        if (intent != null) {
            productoId = intent.getIntExtra("id", -1);
            String nombre = intent.getStringExtra("nombre");
            String descrip = intent.getStringExtra("descrip");
            double precio = intent.getDoubleExtra("precio", 0.0);
            int stock = intent.getIntExtra("stock", 0);
            String url = intent.getStringExtra("url");

            edtNombre.setText(nombre);
            edtDescrip.setText(descrip);
            edtPrecio.setText(String.valueOf(precio));
            edtStock.setText(String.valueOf(stock));
            edtUrl.setText(url);
        }

        // Botón para actualizar el producto
        btnActualizar.setOnClickListener(view -> {
            String nuevoNombre = edtNombre.getText().toString();
            String nuevoDescrip = edtDescrip.getText().toString();
            double nuevoPrecio = Double.parseDouble(edtPrecio.getText().toString());
            int nuevoStock = Integer.parseInt(edtStock.getText().toString());
            String nuevoUrl = edtUrl.getText().toString();

            // Ejecutar la actualización en un hilo en segundo plano
            executorService.execute(() -> {
                try {
                    // Intentar actualizar el producto en la base de datos
                    boolean actualizado = databaseHelper.actualizarProducto(productoId, nuevoNombre, nuevoDescrip, nuevoPrecio, nuevoStock, nuevoUrl) > 0;

                    // Usar runOnUiThread para actualizar la interfaz de usuario en el hilo principal
                    runOnUiThread(() -> {
                        if (actualizado) {
                            Toast.makeText(EditarProductoActivity.this, "Producto actualizado", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(EditarProductoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(EditarProductoActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show());
                }
            });
        });

        // Botón para cancelar la edición
        btnCancelar.setOnClickListener(view -> finish());

        // Botón para eliminar producto
        btnEliminar.setOnClickListener(view -> mostrarDialogoConfirmacion());
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Producto")
                .setMessage("¿Estás seguro que deseas eliminar este producto?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Ejecutar la eliminación en un hilo en segundo plano
                    executorService.execute(() -> {
                        try {
                            boolean eliminado = databaseHelper.eliminarProducto(productoId) > 0;

                            // Usar runOnUiThread para actualizar la interfaz de usuario en el hilo principal
                            runOnUiThread(() -> {
                                if (eliminado) {
                                    Toast.makeText(EditarProductoActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(EditarProductoActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            runOnUiThread(() -> Toast.makeText(EditarProductoActivity.this, "Error al eliminar", Toast.LENGTH_SHORT).show());
                        }
                    });
                })
                .setNegativeButton("No", null)
                .show();
    }
}
