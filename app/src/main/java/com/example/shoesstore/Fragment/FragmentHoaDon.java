package com.example.shoesstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Adapter.HoaDonAdapter;
import com.example.shoesstore.Moder.HoaDon;
import com.example.shoesstore.MyApplication;
import com.example.shoesstore.R;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class FragmentHoaDon extends Fragment {
    private RecyclerView recyclerView;
    private HoaDonAdapter hoaDonAdapter;
//    private List<HoaDon> mListhoadon = FragmentHome.mListhoadon;
    private List<HoaDon> mListhoadon = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);
        recyclerView = view.findViewById(R.id.rcvHoaDon);



        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        String user_name = mySharedPreferences.getValue("remember_username");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("hoadon");

        myRef.orderByChild("name_khachhang").equalTo(user_name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (mListhoadon != null) {
                    mListhoadon.clear();
                }
                for (DataSnapshot list : snapshot.getChildren()) {
                    HoaDon hoaDon = list.getValue(HoaDon.class);

                    mListhoadon.add(hoaDon);
                }
                Log.i("b", "setAdapterHoaDon: " +mListhoadon.size());
                //Set adapter
                setAdapterHoaDon();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        return view;
    }

    private void setAdapterHoaDon() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        hoaDonAdapter = new HoaDonAdapter(mListhoadon);
        recyclerView.setAdapter(hoaDonAdapter);

    }
}