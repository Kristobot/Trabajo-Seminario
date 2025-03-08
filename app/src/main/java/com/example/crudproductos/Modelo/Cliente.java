package com.example.crudproductos.Modelo;

public class Cliente {
    private int id;

    private String ruc;
    private String nom;
    private String dir;

    public Cliente() {
    }

    public Cliente(int id, String ruc, String nom, String dir) {
        this.id = id;
        this.ruc = ruc;
        this.nom = nom;
        this.dir = dir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", ruc='" + ruc + '\'' +
                ", nom='" + nom + '\'' +
                ", dir='" + dir + '\'' +
                '}';
    }
}
