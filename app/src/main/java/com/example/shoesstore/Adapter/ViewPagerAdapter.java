package com.example.shoesstore.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoesstore.Fragment.FragmentGioHang;
import com.example.shoesstore.Fragment.FragmentHoaDon;

import org.jetbrains.annotations.NotNull;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentGioHang();
            case 1:
                return new FragmentHoaDon();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
