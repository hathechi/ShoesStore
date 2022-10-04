package com.example.shoesstore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.shoesstore.Fragment.FragmentHome;
import com.example.shoesstore.Fragment.FragmentSanPham;
import com.example.shoesstore.Fragment.FragmentThuongHieu;
import com.example.shoesstore.Fragment.MapsFragment;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private com.google.android.material.navigation.NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private TextView tv_header_nav;
    private MySharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //set Fragment home cho app
        replaceFragment(new FragmentHome());

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

        //Ánh xạ nav
        navigationView = findViewById(R.id.nav_view);
        View mView = navigationView.getHeaderView(0);
        tv_header_nav = mView.findViewById(R.id.tv_header_nav);

        sharedPreferences = new MySharedPreferences(MainActivity.this);

        //Ẩn hiện quyền quản lý khi là admin
        Menu nav_Menu = navigationView.getMenu();
        if (sharedPreferences.getBooleanValue("permission_admin")) {

            nav_Menu.findItem(R.id.nav_QLsanpham).setVisible(true);
            nav_Menu.findItem(R.id.nav_QLthuonghieu).setVisible(true);

        } else {
            nav_Menu.findItem(R.id.nav_QLsanpham).setVisible(false);
            nav_Menu.findItem(R.id.nav_QLthuonghieu).setVisible(false);
        }
        //Ẩn hiện menu login, logout
        if (!sharedPreferences.getBooleanValue("login")) {
            nav_Menu.findItem(R.id.nav_login).setVisible(true);
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        } else {
            nav_Menu.findItem(R.id.nav_login).setVisible(false);
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }


        //lấy user name đổ lên nav
        String user = sharedPreferences.getValue("remember_username");
        if (user != null && sharedPreferences.getBooleanValue("login") == true) {
            tv_header_nav.setText("Hi: " + user.toUpperCase());
        } else {
            tv_header_nav.setText(null);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        replaceFragment(new FragmentHome());
                        break;
                    case R.id.nav_QLsanpham:
                        replaceFragment(new FragmentSanPham());
                        break;
                    case R.id.nav_QLthuonghieu:
                        replaceFragment(new FragmentThuongHieu());
                        break;
                    case R.id.nav_login:
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        MySharedPreferences mySharedPreferences = new MySharedPreferences(getApplicationContext());
                        mySharedPreferences.putBooleanValue("login", false);
                        mySharedPreferences.putBooleanValue("permission_admin", false);
                        Intent intent1 = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_maps:
                        replaceFragment(new MapsFragment());
                        break;

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


        //Floating Button
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_action);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreferences.getBooleanValue("login")) {
                    Intent intent = new Intent(MainActivity.this, ActivityGioHang.class);
                    startActivity(intent);
                } else {
                    FancyToast.makeText(MainActivity.this, "Bạn Cần Phải Đăng Nhập Trước !", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

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