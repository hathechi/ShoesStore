package com.example.shoesstore.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.ActivityGioHang;
import com.example.shoesstore.Adapter.SanPhamAdapter;
import com.example.shoesstore.Adapter.SanPhamMainAdapter;
import com.example.shoesstore.Adapter.ThuongHieuAdapter;
import com.example.shoesstore.LoginActivity;
import com.example.shoesstore.Moder.GioHang;
import com.example.shoesstore.Moder.SanPham;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.MySharedPreferences;
import com.example.shoesstore.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    public static List<GioHang> mGioHang = new ArrayList<>();
    private RecyclerView recyclerView, rcvThuongHieu, rcvSanPhamMain;
    private SanPhamAdapter sanPhamAdapter;
    private ThuongHieuAdapter thuongHieuAdapter;
    private SanPhamMainAdapter sanPhamMainAdapter;
    private List<SanPhamMain> sanPhamMains = new ArrayList<>();
    private List<SanPham> sanPhams = new ArrayList<>();
    private List<ThuongHieu> thuonghieu = new ArrayList<>();
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //thêm setHasOptionsMenu(true); để hiện menu custom trên thanh tool bar
        setHasOptionsMenu(true);
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

        //Search view
        searchView = view.findViewById(R.id.SearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sanPhamMainAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamMainAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_giohang:
                MySharedPreferences sharedPreferences = new MySharedPreferences(getContext());
                if (sharedPreferences.getBooleanValue("login")) {
                    Intent intent = new Intent(getActivity(), ActivityGioHang.class);
                    startActivity(intent);
                } else {
                    FancyToast.makeText(getActivity(), "Bạn Cần Phải Đăng Nhập Trước !", FancyToast.LENGTH_SHORT, FancyToast.WARNING, false).show();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
                break;
            case R.id.id_menu_dangxuat:
                MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
                mySharedPreferences.putBooleanValue("login", false);
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //menu toolbar
    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);

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
        sanPhamMainAdapter = new SanPhamMainAdapter(this, sanPhamMains, new SanPhamMainAdapter.IclickAddGioHang() {
            @Override
            public void onClickAdd(SanPhamMain sanPhamMain) {
                mGioHang.add(new GioHang(sanPhamMain.getGia(), sanPhamMain.getName(), sanPhamMain.getThuonghieu(), sanPhamMain.getMota(), sanPhamMain.getURLImage()));
                FancyToast.makeText(getContext(), "Thêm Vào Giỏ Hàng Thành Công !!", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

            }
        });
        rcvSanPhamMain.setAdapter(sanPhamMainAdapter);
    }

    private void AdapterThuongHieu() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (thuonghieu != null) {
                    thuonghieu.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ThuongHieu thuongHieu = dataSnapshot.getValue(ThuongHieu.class);
                    thuonghieu.add(thuongHieu);
                    thuongHieuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rcvThuongHieu.setLayoutManager(linearLayoutManager);
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
                sanPhams.add(new SanPham(sanPham.getId_sanpham1(), sanPham.getURLImage(), sanPham.getGia()));
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