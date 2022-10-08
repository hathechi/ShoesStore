package com.example.shoesstore;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import com.example.shoesstore.Fragment.FragmentGioHang;
import com.example.shoesstore.Fragment.FragmentHoaDon;
import com.example.shoesstore.Moder.HoaDon;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jetbrains.annotations.NotNull;

public class ActivityGioHang extends AppCompatActivity {
    //        private ViewPager2 viewPager2;
//        private AHBottomNavigation ahBottomNavigation;
    private BottomNavigationView ahBottomNavigation;
//    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        //set ToolBar
        final Toolbar toolbar = findViewById(R.id.toolbar_giohang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Phải push notifi ở Activity
        pushNotification();


        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerView, new FragmentGioHang()).commit();

        ahBottomNavigation = findViewById(R.id.AHBottomNavigation);
        ahBottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_menu_bottom_giohang:
                        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerView, new FragmentGioHang()).commit();
                        break;
                    case R.id.id_menu_bottom_hoadon:
                        getSupportFragmentManager().beginTransaction().replace(R.id.FragmentContainerView, new FragmentHoaDon()).commit();
                }
                return true;
            }
        });


//        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Giỏ Hàng", R.drawable.ic_baseline_shopping_cart_24, R.color.white);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Hóa Đơn", R.drawable.ic_baseline_event_note_24, R.color.white);
//
//        ahBottomNavigation.addItem(item1);
//        ahBottomNavigation.addItem(item2);
//
//        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public boolean onTabSelected(int position, boolean wasSelected) {
//                viewPager2.setCurrentItem(position);
//                return true;
//            }
//        });
//
//
//        viewPager2 = findViewById(R.id.viewPager2);
//        adapter = new ViewPagerAdapter(ActivityGioHang.this);
//        viewPager2.setAdapter(adapter);
//        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                ahBottomNavigation.setCurrentItem(position);
//            }
//        });
    }

    public void pushNotification() {
        Intent intent = getIntent();
        HoaDon hoaDon = (HoaDon) intent.getSerializableExtra("key");

        if (hoaDon != null) {

            @SuppressLint("ResourceAsColor")
            Notification notification = new NotificationCompat.Builder(getApplicationContext(), MyApplication.CHANNEL_ID)
                    .setContentTitle("Khách Hàng " + hoaDon.getName_khachhang().toUpperCase() + " Thân Mến !")
                    .setContentText("Khách Hàng Đã Thanh Toán " + hoaDon.getTongtien() + " $ Thành Công! ")
                    .setSmallIcon(R.drawable.icon_mail)
                    .setColor(R.color.pig)
                    .build();
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.notify(1, notification);
            }
        }

    }
}