package com.example.shoesstore.DAO;

import com.example.shoesstore.Moder.ThuongHieu;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ThuongHieuDAO {

    public void insertThuongHieu(ThuongHieu thuongHieu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.child(String.valueOf(thuongHieu.getId_thuonghieu())).setValue(thuongHieu);
    }

    public void updateThuongHieu(ThuongHieu thuongHieu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.child(String.valueOf(thuongHieu.getId_thuonghieu())).updateChildren(thuongHieu.toMap());
    }

    public void deleteThuongHieu(ThuongHieu thuongHieu) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.child(String.valueOf(thuongHieu.getId_thuonghieu())).removeValue();
    }
}
