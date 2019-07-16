package com.example.menudigital;


public class PedidoPasado {
    private int Num;
    private String Fecha;
    private Double Total;
    private String Estado;



    public PedidoPasado() {
    }

    public PedidoPasado(int num, String fecha, Double total, String estado) {
        Num = num;
        Fecha = fecha;
        Total = total;
        Estado = estado;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }
}
