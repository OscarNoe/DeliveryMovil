package com.example.menudigital;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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

import java.io.ByteArrayOutputStream;
import java.util.List;

import static java.lang.String.valueOf;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>  {
    private List<Product>  listaProducto;
    RequestQueue request;
    Context context;
    Bitmap bmap=null;
    Boolean posible=true;
    int p=0;



    public RecyclerViewAdapter( List<Product> mData, Context context) {
        this.listaProducto = mData;
        this.context = context;
        request= Volley.newRequestQueue(context);
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_item_product, null, false);
        return new MyViewHolder(view);
    }
    CartProduct cartProduct=null;
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_product_desc.setText(listaProducto.get(i).getDescripcion());
        myViewHolder.tv_product_nombre.setText(listaProducto.get(i).getNombre());
        myViewHolder.tv_product_precio.setText(valueOf(listaProducto.get(i).getPrecio()));
        final dialogProduct frag;
        frag = dialogProduct.newInstance("Titulo jaja");
        cartProduct = new CartProduct();
        cartProduct.setIdProduct(listaProducto.get(i).getId());
        //Toast.makeText(context, "ID ES "+cartProduct.getIdProduct(), Toast.LENGTH_SHORT).show();
        cartProduct.setNombre(listaProducto.get(i).getNombre());
        cartProduct.setDescripcion(listaProducto.get(i).getDescripcion());
        cartProduct.setPrecio(listaProducto.get(i).getPrecio());
        final ContentValues values = new ContentValues();
        values.put("id", String.valueOf(cartProduct.getIdProduct()));
        values.put("titulo", cartProduct.getNombre());
        values.put("descripcion", cartProduct.getDescripcion());
        values.put("precio", String.valueOf(cartProduct.getPrecio()));


        frag.setId(listaProducto.get(i).getId());
        if(listaProducto.get(i).getRuta_imagen()!=null) {
            //myViewHolder.img_product_thumbnail.setImageBitmap(listaProducto.get(i).getImagen());
            cargarImagen(listaProducto.get(i).getRuta_imagen() , myViewHolder, frag, values);

        }else{
            myViewHolder.img_product_thumbnail.setImageResource(R.drawable.producto1);
        }






        myViewHolder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(context, "bd_pedidos",null,1);
                SQLiteDatabase db= conn.getWritableDatabase();
                Toast.makeText(context, "Se agregó el producto a su carrito.", Toast.LENGTH_SHORT).show();
                Long idResultado = db.insert("pedidos", "idOrden", values);
                //Toast.makeText(context, "IdRegistro= "+ idResultado, Toast.LENGTH_SHORT).show();

            }
        });

        //bmap= null;
        myViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cuando se da click en un LinearLayout del CardView

                if(frag != null && frag.isAdded() ) {


                    return;
                }else{

                        frag.show(((PrincipalActivity)context).getSupportFragmentManager(),"Title" );
                        posible=false;


                }


            }
        });


    }




    private void cargarImagen(String ruta_imagen, final MyViewHolder myViewHolder, final dialogProduct frag, final ContentValues values) {
        String urlimg = "http://"+context.getResources().getString(R.string.ip)+"/bdSistemaPOS/"+ruta_imagen;
        urlimg = urlimg.replace(" ", "%20");
        ImageRequest imageRequest = new ImageRequest(urlimg, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
                myViewHolder.img_product_thumbnail.setImageBitmap(response);
                response.compress(Bitmap.CompressFormat.PNG, 0 , baos);
                frag.setImG(response);
                byte[] blob = baos.toByteArray();
                values.put("imagen", blob);





            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error al conectarse"+error, Toast.LENGTH_LONG).show();


            }
        });
        request.add(imageRequest);
    }

    @Override
    public int getItemCount() {
        return listaProducto.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_product_nombre, tv_product_precio, tv_product_desc;
        ImageView img_product_thumbnail;
        Button btnCart;
        LinearLayout linearLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            btnCart=itemView.findViewById(R.id.btnCart);
            linearLayout =itemView.findViewById(R.id.linearLayout);
            tv_product_desc = itemView.findViewById(R.id.tvDesc);
            tv_product_nombre = itemView.findViewById(R.id.tvNombre);
            tv_product_precio = itemView.findViewById(R.id.tvPrecio);
            img_product_thumbnail = itemView.findViewById(R.id.ivCardview);

        }
    }
}
