package com.example.shoesstore.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Fragment.FragmentGioHang;
import com.example.shoesstore.Moder.GioHang;
import com.example.shoesstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.GioHangViewHoder> {
    private Context context;
    private List<GioHang> mListGioHang;
    private IclickListener mIclickListener;

    public GioHangAdapter(Context context, List<GioHang> mListGioHang, IclickListener iclickListener) {
        this.context = context;
        this.mListGioHang = mListGioHang;
        this.mIclickListener = iclickListener;

    }

    @NonNull
    @NotNull
    @Override
    public GioHangViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_custom_giohang, parent, false);
        return new GioHangViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GioHangViewHoder holder, int position) {
        GioHang sanPhamMain = mListGioHang.get(position);
        if (sanPhamMain == null) {
            return;
        }
        holder.tvName.setText(sanPhamMain.getName());
        holder.tvGia.setText((sanPhamMain.getGiasp()) + " $");
        //sử dụng thư viện Glide để set hình từ url
        Glide.with(context).load(mListGioHang.get(position).getURLImage()).into(holder.iv_sanpham1);
        holder.tvThuongHieu.setText(sanPhamMain.getThuonghieu());
        holder.tvMota.setText(sanPhamMain.getMota());

        holder.tvSoLuong.setText(String.valueOf(sanPhamMain.getSlHienTai()));

        holder.iv_tangsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slMoiNhat = Integer.parseInt(holder.tvSoLuong.getText().toString()) + 1;
                holder.tvSoLuong.setText(String.valueOf(slMoiNhat));
                int slht = sanPhamMain.getSlHienTai();
                mListGioHang.get(position).setSlHienTai(slMoiNhat);
                int giaht = sanPhamMain.getGiasp();
                long giamoinhat = (giaht * slMoiNhat) / slht;
                mListGioHang.get(position).setGiasp((int) giamoinhat);
                FragmentGioHang.getSoLuongGioHang();
                notifyDataSetChanged();
            }
        });

        holder.iv_giamsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sanPhamMain.getSlHienTai() == 1) {
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Xóa Sản Phẩm!")
                            .setMessage("Bạn Có Chắc Muốn Xóa Sản Phẩm Này Khỏi Giỏ Hàng? ")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mListGioHang.remove(position);
                                    FragmentGioHang.getSoLuongGioHang();
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("Không", null)
                            .show();
                } else {
                    int slMoiNhat = Integer.parseInt(holder.tvSoLuong.getText().toString()) - 1;
                    holder.tvSoLuong.setText(String.valueOf(slMoiNhat));
                    int slht = sanPhamMain.getSlHienTai();
                    mListGioHang.get(position).setSlHienTai(slMoiNhat);
                    int giaht = sanPhamMain.getGiasp();
                    long giamoinhat = (giaht * slMoiNhat) / slht;
                    mListGioHang.get(position).setGiasp((int) giamoinhat);
                    FragmentGioHang.getSoLuongGioHang();
                    notifyDataSetChanged();
                }

            }
        });
        holder.btnTVxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Xóa Sản Phẩm!")
                        .setMessage("Bạn Có Chắc Muốn Xóa Sản Phẩm Này Khỏi Giỏ Hàng? ")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mListGioHang.remove(position);
                                FragmentGioHang.getSoLuongGioHang();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });


    }

    @Override
    public int getItemCount() {
        if (mListGioHang != null) {
            return mListGioHang.size();
        }
        return 0;
    }

    // tạo interface để gọi sự kiện update ra bên ngoài
    public interface IclickListener {
        //        void onClickTinhTien(GioHang gioHang, int soluong);
        void onClickTinhTien(int position);

    }

    public class GioHangViewHoder extends RecyclerView.ViewHolder {
        private ImageView iv_sanpham1;
        private TextView tvGia, tvName, tvThuongHieu, tvMota, tvSoLuong;
        private TextView btnTVxoa;
        private ImageView iv_tangsoluong, iv_giamsoluong;

        public GioHangViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_sanpham1 = itemView.findViewById(R.id.iv_sanpham1_home);
            tvGia = itemView.findViewById(R.id.tv_gia_home);
            tvName = itemView.findViewById(R.id.tv_sanpham1_home);
            tvThuongHieu = itemView.findViewById(R.id.tv_thuonghieu1_home);
            tvMota = itemView.findViewById(R.id.tv_mota_home);
            btnTVxoa = itemView.findViewById(R.id.btntvXoa);
            tvSoLuong = itemView.findViewById(R.id.id_soluong);
            iv_giamsoluong = itemView.findViewById(R.id.id_giamsoluong);
            iv_tangsoluong = itemView.findViewById(R.id.id_tangsoluong);
        }
    }
}
