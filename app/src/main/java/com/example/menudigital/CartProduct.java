package com.example.menudigital;

import android.graphics.Bitmap;

public class CartProduct {
    private int id;
    private int idProduct;
    private String nombre;
    private String descripcion;
    private double precio;
    private Bitmap icono;

    public CartProduct() {
    }

    public CartProduct(int id, int idProduct, String nombre, String descripcion, double precio, Bitmap icono) {
        this.id = id;
        this.idProduct = idProduct;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.icono = icono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Bitmap getIcono() {
        return icono;
    }

    public void setIcono(Bitmap icono) {
        this.icono = icono;
    }
}
