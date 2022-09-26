package com.example.shoesstore.DAO;

import androidx.annotation.NonNull;

import com.example.shoesstore.Moder.SanPhamMain;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {


    public static List<SanPhamMain> getAll() {


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        List<SanPhamMain> list = new ArrayList<>();

//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//                SanPhamMain sanPhamMain = snapshot.getValue(SanPhamMain.class);
//                list.add(sanPhamMain);
//            }
//
//            @Override
//            public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    SanPhamMain sanPhamMain = dataSnapshot.getValue(SanPhamMain.class);
                    list.add(sanPhamMain);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        return list;
    }

    public void DeletetSanPham(SanPhamMain sanPhamMain) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.child(String.valueOf(sanPhamMain.getId_sanpham1())).removeValue();
    }

    public void insertSanPham(SanPhamMain sanPhamMain) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.child(String.valueOf(sanPhamMain.getId_sanpham1())).setValue(sanPhamMain);
    }

    public void updateSanPham(SanPhamMain sanPhamMain) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("sanpham");
        myRef.child(String.valueOf(sanPhamMain.getId_sanpham1())).updateChildren(sanPhamMain.toMap());
    }
}
