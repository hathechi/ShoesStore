package com.example.shoesstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoesstore.DAO.ThuongHieuDAO;
import com.example.shoesstore.Fragment.FragmentHome;
import com.example.shoesstore.Fragment.FragmentSanPham;
import com.example.shoesstore.Fragment.FragmentThuongHieu;
import com.example.shoesstore.Moder.ThuongHieu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private com.google.android.material.navigation.NavigationView navigationView;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set Fragment home cho app
        replaceFragment(new FragmentThuongHieu());

        //set ToolBar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /////// navigationView
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.nav_home:
                        replaceFragment(new FragmentHome());
                        break;
                    case R.id.nav_QLsanpham:
                        replaceFragment(new FragmentSanPham());
                        break;
                    case R.id.nav_QLthuonghieu:
                        replaceFragment(new FragmentThuongHieu());
                        break;

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        //set Adapter recycerview



        //Floating Button
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityGioHang.class);
                startActivity(intent);
            }
        });
    }


    // chuyển fragment
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();
    }

}