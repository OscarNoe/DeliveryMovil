package com.example.menudigital;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
public class PedidosPasadosFragment extends Fragment  implements Response.Listener<JSONObject>, Response.ErrorListener  {
    RecyclerView myrv;
    ProgressDialog progress;
    ArrayList<PedidoPasado> lstPedidos;
    JsonObjectRequest jsonObjectRequest= null;
    RequestQueue request;
    ArrayList<ProductosPedidos> lstProduct;
    ArrayList<ListProductPedidos> lstLst;
    ProductosPedidos productosPedidos;
   // Bundle bundle;
    private List<PedidoPasado> listaPedido;
    public PedidosPasadosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_pedidos_pasados, container, false);
        lstPedidos = new ArrayList<>();
        lstProduct= new ArrayList<>();
        lstLst = new ArrayList<>();
        //RecyclerView
        myrv =rootView.findViewById(R.id.recyclerview_idP);
       // bundle = new Bundle();
        //bundle.putString("tableta", ((PedidoActivity)getContext()).args.getString("tableta1"));
       /* int columnas = 1; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        GridLayoutManager grid = new GridLayoutManager(getActivity(), columnas);
        myrv.setLayoutManager(grid);
        myrv.addItemDecoration(new GridSpacingItemDecoration(columnas, spacing, includeEdge));*/
        int columnas = 1; // 3 columns
        int spacing = 60; // 50px
        boolean includeEdge = true;
        GridLayoutManager grid = new GridLayoutManager(getActivity(), columnas);
        myrv.setLayoutManager(grid);
        myrv.addItemDecoration(new GridSpacingItemDecoration(columnas, spacing, includeEdge));
        request = Volley.newRequestQueue(getActivity());
        llenarRecycler();


        //RecyclerViewAdapterPedidos adapt = new RecyclerViewAdapterPedidos(lstPedidos,getContext());
        //myrv.setAdapter(adapt);
        //Toast.makeText(getActivity(), "Items:"+adapt.getItemCount(), Toast.LENGTH_SHORT).show();
        return rootView;

    }

    private void llenarRecycler() {
        progress=new ProgressDialog(getContext());
        progress.setMessage("Buscando los pedidos");
        progress.show();


        String url= "http://"+getResources().getString(R.string.ip)+"/bdSistemaPOS/wsJSONConsultarListaPedidos.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);


    }

    @Override
    public void onResponse(JSONObject response) {
        PedidoPasado pedidoPasado = null;
        JSONArray json = response.optJSONArray("pedidos");

        try {
            for(int i=0;i<json.length();i++){
                pedidoPasado = new PedidoPasado();
                JSONObject jsonObject = null;
                lstProduct= new ArrayList<>();
                jsonObject = json.getJSONObject(i);
                pedidoPasado.setNum(jsonObject.optInt("id"));
                pedidoPasado.setTotal(jsonObject.optDouble("total"));
                pedidoPasado.setEstado(jsonObject.getString("estado"));
                pedidoPasado.setFecha(jsonObject.getString("fecha"));
                lstPedidos.add(pedidoPasado);
                JSONArray mJsonArrayProductos=jsonObject.getJSONArray("productos");
                for(int z=0;z<mJsonArrayProductos.length() ;z++){
                    JSONObject mJsonObjectProductos=mJsonArrayProductos.getJSONObject(z);

                    lstProduct.add(new ProductosPedidos(mJsonObjectProductos.getInt("repeticion"), mJsonObjectProductos.getString("nombre")));
                }
                mJsonArrayProductos=null;
                lstLst.add(new ListProductPedidos(lstProduct, pedidoPasado));





            }

            RecyclerViewAdapterPedidos adapt = new RecyclerViewAdapterPedidos(lstLst,getActivity());

            myrv.setAdapter(adapt);
            progress.hide();


        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error al conectarse", Toast.LENGTH_LONG).show();

            progress.hide();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}
