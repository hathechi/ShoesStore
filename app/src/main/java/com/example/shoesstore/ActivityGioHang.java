package com.example.shoesstore;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.shoesstore.Adapter.ViewPagerAdapter;

public class ActivityGioHang extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private AHBottomNavigation ahBottomNavigation;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        //set ToolBar
        final Toolbar toolbar = findViewById(R.id.toolbar_giohang);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ahBottomNavigation = findViewById(R.id.AHBottomNavigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Giỏ Hàng", R.drawable.ic_clear_black_24dp, R.color.purple_200);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Hóa Đơn", R.drawable.ic_check_black_24dp, R.color.purple_700);

        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);

        ahBottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                viewPager2.setCurrentItem(position);
                return true;
            }
        });


        viewPager2 = findViewById(R.id.viewPager2);
        adapter = new ViewPagerAdapter(ActivityGioHang.this);
        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);

            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ahBottomNavigation.setCurrentItem(position);
            }
        });
    }
}