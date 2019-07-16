package com.example.menudigital;

import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class PedidoActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabItem tabPlatillos;
    TabItem tabBebidas;
    TabItem tabPostres;
    ViewPager viewPager;
    TabLayout tabLayout;
    PageAdapter2 pageAdapter;
   // Bundle args;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        tabLayout = findViewById(R.id.tablayout);
        tabBebidas= findViewById(R.id.tabBebidas);
        tabPlatillos = findViewById(R.id.tabPlatillos);
        tabPostres = findViewById(R.id.tabSnacks);
        viewPager = findViewById(R.id.viewPager);
        pageAdapter = new PageAdapter2(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //Bundle bundle = getIntent().getExtras();
       // String tablet=bundle.getString("tableta");

       // args = new Bundle();
       // args.putString("tableta1", tablet);


        //viewPager.setOffscreenPageLimit(3);
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
}
