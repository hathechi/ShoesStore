package com.example.shoesstore.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Adapter.GioHangAdapter;
import com.example.shoesstore.DAO.HoaDonDAO;
import com.example.shoesstore.Moder.GioHang;
import com.example.shoesstore.Moder.HoaDon;
import com.example.shoesstore.R;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentGioHang extends Fragment {
    public static List<GioHang> mlistGioHang = FragmentHome.mGioHangtoFirebase;
    private static TextView tvTongSo;
    private static TextView tvThanhTien;
    private static Button btnXuatHoaDon;
    String user_name_login;
    private RecyclerView rcvGioHang;
    private GioHangAdapter gioHangAdapter;

    // hàm cộng tổng tiền rồi đưa lên màn hình
    public static void getSoLuongGioHang() {
        long tongtien = 0;
        for (int i = 0; i < mlistGioHang.size(); i++) {
            tongtien += mlistGioHang.get(i).getGiasp();
        }
        tvTongSo.setText(String.valueOf(mlistGioHang.size()));
        tvThanhTien.setText((tongtien) + " $");

    }


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
        btnXuatHoaDon = view.findViewById(R.id.btnThanhToan_giohang);
        btnXuatHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Lấy ngày hiện tại
                String DateToday = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                String TimeToday = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String mahd = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                Log.i("r", "onClick: " + TimeToday);
                long tongtien = 0;
                String listName = "";
                for (int i = 0; i < mlistGioHang.size(); i++) {
                    tongtien += mlistGioHang.get(i).getGiasp();
                    listName += "- " + mlistGioHang.get(i).getName() + " (" + mlistGioHang.get(i).getSlHienTai()
                            + " - " + mlistGioHang.get(i).getColor() + " - " + mlistGioHang.get(i).getSize() + ") " + "\n";
                    Log.i("a", "onClick: ");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("sanpham");
                    myRef.child((mlistGioHang.get(i).getIdSanpham()) + "/slDaban").
                            setValue((mlistGioHang.get(i).getSlSanphamDaban() + mlistGioHang.get(i).getSlHienTai()));
                }

                HoaDon hoaDon = new HoaDon(TimeToday, user_name_login, DateToday, TimeToday, false, listName, tongtien);
                HoaDonDAO hoaDonDAO = new HoaDonDAO();
                hoaDonDAO.insertHoaDon(hoaDon);
                FancyToast.makeText(getContext(), "In Thành Công ! Xin Kiểm Tra Lại Và Thanh Toán", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
            }


        });
        getSoLuongGioHang();
        setAdapterGioHang();
        return view;
    }
  
    public void setAdapterGioHang() {


        //Lấy user name để lấy được child của firebase
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        user_name_login = mySharedPreferences.getValue("remember_username");


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvGioHang.setLayoutManager(linearLayoutManager);

        gioHangAdapter = new GioHangAdapter(getContext(), mlistGioHang, new GioHangAdapter.IclickListener() {
            @Override
            public void onClickDeleteItemFireBase(int position) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("giohang");
                myRef.child(user_name_login).child(String.valueOf(position)).removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                        FancyToast.makeText(getContext(), "Xóa Khỏi Giỏ Hàng Thành Công !!",
                                FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                    }
                });
            }
        });
        rcvGioHang.setAdapter(gioHangAdapter);
    }
}