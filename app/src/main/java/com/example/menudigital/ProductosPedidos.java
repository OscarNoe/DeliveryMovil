package com.example.menudigital;

public class ProductosPedidos {
    int cant;
    String nombre;
    public ProductosPedidos() {
    }

    public ProductosPedidos(int cant, String nombre) {
        this.cant = cant;
        this.nombre = nombre;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
