package com.example.shoesstore.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoesstore.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentGioHang#} factory method to
 * create an instance of this fragment.
 */
public class FragmentGioHang extends Fragment {

    public FragmentGioHang() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}