package com.example.menudigital;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BebidasFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
    RecyclerView myrv;
    ProgressDialog progress;
    ArrayList<Product> lstProduct;
    JsonObjectRequest jsonObjectRequest= null;
    RequestQueue request;
    ImageView image;
    Bundle bundle;
    public BebidasFragment() {
        // Required empty public constructor
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            Intent intent = new Intent(getActivity(), CartActivity.class);


            //Toast.makeText(getActivity(), "PRINCIpAL"+bundle.getString("tableta"), Toast.LENGTH_SHORT).show();
            //intent.putExtra("tableta", t);
            intent.putExtras(bundle);
            startActivity(intent);
        }
       /* if (item.getItemId() == R.id.action_pedido) {
            String t= getArguments().getString("tableta");
            Intent intent = new Intent(getActivity(), PedidoActivity.class);

            startActivity(intent);
        }*/
        return true;

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_cart, menu);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_bebidas, container, false);
        setHasOptionsMenu(true);
        bundle = new Bundle();
        bundle.putString("tableta", ((PrincipalActivity)getContext()).args.getString("tableta1"));
        myrv = rootView.findViewById(R.id.recyclerview_id2);
        lstProduct = new ArrayList<>();
        int columnas = 5; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        GridLayoutManager grid = new GridLayoutManager(getActivity(), columnas);
        myrv.setLayoutManager(grid);
        myrv.addItemDecoration(new GridSpacingItemDecoration(columnas, spacing, includeEdge));
        request = Volley.newRequestQueue(getActivity());
        cargarWebService();



        return rootView;

    }

    private void cargarWebService() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Buscando las bebidas...");
        progress.show();

        String urlimg = "http://"+getResources().getString(R.string.ip)+"/bdSistemaPOS/wsJSONConsultarListaBebidasUrl.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlimg, null, this, this);
        request.add(jsonObjectRequest);

    }


    @Override
    public void onResponse(JSONObject response) {
        progress.hide();
        Product producto = null;
        JSONArray json = response.optJSONArray("producto");
        try {
            for(int i=0;i<json.length();i++){
                producto = new Product();
                JSONObject jsonObject = null;

                jsonObject = json.getJSONObject(i);
                producto.setId(jsonObject.optInt("id"));
                producto.setNombre(jsonObject.optString("nombre"));
                producto.setDescripcion(jsonObject.getString("descripcion"));
                producto.setCategory(jsonObject.getString("categoria"));
                producto.setPrecio(jsonObject.optDouble("precio"));
                producto.setRuta_imagen(jsonObject.optString("ruta_imagen"));
                lstProduct.add(producto);
            }

            progress.hide();
            RecyclerViewAdapter adapt = new RecyclerViewAdapter(lstProduct,getActivity());
            myrv.setAdapter(adapt);

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error al conectarse", Toast.LENGTH_LONG).show();

            progress.hide();
        }

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getActivity(), "Error al conectarse"+error, Toast.LENGTH_LONG).show();

        progress.hide();
    }





   /* private void llenarList() {
        lstProduct.add(new Product("Hamburguesa", "Platillo", "$ 156.00", "Rica hamburguesa con carne", R.drawable.producto1));
        lstProduct.add(new Product("Pizza", "Platillo", "$ 250.00", "Rica pizza con peperoni", R.drawable.producto2));
        lstProduct.add(new Product("Spaghetti", "Platillo", "$ 130.00", "Rica pasta a la bolonesia", R.drawable.producto3));
        lstProduct.add(new Product("Sopa de mooshroom", "Platillo", "$ 280.00", "Rica sopa de champinones", R.drawable.producto4));
        lstProduct.add(new Product("Hamburguesa", "Platillo", "$ 156.00", "Rica hamburguesa con carne", R.drawable.producto1));
        lstProduct.add(new Product("Pizza", "Platillo", "$ 250.00", "Rica pizza con peperoni", R.drawable.producto2));
        lstProduct.add(new Product("Spaghetti", "Platillo", "$ 130.00", "Rica pasta a la bolonesia", R.drawable.producto3));
        lstProduct.add(new Product("Sopa de mooshroom", "Platillo", "$ 280.00", "Rica sopa de champinones", R.drawable.producto4));
        lstProduct.add(new Product("Hamburguesa", "Platillo", "$ 156.00", "Rica hamburguesa con carne", R.drawable.producto1));
        lstProduct.add(new Product("Pizza", "Platillo", "$ 250.00", "Rica pizza con peperoni", R.drawable.producto2));
        lstProduct.add(new Product("Spaghetti", "Platillo", "$ 130.00", "Rica pasta a la bolonesia", R.drawable.producto3));
        lstProduct.add(new Product("Sopa de mooshroom", "Platillo", "$ 280.00", "Rica sopa de champinones", R.drawable.producto4));
        lstProduct.add(new Product("Hamburguesa", "Platillo", "$ 156.00", "Rica hamburguesa con carne", R.drawable.producto1));
        lstProduct.add(new Product("Pizza", "Platillo", "$ 250.00", "Rica pizza con peperoni", R.drawable.producto2));
        lstProduct.add(new Product("Spaghetti", "Platillo", "$ 130.00", "Rica pasta a la bolonesia", R.drawable.producto3));
        lstProduct.add(new Product("Sopa de mooshroom", "Platillo", "$ 280.00", "Rica sopa de champinones", R.drawable.producto4));
        lstProduct.add(new Product("Hamburguesa", "Platillo", "$ 156.00", "Rica hamburguesa con carne", R.drawable.producto1));
        lstProduct.add(new Product("Pizza", "Platillo", "$ 250.00", "Rica pizza con peperoni", R.drawable.producto2));
        lstProduct.add(new Product("Spaghetti", "Platillo", "$ 130.00", "Rica pasta a la bolonesia", R.drawable.producto3));
        lstProduct.add(new Product("Sopa de mooshroom", "Platillo", "$ 280.00", "Rica sopa de champinones", R.drawable.producto4));

    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }







    /*

            lstProduct.add(new Product("Hamburguesa", "Platillo", 156.00, "Rica hamburguesa con carne", R.drawable.producto1));
            lstProduct.add(new Product("Pizza", "Platillo", 250.00, "Rica pizza con peperoni", R.drawable.producto2));
            lstProduct.add(new Product("Spaghetti", "Platillo", 130.00, "Rica pasta a la bolonesia", R.drawable.producto3));
            lstProduct.add(new Product("Sopa de mooshroom", "Platillo", 280.00, "Rica sopa de champinones", R.drawable.producto4));
            lstProduct.add(new Product("Hamburguesa", "Platillo", 156.00, "Rica hamburguesa con carne", R.drawable.producto1));
            lstProduct.add(new Product("Pizza", "Platillo", 250.00, "Rica pizza con peperoni", R.drawable.producto2));
            lstProduct.add(new Product("Spaghetti", "Platillo", 130.00, "Rica pasta a la bolonesia", R.drawable.producto3));
            lstProduct.add(new Product("Sopa de mooshroom", "Platillo", 280.00, "Rica sopa de champinones", R.drawable.producto4));
            lstProduct.add(new Product("Hamburguesa", "Platillo", 156.00, "Rica hamburguesa con carne", R.drawable.producto1));
            lstProduct.add(new Product("Pizza", "Platillo", 250.00, "Rica pizza con peperoni", R.drawable.producto2));
            lstProduct.add(new Product("Spaghetti", "Platillo", 130.00, "Rica pasta a la bolonesia", R.drawable.producto3));
            lstProduct.add(new Product("Sopa de mooshroom", "Platillo", 280.00, "Rica sopa de champinones", R.drawable.producto4));

              final GridLayoutManager grid = new GridLayoutManager(getActivity(), 4);
        myrv.setLayoutManager(grid);
            View rootView= inflater.inflate(R.layout.fragment_platillos, container, false);
            myrv = (RecyclerView) rootView.findViewById(R.id.recyclerview_id);

            myrv.setLayoutManager(grid);

            myrv.setHasFixedSize(true);
            RecyclerViewAdapter myAdapter = new RecyclerViewAdapter(getActivity(), lstProduct);

            myrv.setAdapter(myAdapter);
    */

}
