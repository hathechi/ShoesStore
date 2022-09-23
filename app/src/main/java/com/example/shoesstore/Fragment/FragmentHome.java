package com.example.shoesstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Adapter.SanPhamAdapter;
import com.example.shoesstore.Adapter.SanPhamMainAdapter;
import com.example.shoesstore.Adapter.ThuongHieuAdapter;
import com.example.shoesstore.Moder.SanPham;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentHome#} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment {
    private RecyclerView recyclerView, rcvThuongHieu, rcvSanPhamMain;
    private SanPhamAdapter sanPhamAdapter;
    private ThuongHieuAdapter thuongHieuAdapter;
    private SanPhamMainAdapter sanPhamMainAdapter;
    private List<SanPhamMain> sanPhamMains = new ArrayList<>();
    private List<SanPham> sanPhams = new ArrayList<>();
    private List<ThuongHieu> thuonghieu = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//set Title cho toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //Sản Phẩm
        recyclerView = view.findViewById(R.id.rcv_home_top);
        AdapterSanPham();
        //Thương Hiệu
        rcvThuongHieu = view.findViewById(R.id.rcv_home_content);
        AdapterThuongHieu();
        //Sản PHẩm Main
        rcvSanPhamMain = view.findViewById(R.id.rcv_home_bottom);

        AdapterSanPhamMain();


        return view;
    }

    private void AdapterSanPhamMain() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                sanPhamMains.add(sanPham);
                sanPhamMainAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rcvSanPhamMain.setLayoutManager(gridLayoutManager);
        sanPhamMainAdapter = new SanPhamMainAdapter(getActivity(), sanPhamMains);
        rcvSanPhamMain.setAdapter(sanPhamMainAdapter);
    }

    private void AdapterThuongHieu() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvThuongHieu.setLayoutManager(linearLayoutManager);
        thuonghieu.add(new ThuongHieu(1, "Nike"));
        thuonghieu.add(new ThuongHieu(1, "Nike1"));
        thuonghieu.add(new ThuongHieu(1, "Nike2"));
        thuonghieu.add(new ThuongHieu(1, "Nike3"));
        thuonghieu.add(new ThuongHieu(1, "Nike4"));
        thuonghieu.add(new ThuongHieu(1, "Nike5"));
        thuongHieuAdapter = new ThuongHieuAdapter(thuonghieu);
        rcvThuongHieu.setAdapter(thuongHieuAdapter);
    }

    private void AdapterSanPham() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                sanPhams.add(new SanPham(sanPham.getId_sanpham1(), sanPham.getURLImage(), sanPham.getThuonghieu()));
                sanPhamAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        sanPhamAdapter = new SanPhamAdapter(sanPhams, getContext());
        recyclerView.setAdapter(sanPhamAdapter);
    }
}