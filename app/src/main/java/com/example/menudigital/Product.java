package com.example.menudigital;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Product {
    private String Nombre;
    private String Category;
    private Double Precio;
    private String Descripcion;
    private Bitmap imagen;
    private String ruta_imagen;
    private int Id;

    public Product() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public Product(int id, String nombre, String category, Double precio, String descripcion, int thumbnail) {
        Nombre = nombre;
        Category = category;
        Precio = precio;
        Descripcion = descripcion;
        Id = id;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }


    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }



    public String getNombre() {
        return Nombre;
    }

    public String getCategory() {
        return Category;
    }

    public Double getPrecio() {
        return Precio;
    }

    public String getDescripcion() {
        return Descripcion;
    }


}
