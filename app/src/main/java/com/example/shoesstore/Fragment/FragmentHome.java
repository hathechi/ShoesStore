package com.example.shoesstore.Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.ActivityGioHang;
import com.example.shoesstore.Adapter.SanPhamAdapter;
import com.example.shoesstore.Adapter.SanPhamMainAdapter;
import com.example.shoesstore.Adapter.ThuongHieuAdapter;
import com.example.shoesstore.LoginActivity;
import com.example.shoesstore.Moder.GioHang;
import com.example.shoesstore.Moder.HoaDon;
import com.example.shoesstore.Moder.SanPham;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;
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
import java.util.Timer;
import java.util.TimerTask;

public class FragmentHome extends Fragment {
    public static List<GioHang> mGioHang = new ArrayList<>();
    public static List<HoaDon> mListhoadon = new ArrayList<>();
    public static List<GioHang> mGioHangtoFirebase = new ArrayList<>();
    private final List<SanPhamMain> sanPhamMains = new ArrayList<>();
    private final List<SanPham> sanPhams = new ArrayList<>();
    private final List<ThuongHieu> thuonghieu = new ArrayList<>();
    private RecyclerView recyclerView, rcvThuongHieu, rcvSanPhamMain;
    private SanPhamAdapter sanPhamAdapter;
    private ThuongHieuAdapter thuongHieuAdapter;
    private SanPhamMainAdapter sanPhamMainAdapter;
    private SearchView searchView;
    private String Color = null;
    private String Size = null;

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
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
        String user = mySharedPreferences.getValue("remember_username");


        getDataGioHangFirebase(user);   //Lấy dữ liệu giỏ hàng của từng user từ firebase

