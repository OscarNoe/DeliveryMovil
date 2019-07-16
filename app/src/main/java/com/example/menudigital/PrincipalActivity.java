package com.example.menudigital;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;


public class PrincipalActivity extends AppCompatActivity{
    Toolbar toolbar;
    TabItem tabPlatillos;
    TabItem tabBebidas;
    TabItem tabPostres;
    ViewPager viewPager;
    TabLayout tabLayout;
    PageAdapter pageAdapter;
    String tableta;
    Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_principal);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // toolbar.setTitle(getResources().getString(R.string.app_name));
        //toolbar.setSubtitle("Tu cartilla digital");
        Bundle bundle = getIntent().getExtras();
        String tablet=bundle.getString("tableta");

        args = new Bundle();
        args.putString("tableta1", tablet);
        toolbar.setLogo(R.drawable.logo);

        //tableta = getIntent().getStringExtra("tableta");

        Toast.makeText(this, "PRINCIpAL"+args.getString("tableta"), Toast.LENGTH_SHORT).show();
        tabLayout = findViewById(R.id.tablayout);
        tabBebidas= findViewById(R.id.tabBebidas);
        tabPlatillos = findViewById(R.id.tabPlatillos);
        tabPostres = findViewById(R.id.tabSnacks);
        viewPager = findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                /*if (tab.getPosition() == 1) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            R.color.colorAccent));
                } else if (tab.getPosition() == 2) {
                    toolbar.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            android.R.color.darker_gray));
                } else {
                    toolbar.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            R.color.colorPrimary));
                }

                if (tab.getPosition() == 1) {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            R.color.colorAccent));
                } else if (tab.getPosition() == 2) {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            android.R.color.darker_gray));
                } else {
                    tabLayout.setBackgroundColor(ContextCompat.getColor(PrincipalActivity.this,
                            R.color.colorPrimary));
                }
*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





    }

    @Override
    public void onBackPressed (){
       Intent in = new Intent(PrincipalActivity.this, MainActivity.class);

       startActivity(in);
       finish();
    }
}

