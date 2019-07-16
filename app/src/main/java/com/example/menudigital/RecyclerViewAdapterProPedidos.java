package com.example.menudigital;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.valueOf;

public class RecyclerViewAdapterProPedidos extends RecyclerView.Adapter<RecyclerViewAdapterProPedidos.MyViewHolder>  {
    int idPedido;
    private List<ProductosPedidos>  listaPedido;
    Context context;



    public RecyclerViewAdapterProPedidos(List<ProductosPedidos> mData, Context context) {
        this.listaPedido = mData;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pedidos_pasados, null, false);
        CardView cardView = (CardView)LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_nombre_pedidos, viewGroup, false);
        return new MyViewHolder(cardView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {


        myViewHolder.tvCantidad.setText(listaPedido.get(i).getCant()+"x ");
        myViewHolder.tvNombre.setText(listaPedido.get(i).getNombre());

        Toast.makeText(context, "ITEMS:"+getItemCount(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvCantidad, tvNombre;
        Button  btnAyuda;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCantidad=itemView.findViewById(R.id.tvCantidad);
            tvNombre=itemView.findViewById(R.id.tvNombre);
           // btnAyuda=itemView.findViewById(R.id.btnHelp);


        }
    }

}