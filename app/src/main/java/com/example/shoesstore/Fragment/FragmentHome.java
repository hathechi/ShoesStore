package com.example.shoesstore.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.google.firebase.database.ValueEventListener;

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
    private List<SanPhamMain> mSanPhamMains = new ArrayList<>(); // list trung gian
    private List<SanPham> sanPhams = new ArrayList<>();
    private List<ThuongHieu> thuonghieu = new ArrayList<>();

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


        return view;
    }

    //menu Search


    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        MenuItem item = menu.findItem(R.id.id_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                sanPhamMainAdapter.getFilter().filter(query);
//                searchItem(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                sanPhamMainAdapter.getFilter().filter(newText);
//                searchItem(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void searchItem(String Search) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                SanPhamMain sanPham = snapshot.getValue(SanPhamMain.class);
                mSanPhamMains = sanPhamMains;
                if (Search.isEmpty()) {
                    sanPhamMains = mSanPhamMains;
                } else {

                        if (sanPham.getName().toLowerCase().contains(Search.toLowerCase())
                                || (sanPham.getThuonghieu()).contains(Search.toLowerCase())
                                || String.valueOf(sanPham.getGia()).toLowerCase().contains(Search.toLowerCase())
                                || sanPham.getMota().toLowerCase().contains(Search.toLowerCase())) {
                           sanPhamMains.add(sanPham);
                        }

//                    sanPhamMains = list;
//                    List<SanPhamMain> list = new ArrayList<>();
//                    for (SanPhamMain product : mSanPhamMains) {
//                        if (product.getName().toLowerCase().contains(Search.toLowerCase())
//                                || (product.getThuonghieu()).contains(Search.toLowerCase())
//                                || String.valueOf(product.getGia()).toLowerCase().contains(Search.toLowerCase())
//                                || product.getMota().toLowerCase().contains(Search.toLowerCase())) {
//                            list.add(product);
//                        }
//                    }
//                    sanPhamMains = list;
                }

//                if (sanPham.getThuonghieu().contains(query) || sanPham.getName().contains(query)) {
//                    if (mSanPhamMains != null) {
//                        sanPhamMains.clear();
//                    }
//                    sanPhamMains.add(sanPham);
//                }
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

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == R.id.id_search) {
            Toast.makeText(getActivity(), "abc", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
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
        sanPhamMainAdapter = new SanPhamMainAdapter(this, sanPhamMains);
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