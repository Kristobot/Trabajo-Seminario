package com.example.crudproductos;

import android.content.Context;
import com.example.crudproductos.Modelo.Producto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    // Detalles de la conexión a la base de datos
    private static final String JDBC_URL = "jdbc:sqlserver://bd-ra.database.windows.net:1433;database=trabajo-seminario-5;user=pm-cris@bd-ra;password=@Hola456123;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    // Método para obtener la conexión
    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Error de conexión a la base de datos: " + e.getMessage());
        }
    }

    // Método para insertar un producto en SQL Server
    public void insertarProducto(String nombre, String descripcion, double precio, int stock, String url) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO productos (nombre, descrip, precio, stock, url) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, descripcion);
                ps.setDouble(3, precio);
                ps.setInt(4, stock);
                ps.setString(5, url);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para obtener todos los productos desde SQL Server
    public List<Producto> obtenerProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM productos";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descrip");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");
                    String url = rs.getString("url");
                    listaProductos.add(new Producto(id, nombre, descripcion, precio, stock, url));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaProductos;
    }

    // Método para actualizar un producto en SQL Server
    public int actualizarProducto(int id, String nombre, String descripcion, double precio, int stock, String url) {
        int filasAfectadas = 0;
        try (Connection conn = getConnection()) {
            String sql = "UPDATE productos SET nombre = ?, descrip = ?, precio = ?, stock = ?, url = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, nombre);
                ps.setString(2, descripcion);
                ps.setDouble(3, precio);
                ps.setInt(4, stock);
                ps.setString(5, url);
                ps.setInt(6, id);
                filasAfectadas = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    // Método para eliminar un producto en SQL Server
    public int eliminarProducto(int id) {
        int filasAfectadas = 0;
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM productos WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                filasAfectadas = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    // Método para contar la cantidad de productos en la base de datos
    public int contarRegistros() {
        int count = 0;
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM productos";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}