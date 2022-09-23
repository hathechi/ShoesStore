package com.example.shoesstore.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Adapter.ThuongHieuSwipeAdapter;
import com.example.shoesstore.DAO.ThuongHieuDAO;
import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FragmentThuongHieu extends Fragment {
    private RecyclerView rcvEditThuongHieu;
    private ThuongHieuSwipeAdapter thuongHieuSwipeAdapter;
    private List<ThuongHieu> mThuongHieuList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thuong_hieu, container, false);
        //thêm setHasOptionsMenu(true); để hiện menu custom trên thanh tool bar
        setHasOptionsMenu(true);

        rcvEditThuongHieu = view.findViewById(R.id.rcv_thuonghieu);
        setAdapterEditThuongHieu();


        return view;
    }

    private void setAdapterEditThuongHieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("thuonghieu");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (mThuongHieuList != null) {
                    mThuongHieuList.clear();
                }

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ThuongHieu thuongHieu = dataSnapshot.getValue(ThuongHieu.class);
                    mThuongHieuList.add(thuongHieu);
                    thuongHieuSwipeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvEditThuongHieu.setLayoutManager(linearLayoutManager);
        thuongHieuSwipeAdapter = new ThuongHieuSwipeAdapter(mThuongHieuList, new ThuongHieuSwipeAdapter.IclickListenerThuonghieu() {
            @Override
            public void onClickupdateItem(ThuongHieu thuongHieu) {
                updateThuongHieu(thuongHieu);
            }

            @Override
            public void onClickdeleteItem(ThuongHieu thuongHieu) {
                deleteThuongHieu(thuongHieu);
            }
        });
        rcvEditThuongHieu.setAdapter(thuongHieuSwipeAdapter);

    }

    private void updateThuongHieu(ThuongHieu thuonghieu) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        //Nhúng layout vào dialog alert
        View view = layoutInflater.inflate(R.layout.layout_dialog_addthuonghieu, null);
        builder.setView(view);
        //tạo "al" để dissmis() khi xong tác vụ
        final AlertDialog al = builder.show();
        //Ánh xạ

        EditText etThuongHieu = view.findViewById(R.id.etThuongHieu);
        Button btnAddThuongHieu_dialog = view.findViewById(R.id.btnAddThuongHieu_dialog);
        btnAddThuongHieu_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenThuongHieu = etThuongHieu.getText().toString().trim().toUpperCase();
                if (tenThuongHieu.isEmpty()) {
                    FancyToast.makeText(getContext(), "Chưa Nhập Tên Thương Hiệu ! ",
                            FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                } else {
                    ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO();
                    ThuongHieu thuongHieu = new ThuongHieu(thuonghieu.getId_thuonghieu(), tenThuongHieu);

                    thuongHieuDAO.updateThuongHieu(thuongHieu);
                    AlertThongBao();
                    al.dismiss();
                }
            }
        });

    }

    private void deleteThuongHieu(ThuongHieu mThuonghieu) {
        ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO();
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa !!")
                .setMessage("Bạn Có Muốn Xóa Không?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        thuongHieuDAO.deleteThuongHieu(mThuonghieu);
                        AlertThongBao();
                    }
                }).setNegativeButton("Không", null)
                .show();


    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_thuonghieu, menu);
        super.onCreateOptionsMenu(menu, inflater);
        //set Title cho toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Quản Lý Thương Hiệu");


    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuAddThuongHieu:
                addThuongHieu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addThuongHieu() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        //Nhúng layout vào dialog alert
        View view = layoutInflater.inflate(R.layout.layout_dialog_addthuonghieu, null);
        builder.setView(view);
        //tạo "al" để dissmis() khi xong tác vụ
        final AlertDialog al = builder.show();

        //Ánh xạ
        //sau mỗi lần thêm cho id tự tăng thêm 1
        final int[] id = {0};
        EditText etThuongHieu = view.findViewById(R.id.etThuongHieu);
        Button btnAddThuongHieu_dialog = view.findViewById(R.id.btnAddThuongHieu_dialog);
        btnAddThuongHieu_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenThuongHieu = etThuongHieu.getText().toString().trim().toUpperCase();
                if (tenThuongHieu.isEmpty()) {
                    FancyToast.makeText(getContext(), "Chưa Nhập Tên Thương Hiệu ! ",
                            FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                } else {
                    ThuongHieuDAO thuongHieuDAO = new ThuongHieuDAO();
                    ThuongHieu thuongHieu = new ThuongHieu(id[0], tenThuongHieu);
                    id[0] += 1;
                    thuongHieuDAO.insertThuongHieu(thuongHieu);

                    AlertThongBao();
                    al.dismiss();
                }
            }
        });
    }

    private void AlertThongBao() {
        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Chờ Chút !");
        progressDialog.setMessage(" Xong Ngay Đây !");
        progressDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 500);
    }
}