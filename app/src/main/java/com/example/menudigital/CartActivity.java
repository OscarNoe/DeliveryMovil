package com.example.menudigital;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.menudigital.entidades.ConexionSQLiteHelper;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView myrv;
    ProgressDialog progress;
    ArrayList<CartProduct> lstProduct;
    Button btnPedido;
    ProgressDialog progreso;
    TextView total,tvVacio, tvMesa;
    Button vaciar;
    String tableta;
    double Total;
   String tav;
    RecyclerViewAdapterCart adapt;
    boolean PEDIR_POSIBLE=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        tvVacio=findViewById(R.id.tvVacio);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Tu carrito de compras");
        toolbar.setSubtitle("Tu cartilla digital");
        total=findViewById(R.id.tvTotal);
        myrv =findViewById(R.id.recyclerview_id);
        lstProduct = new ArrayList<>();
        btnPedido=findViewById(R.id.btnPedido);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(myrv.getContext(),
                llm.getOrientation());
        myrv.addItemDecoration(dividerItemDecoration);
        myrv.setLayoutManager(llm);
        myrv.setHasFixedSize(true);
        vaciar=findViewById(R.id.ivVaciar);
        vaciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrarDatos();
            }
        });
        llenarLista();
        RecyclerVisible();
        Bundle bundle = getIntent().getExtras();
        String ta="";
        ta=   bundle.getString("tableta");
        //tvMesa.setText("MESA #"+bundle.getString("tableta"));
        tav=ta;
        Toast.makeText(this, "C"+ta, Toast.LENGTH_SHORT).show();
        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myrv.getAdapter() != null){
                    //De esta manera sabes si tu RecyclerView está vacío
                    if(adapt.getItemCount()==0) {
                        //Aquí muestras el mensaje
                        Toast.makeText(CartActivity.this, "Su orden está vacía.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(PEDIR_POSIBLE==true){
                            PedirOrden();
                            Toast.makeText(CartActivity.this, "Su orden ha sido pedida, viene en camino.", Toast.LENGTH_SHORT).show();
                        }

                    }

                }

                //BorrarDatos();
            }
        });
    }

    public void RecyclerVisible() {
        if(myrv.getAdapter() != null){
            //De esta manera sabes si tu RecyclerView está vacío
            if(adapt.getItemCount()==0) {
                //Aquí muestras el mensaje
                tvVacio.setVisibility(View.VISIBLE);
                myrv.setVisibility(View.GONE);
            }

        }
    }

    int i=0;
    public ArrayList<String> idProductos = new ArrayList<>();
    private void PedirOrden() {


        PEDIR_POSIBLE=false;
        progreso=new ProgressDialog(CartActivity.this);
        progreso.setMessage("Cargando...");
        progreso.show();
        i=0;

        String url = "http://"+ getResources().getString(R.string.ip)+"/bdSistemaPOS/nuevoPedido.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                    //Toast.makeText(CartActivity.this, "Funco, la id es: "+response, Toast.LENGTH_SHORT).show();
                    RegistrarProductos(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tableta", tav);
                params.put("total", String.valueOf(Total));

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
        Toast.makeText(this, "Su pedido fue hecho exitosamente.", Toast.LENGTH_SHORT).show();

        progreso.hide();
    }



    private void RegistrarProductos(final String idPedidos) {
        progreso=new ProgressDialog(CartActivity.this);
        progreso.setMessage("Cargando...");
        progreso.show();

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_pedidos",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();




        Cursor cursor = db.rawQuery("SELECT * FROM pedidos",null);
        int y=0;
        while(cursor.moveToNext()) {


            idProductos.add(String.valueOf(cursor.getInt(1)));
            //Toast.makeText(this, "ID:" + idProductos.get(y)+" "+y, Toast.LENGTH_SHORT).show();
            y++;
        }
        db.close();
        String url = "http://"+ getResources().getString(R.string.ip)+"/bdSistemaPOS/insertPedido.php";
        for(int z=0;z<=y;z++) {
            final int finalZ = z;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {


                    //Toast.makeText(CartActivity.this, "KIUBO: " + response, Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("idProducto", idProductos.get(finalZ));
                    params.put("idPedidos", idPedidos);
                    return params;
                }
            };
            i++;
            Volley.newRequestQueue(this).add(stringRequest);

        }
        BorrarDatos();
        progreso.hide();

    }
    private void BorrarDatos() {
        lstProduct.clear();
        adapt.notifyDataSetChanged();
        Total=0;
        total.setText("$"+Total);
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_pedidos",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.delete("pedidos", null, null);
        db.execSQL("update sqlite_sequence set seq=0 where name='pedidos'");
        RecyclerVisible();
        if(lstProduct==null){
            boolean PEDIR_POSIBLE=true;
        }
    }
    public void SumarTotal() {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_pedidos",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor= db.rawQuery("SELECT precio FROM pedidos", null);
        Total = 0;
        while(cursor.moveToNext()){
            int i=0;
            Total=Total+cursor.getDouble(0);
        }
        db.close();
        BigDecimal a = new BigDecimal(Total);
        a = a.divide(new BigDecimal("1"), 2, BigDecimal.ROUND_HALF_UP);
        Total = a.doubleValue();
        total.setText("$"+Total);



    }

    public void llenarLista() {
        //DatosEjemplo();
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_pedidos",null,1);
        SQLiteDatabase db = conn.getReadableDatabase();
        CartProduct cartProduct= null;
        Cursor cursor = db.rawQuery("SELECT * FROM pedidos",null);
        while(cursor.moveToNext()){
            Bitmap bitmap;
            cartProduct = new CartProduct();
            cartProduct.setId(cursor.getInt(0));
            cartProduct.setIdProduct(cursor.getInt(1));
            cartProduct.setNombre(cursor.getString(2));
            cartProduct.setDescripcion(cursor.getString(3));
            cartProduct.setPrecio(cursor.getDouble(4));

            byte[] blob = cursor.getBlob(5);
            ByteArrayInputStream bais = new ByteArrayInputStream(blob);
            bitmap = BitmapFactory.decodeStream(bais);
            cartProduct.setIcono(bitmap);
            lstProduct.add(cartProduct);
        }
        db.close();




        adapt = new RecyclerViewAdapterCart(lstProduct, getApplicationContext());
        myrv.setAdapter(adapt);
        SumarTotal();
    }
   // public void borrarData(int position) {
       // SumarTotal();
      //  lstProduct.clear(); //Borras la data con la que llenas el recyclerview
      //  adapt.notifyItemRemoved(position);//le notificas al adaptador que no hay nada para llenar la vista
   // }
    private void DatosEjemplo() {
        lstProduct.add(new CartProduct(1,1,"Hamburguesa", "Rica hamburguesa recien hecha", 120.50, BitmapFactory.decodeResource(getApplication().getResources(),
                R.drawable.producto1)));
        lstProduct.add(new CartProduct(2,2,"Pizza de queso", "Deliciosa pizza con queso doble", 190.00, BitmapFactory.decodeResource(getApplication().getResources(),
                R.drawable.producto2)));
        lstProduct.add(new CartProduct(3,3,"Hot-Dogs", "Salchicha ahumada y sazón exquisito", 40.50, BitmapFactory.decodeResource(getApplication().getResources(),
                R.drawable.producto3)));
        lstProduct.add(new CartProduct(1,1,"Hamburguesa", "Rica hamburguesa recien hecha", 120.50, BitmapFactory.decodeResource(getApplication().getResources(),
                R.drawable.producto1)));

    }
  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_pedido) {
            //Toast.makeText(this, "Pedidos hechos", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CartActivity.this, PedidoActivity.class);

            startActivity(intent);
        }
        return true;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedido, menu);

        return true;
    }*/

}
