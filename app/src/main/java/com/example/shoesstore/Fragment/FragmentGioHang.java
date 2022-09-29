package com.example.shoesstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Adapter.GioHangAdapter;
import com.example.shoesstore.Moder.GioHang;
import com.example.shoesstore.R;

import java.util.List;

public class FragmentGioHang extends Fragment {
    public static List<GioHang> mlistGioHang = FragmentHome.mGioHang;
    private RecyclerView rcvGioHang;
    private GioHangAdapter gioHangAdapter;
    private static TextView tvTongSo;
    private static TextView tvThanhTien;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //set Title cho toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Giỏ Hàng ");


        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);
        rcvGioHang = view.findViewById(R.id.rcvGioHang);
        tvThanhTien = view.findViewById(R.id.id_tongtien_giohang);
        tvTongSo = view.findViewById(R.id.id_tongso_giohang);
        getSoLuongGioHang();
        setAdapterGioHang();
        return view;
    }

    public void setAdapterGioHang() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvGioHang.setLayoutManager(linearLayoutManager);
        gioHangAdapter = new GioHangAdapter(getContext(), mlistGioHang, new GioHangAdapter.IclickListener() {
            @Override
            public void onClickTinhTien(int position) {

            }
        });
        rcvGioHang.setAdapter(gioHangAdapter);
    }
// hàm cộng tổng tiền rồi đưa lên màn hình
    public static void getSoLuongGioHang() {
        long tongtien = 0;
        for (int i = 0; i < mlistGioHang.size(); i++) {
            tongtien += mlistGioHang.get(i).getGiasp();
        }
        tvTongSo.setText(String.valueOf(mlistGioHang.size()));
        tvThanhTien.setText((tongtien) + " $");

    }
}