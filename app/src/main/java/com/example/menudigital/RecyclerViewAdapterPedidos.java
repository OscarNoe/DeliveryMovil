package com.example.menudigital;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.RelativeLayout.BELOW;
import static java.lang.String.valueOf;

public class RecyclerViewAdapterPedidos  extends RecyclerView.Adapter<RecyclerViewAdapterPedidos.MyViewHolder> {
    ArrayList<ListProductPedidos> lstProductos;
    RequestQueue request;
    ImageView image;



    Context context;



    public RecyclerViewAdapterPedidos(ArrayList<ListProductPedidos> mProduct, Context context) {
        this.lstProductos=mProduct;
        this.context = context;

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        //View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_pedidos_pasados, null, false);
        CardView cardView = (CardView)LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_pedidos_pasados, viewGroup, false);
        return new MyViewHolder(cardView);

    }
    int z=0;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {

        z=i;
        myViewHolder.tvNum.setText("Pedidos #"+lstProductos.get(i).pedidoPasado.getNum());
       myViewHolder.tvFecha.setText(lstProductos.get(i).pedidoPasado.getFecha());
        myViewHolder.tvTotal.setText("Total: $"+lstProductos.get(i).pedidoPasado.getTotal());
        myViewHolder.tvEstado.setText(lstProductos.get(i).pedidoPasado.getEstado());
        request = Volley.newRequestQueue(context);
        cargarProductos(lstProductos.get(i).lstProduct);
        //lstProduct.add(new ProductosPedidos(2, "HAA"));
        //lstProduct.add(new ProductosPedidos(1, "Hamburguesa"));



    }

    private void cargarProductos(ArrayList<ProductosPedidos> lstProduct) {

            for(int i = 0; i<lstProduct.size();i++){

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

                View view = inflater.inflate(R.layout.card_nombre_pedidos, null);
                view.bringToFront();
                TextView tvCantidad, tvNombre;
                tvCantidad=view.findViewById(R.id.tvCantidad);
                tvNombre=view.findViewById(R.id.tvNombre);
                tvCantidad.setText(lstProduct.get(i).getCant()+"x");
                tvNombre.setText(lstProduct.get(i).getNombre());
                MyViewHolder.container.addView(view);
            }


        }















    /*private void cargarProductos() {
        progress=new ProgressDialog(context);
        progress.setMessage("Buscando los pedidos. . .");
        progress.show();

        String url = "http://"+context.getResources().getString(R.string.ip)+"/bdsistemapos/wsJSONConsultarProductosPedidos.php?pedido="+
                String.valueOf(listaPedido.get(z).getNum());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }*/

    @Override
    public int getItemCount() {
        return lstProductos.size();
    }

   /* @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(context, " "+response, Toast.LENGTH_SHORT).show();
        JSONArray json = response.optJSONArray("pedidos");
        try {
            for (int i = 0; i < json.length(); i++) {
                productosPedidos = new ProductosPedidos();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);


                productosPedidos.setCant(jsonObject.optInt("repeticion"));
                productosPedidos.setNombre(jsonObject.optString("nombre"));

                lstProduct.add(productosPedidos);
                progress.hide();
            }


        } catch (JSONException e) {

            e.printStackTrace();
            Toast.makeText(context, "Error al conectarse", Toast.LENGTH_LONG).show();
            progress.hide();
        }
        RecyclerViewAdapterProPedidos adapt = new RecyclerViewAdapterProPedidos(lstProduct,context);
        MyViewHolder.myrv.setAdapter(adapt);
    }
*/






    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvNum, tvTotal, tvFecha, tvEstado;
        //public static RecyclerView myrv;
        Button btnRecibo, btnAyuda;
       //9 public static LinearLayout linearLayout;
        public static LinearLayout container ;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            tvNum = itemView.findViewById(R.id.tvNum);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvEstado=itemView.findViewById(R.id.tvEstado);
            //linearLayout=itemView.findViewById(R.id.ll2);
            container = itemView.findViewById(R.id.ll2);
            //myrv = itemView.findViewById(R.id.recyclerview_id);
        }
    }

}
