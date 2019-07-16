package com.example.menudigital;

import java.util.ArrayList;

public class ListProductPedidos {
    ArrayList<ProductosPedidos> lstProduct;
    PedidoPasado pedidoPasado;
    public ListProductPedidos() {
    }

    public ListProductPedidos(ArrayList<ProductosPedidos> lstProduct, PedidoPasado pedidoPasado) {
        this.lstProduct = lstProduct;
        this.pedidoPasado = pedidoPasado;
    }

    public PedidoPasado getPedidoPasado() {
        return pedidoPasado;
    }

    public void setPedidoPasado(PedidoPasado pedidoPasado) {
        this.pedidoPasado = pedidoPasado;
    }

    public ArrayList<ProductosPedidos> getLstProduct() {
        return lstProduct;
    }

    public void setLstProduct(ArrayList<ProductosPedidos> lstProduct) {
        this.lstProduct = lstProduct;
    }
}
