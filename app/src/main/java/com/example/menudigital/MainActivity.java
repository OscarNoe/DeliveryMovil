package com.example.menudigital;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.menudigital.entidades.ConexionSQLiteHelper;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText etCodigo, etPin, etTableta;
    Button btnLogin;
    ProgressDialog progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        etTableta = findViewById(R.id.etTableta);
        etCodigo = findViewById(R.id.etCodigo);
        etPin = findViewById(R.id.etPin);
        btnLogin=findViewById(R.id.btnLogin);
        etCodigo.setTransformationMethod(null);
        etTableta.setTransformationMethod(null);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
       // resettt();
    }



    private void Login(){
        progreso=new ProgressDialog(MainActivity.this);
        progreso.setMessage("Carganding...");
        progreso.show();
        String url = "http://"+ getResources().getString(R.string.ip)+"/bdSistemaPOS/login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("1")) {
                    Bundle bundle = new Bundle();
                    Intent intent= new Intent(getApplicationContext(), PrincipalActivity.class);

                    //intent.putExtra("tableta", etTableta.getText().toString());
                    String tabl = etTableta.getText().toString();
                    bundle.putString("tableta", tabl);
                    Toast.makeText(MainActivity.this, bundle.getString("tableta"), Toast.LENGTH_SHORT).show();
                    intent.putExtras(bundle);
                    startActivity(intent);
                    etPin.setText("");
                    etCodigo.setText("");
                    etTableta.setText("");

                    finish();
                    progreso.hide();
                }else if(response.equals("0")){
                    progreso.hide();
                    Toast.makeText(MainActivity.this, "Codigo o Pin de usuario equivocado.", Toast.LENGTH_SHORT).show();
                }else if(response.equals("3")){
                    progreso.hide();
                    Toast.makeText(MainActivity.this, "Numero de tableta ocupada.", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", etCodigo.getText().toString());
                params.put("pin", etPin.getText().toString()  );
                params.put("tableta", etTableta.getText().toString()  );
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }



   /* private void Login(){
        String url = "http://192.168.100.159/bdSistemaPOS/login.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("hecho")){
                    Toast.makeText(MainActivity.this, "Usted ha ingresado exitosamente!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "No coincide!"+response.trim(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, " Error:  "+error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("codigo", etCodigo.getText().toString().trim());
                params.put("pin", etPin.getText().toString().trim());
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }*/
}