        if (mySharedPreferences.getBooleanValue("login") && user != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(" Hi  " + user.toUpperCase());
            ((AppCompatActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_baseline_favorite_24);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("");
        }


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
                mySharedPreferences.putBooleanValue("permission_admin", false);
                //Xóa List giỏ hàng cũ khi người dùng đăng xuất.
                if (mGioHang != null) {
                    mGioHang.clear();
                }
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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater1 = getActivity().getLayoutInflater();

                //Nhúng layout vào dialog alert
                View view1 = layoutInflater1.inflate(R.layout.layout_chitiet, null);
                builder1.setView(view1);
//                builder1.show();
                final AlertDialog show = builder1.show();

                //Ánh Xạ
                ImageView ivImage = view1.findViewById(R.id.iv_sanpham1_home);
                Glide.with(getContext()).load(sanPhamMain.getURLImage()).into(ivImage);
                TextView tvTitle = view1.findViewById(R.id.tv_sanpham1_home);
                TextView tvThuonghieu = view1.findViewById(R.id.tv_thuonghieu1_home);
                TextView tvGia = view1.findViewById(R.id.tv_gia_home);
                TextView tvMota = view1.findViewById(R.id.tv_mota_home);
                TextView tvChiTiet = view1.findViewById(R.id.tv_chitiet_home);
                TextView tvSlconlai = view1.findViewById(R.id.tv_soluongcon_home);
                Button btnAdd = view1.findViewById(R.id.btnAddgiohang);
                ImageView btnClose = view1.findViewById(R.id.btnClose);

                setBackgroundColorShoes(view1);

                setBackgroundSizeShoes(view1);


                //Set data lên
                tvTitle.setText(sanPhamMain.getName());
                tvGia.setText(sanPhamMain.getGia() + " $");
                tvMota.setText(sanPhamMain.getMota());
                tvThuonghieu.setText(sanPhamMain.getThuonghieu());
                tvChiTiet.setText(sanPhamMain.getChitiet());
                tvSlconlai.setText(sanPhamMain.getSlNhapvao() + " Đôi");
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show.dismiss();
                    }
                });

                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Color == null || Size == null) {
                            FancyToast.makeText(getContext(), "Chưa Chọn Màu Hoặc Size !!",
                                    FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
                            return;
                        }
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("giohang");
                        mGioHangtoFirebase.add(new GioHang(sanPhamMain.getId_sanpham1(), sanPhamMain.getSlDaban(), sanPhamMain.getGia(), sanPhamMain.getName(), sanPhamMain.getThuonghieu(), sanPhamMain.getMota(), sanPhamMain.getURLImage(), Color, Size));

                        MySharedPreferences mySharedPreferences = new MySharedPreferences(getContext());
                        String user = mySharedPreferences.getValue("remember_username");
                        myRef.child(user).setValue(mGioHangtoFirebase, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable @org.jetbrains.annotations.Nullable DatabaseError error, @NonNull @NotNull DatabaseReference ref) {
                                FancyToast.makeText(getContext(), "Thêm Vào Giỏ Hàng Thành Công !!",
                                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();
                                Color = null;
                                Size = null;
                            }
                        });

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                show.dismiss();
                            }
                        }, 1000);


                    }
                });


            }

            private void setBackgroundSizeShoes(View view1) {
                CardView size40 = view1.findViewById(R.id.size_40);
                CardView size41 = view1.findViewById(R.id.size_41);
                CardView size42 = view1.findViewById(R.id.size_42);
                CardView size43 = view1.findViewById(R.id.size_43);
                size40.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size40.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        size41.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size42.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size43.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        Size = "40";
                    }
                });
                size41.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size41.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        size40.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size42.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size43.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        Size = "41";
                    }
                });
                size42.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size42.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        size41.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size40.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size43.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        Size = "42";

                    }
                });
                size43.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        size43.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.purple_200));
                        size41.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size42.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        size40.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
                        Size = "43";

                    }
                });
            }

            private void setBackgroundColorShoes(View view1) {
                CardView colorBlack = view1.findViewById(R.id.color_black);
                CardView colorWhite = view1.findViewById(R.id.color_white);
                CardView colorOrange = view1.findViewById(R.id.color_orange);
                CardView colorBlue = view1.findViewById(R.id.color_blue);
                colorWhite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorWhite.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.boder_imageview));
                        colorBlack.setBackground(null);
                        colorBlue.setBackground(null);
                        colorOrange.setBackground(null);
                        Color = "Trắng";
                    }
                });
                colorBlack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorBlack.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.boder_imageview));
                        colorWhite.setBackground(null);
                        colorBlue.setBackground(null);
                        colorOrange.setBackground(null);
                        Color = "Đen";

                    }
                });
                colorBlue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorBlue.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.boder_imageview));
                        colorBlack.setBackground(null);
                        colorWhite.setBackground(null);
                        colorOrange.setBackground(null);
                        Color = "Xanh";
                    }
                });
                colorOrange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        colorOrange.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.boder_imageview));
                        colorBlack.setBackground(null);
                        colorBlue.setBackground(null);
                        colorWhite.setBackground(null);
                        Color = "Cam";
                    }
                });
            }


        });
        rcvSanPhamMain.setAdapter(sanPhamMainAdapter);
    }

    private void getDataGioHangFirebase(String user_name) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("giohang");
        if (user_name != null) {
            myRef.child(user_name).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    if (mGioHangtoFirebase != null) {
                        mGioHangtoFirebase.clear();
                    }
                    for (DataSnapshot list : snapshot.getChildren()) {
                        GioHang gioHang = list.getValue(GioHang.class);
                        mGioHangtoFirebase.add(gioHang);
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });
        }

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

        //Auto Scroll Recycerview
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (sanPhamAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), linearLayoutManager.findFirstCompletelyVisibleItemPosition() + 1);

                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (sanPhamAdapter.getItemCount() - 1)) {
                    linearLayoutManager.smoothScrollToPosition(recyclerView, new RecyclerView.State(), 0);

                }
            }
        }, 0, 2000);
    }
}