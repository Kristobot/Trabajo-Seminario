package com.example.crudproductos.Modelo;

public class Producto {
    //Atributos de la tabla Productos
    private int id;
    private String nombre;
    private String descrip;
    private double precio;
    private int stock;
    private String url;

    //Métodos


    public Producto() {

    }

    public Producto(int id, String nombre, String descrip, double precio, int stock, String url) {
        this.id = id;
        this.nombre = nombre;
        this.descrip = descrip;
        this.precio = precio;
        this.stock = stock;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descrip='" + descrip + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", url='" + url + '\'' +
                '}';
    }
}
