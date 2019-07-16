package com.example.menudigital;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.menudigital.entidades.ConexionSQLiteHelper;

import java.util.List;

import static java.lang.String.valueOf;

public class RecyclerViewAdapterCart extends RecyclerView.Adapter<RecyclerViewAdapterCart.MyViewHolder>  {
    private List<CartProduct>  listaProducto;
    Context context;
    int p=0;
    ConexionSQLiteHelper conn;


    public RecyclerViewAdapterCart(List<CartProduct> mData, Context context) {
        this.listaProducto = mData;
        this.context = context;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_product,  viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {


        myViewHolder.tv_product_desc.setText(listaProducto.get(i).getDescripcion());
        myViewHolder.tv_product_nombre.setText(listaProducto.get(i).getNombre());
        myViewHolder.tv_product_precio.setText(valueOf(listaProducto.get(i).getPrecio()));

        myViewHolder.tvModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myViewHolder.tvEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conn = new ConexionSQLiteHelper(context, "bd_pedidos",null,1);
                SQLiteDatabase db = conn.getWritableDatabase();
                db.execSQL("DELETE FROM pedidos WHERE idOrden = "+listaProducto.get(i).getId());

               // Toast.makeText(context, "Se eliminó correctamente.", Toast.LENGTH_SHORT).show();
                try {
                    ((CartActivity) v.getContext()).lstProduct.remove(i);
                    ((CartActivity) v.getContext()).SumarTotal();
                    ((CartActivity) v.getContext()).adapt.notifyItemRemoved(i);
                    ((CartActivity) v.getContext()).adapt.notifyItemRangeChanged(i, getItemCount());
                    ((CartActivity) v.getContext()).RecyclerVisible();
                    //((CartActivity) v.getContext()).llenarLista();

                } catch (Exception e) {
                    //ignore
                }
            }
        });

        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), listaProducto.get(i).getIcono());
        roundedDrawable.setCircular(true);
        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(800);

        //myViewHolder.img_product_icon.setImageBitmap(listaProducto.get(i).getIcono());
        myViewHolder.img_product_icon.setImageDrawable(roundedDrawable);



    }




    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_product_nombre, tv_product_precio, tv_product_desc, tvModificar, tvEliminar;
        ImageView img_product_icon;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvEliminar = itemView.findViewById(R.id.tvEliminar);
            tvModificar = itemView.findViewById(R.id.tvModificar);
            tv_product_desc = itemView.findViewById(R.id.tvDescr);
            tv_product_nombre = itemView.findViewById(R.id.tvTitulo);
            tv_product_precio = itemView.findViewById(R.id.tvPrecio);
            img_product_icon = itemView.findViewById(R.id.ivIcono);

        }
    }
}
