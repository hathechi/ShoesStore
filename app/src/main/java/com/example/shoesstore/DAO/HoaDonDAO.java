package com.example.shoesstore.DAO;

import com.example.shoesstore.Moder.HoaDon;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HoaDonDAO {

    public void insertHoaDon(HoaDon HoaDon) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hoadon");
        myRef.child(HoaDon.getGiotaoHD()).setValue(HoaDon);
    }

    public void updateHoaDon(HoaDon HoaDon) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hoadon");
        myRef.child(HoaDon.getGiotaoHD()).updateChildren(HoaDon.toMap());
    }

    public void deleteHoaDon(HoaDon HoaDon) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hoadon");
        myRef.child(HoaDon.getGiotaoHD()).removeValue();
    }
}
