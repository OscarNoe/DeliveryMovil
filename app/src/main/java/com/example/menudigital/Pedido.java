package com.example.menudigital;

import android.graphics.Bitmap;

public class Pedido {
    private int id;
    private String Titulo;
    private String Descripcion;
    private double precio;
    private  int idOrden;
    private Bitmap img;
    public Pedido() {
    }

    public Pedido(int id, String titulo, String descripcion, double precio, int idOrden, Bitmap img) {
        this.id = id;
        Titulo = titulo;
        Descripcion = descripcion;
        this.precio = precio;
        this.idOrden = idOrden;
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }
}
