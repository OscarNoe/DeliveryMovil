package com.example.menudigital;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.menudigital.entidades.ConexionSQLiteHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class dialogProduct extends DialogFragment{
    LinearLayout contenidoInfo, contenidoIngre, contenidoComple, ivInfo, ivIngre, ivComple;
    ImageView downIngre, downComple, downInfo, ivImagen;
    TextView tvTitulo, tvPrecio, tvDesc;
    ProgressDialog progress;
    Button btnConfirmar;
    JsonObjectRequest jsonObjectRequest= null;
    RequestQueue request;
    String Id;
    int ide;
    Bitmap imG;
    Product produkt=null;

    public Bitmap getImG() {
        return imG;
    }

    public void setImG(Bitmap imG) {
        this.imG = imG;
    }

    public dialogProduct() {
    }

    public dialogProduct setId(int id){
        ide= id;
        return this;
    }



    public static dialogProduct newInstance(String title) {
        dialogProduct frag = new dialogProduct();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;


    }


    @Override
    public void onResume() {

        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.80), (int) (size.y * 0.85));
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_producto, container);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        request= Volley.newRequestQueue(getContext());
        tvTitulo = view.findViewById(R.id.tvTitulo);
        tvDesc = view.findViewById(R.id.tvDesc);
        tvPrecio = view.findViewById(R.id.tvPrecio);
        ivImagen = view.findViewById(R.id.ivImagen);
        btnConfirmar = view.findViewById(R.id.btnPedido);
        ivComple = view.findViewById(R.id.ivComple);
        ivIngre = view.findViewById(R.id.ivIngre);
        ivInfo = view.findViewById(R.id.ivInfo);
        contenidoComple = view.findViewById(R.id.contenidoComple);
        contenidoIngre = view.findViewById(R.id.contenidoIngre);
        contenidoInfo = view.findViewById(R.id.contenidoInfo);
        downComple = view.findViewById(R.id.downComple);
        downInfo = view.findViewById(R.id.downInfo);
        downIngre = view.findViewById(R.id.downIngre);
        contenidoComple.setVisibility(View.GONE);
        contenidoIngre.setVisibility(View.GONE);
        contenidoInfo.setVisibility(View.GONE);

        CargarWebService();





        // Fetch arguments from bundle and set title
        ivComple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contenidoComple.getVisibility()== View.VISIBLE){
                    contenidoComple.setVisibility(View.GONE);

                    downComple.setImageResource(R.drawable.ic_down);
                }else{
                    downComple.setImageResource(R.drawable.ic_up);
                    contenidoComple.setVisibility(View.VISIBLE);
                }

            }
        });

        ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contenidoInfo.getVisibility()== View.VISIBLE){
                    contenidoInfo.setVisibility(View.GONE);
                    downInfo.setImageResource(R.drawable.ic_down);
                }else{
                    downInfo.setImageResource(R.drawable.ic_up);
                    contenidoInfo.setVisibility(View.VISIBLE);
                }
            }
        });

        ivIngre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contenidoIngre.getVisibility()== View.VISIBLE){
                    contenidoIngre.setVisibility(View.GONE);
                    downIngre.setImageResource(R.drawable.ic_down);
                }else{
                    downIngre.setImageResource(R.drawable.ic_up);
                    contenidoIngre.setVisibility(View.VISIBLE);
                }
            }
        });
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getActivity(), CartActivity.class);
                startActivity(intent);
                getDialog().dismiss();*/
                ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);

                produkt.getImagen().compress(Bitmap.CompressFormat.PNG, 0 , baos);
                byte[] blob = baos.toByteArray();
                // aqui tenemos el byte[] con el imagen comprimido, ahora lo guardemos en SQLite



                ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getActivity(), "bd_pedidos",null,1);
                SQLiteDatabase db= conn.getWritableDatabase();
                ContentValues values = new ContentValues();
                //values.put("idOrden", "null");
                values.put("id", String.valueOf(ide));
                values.put("titulo", produkt.getNombre());
                values.put("descripcion", produkt.getDescripcion());
                values.put("precio", String.valueOf(produkt.getPrecio()));
                values.put("imagen", blob);
                Toast.makeText(getActivity(), "Se agregó el producto a su carrito.", Toast.LENGTH_SHORT).show();
                Long idResultado = db.insert("pedidos", "idOrden", values);

            }
        });



        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        // Show soft keyboard automatically and request focus to field

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);





    }




    private void CargarWebService() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Cargando...");

        String url= "http://"+getResources().getString(R.string.ip)+"/bdSistemaPOS/wsJSONConsultarUsuarioImagenUrl.php?id="+ide;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Product producto = null;
                JSONArray json = response.optJSONArray("producto");
                JSONObject jsonObject = new JSONObject();

                try{
                    producto = new Product();
                    jsonObject = json.getJSONObject(0);
                    producto.setId(jsonObject.optInt("id"));
                    producto.setNombre(jsonObject.optString("nombre"));
                    producto.setDescripcion(jsonObject.optString("descripcion"));
                    producto.setCategory(jsonObject.optString("categoria"));
                    producto.setPrecio(jsonObject.optDouble("precio"));
                    producto.setRuta_imagen(jsonObject.optString("ruta_imagen"));
                    progress.hide();
                    produkt = producto;
                    progress.hide();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error al conectarse"+e, Toast.LENGTH_LONG).show();

                    progress.hide();
                }
                tvTitulo.setText(producto.getNombre());
                tvDesc.setText(producto.getDescripcion());
                tvPrecio.setText("$ "+producto.getPrecio());
                if(imG != null){
                    ivImagen.setImageBitmap(imG);
                    produkt.setImagen(imG);
                }else{
                    ivImagen.setImageResource(R.drawable.producto1);
                }
                //cargarImagen(producto.getRuta_imagen());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Error al conectarse"+error, Toast.LENGTH_LONG).show();

                progress.hide();
            }
        });
        request.add(jsonObjectRequest);
    }
    private void cargarImagen(String ruta_imagen) {
        String urlimg = "http://"+getResources().getString(R.string.ip)+"/bdSistemaPOS/"+ruta_imagen;
        urlimg = urlimg.replace(" ", "%20");
        ImageRequest imageRequest = new ImageRequest(urlimg, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ivImagen.setImageBitmap(response);
                produkt.setImagen(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Error al conectarse"+error, Toast.LENGTH_LONG).show();


            }
        });
        request.add(imageRequest);
    }

   /* @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {
        progress.hide();
        Product producto = null;
        JSONArray json=response.optJSONArray("producto");
        JSONObject jsonObject = null;

        try{
            jsonObject = json.getJSONObject(0);
            producto.setId(jsonObject.optInt("id"));
            producto.setNombre(jsonObject.optString("nombre"));
            producto.setDescripcion(jsonObject.optString("descripcion"));
            producto.setCategory(jsonObject.optString("categoria"));
            producto.setPrecio("$ "+jsonObject.optDouble("precio"));
            producto.setRuta_imagen(jsonObject.optString("ruta_imagen"));
            progress.hide();

            tvTitulo.setText(producto.getNombre());
            tvDesc.setText(producto.getDescripcion());
            tvPrecio.setText("& "+producto.getPrecio());
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error al conectarse"+e, Toast.LENGTH_LONG).show();

            progress.hide();
        }
*/



}
