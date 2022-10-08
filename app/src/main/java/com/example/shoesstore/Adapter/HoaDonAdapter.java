package com.example.shoesstore.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.ActivityGioHang;
import com.example.shoesstore.DAO.HoaDonDAO;
import com.example.shoesstore.Fragment.FragmentHoaDon;
import com.example.shoesstore.Moder.HoaDon;
import com.example.shoesstore.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHoder> {
    private HoaDonDAO hoaDonDAO = new HoaDonDAO();
    private List<HoaDon> mHoadon;


    public HoaDonAdapter( List<HoaDon> mHoadon) {
        this.mHoadon = mHoadon;
    }

    @NonNull
    @NotNull
    @Override
    public HoaDonViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hoadon, parent, false);
        return new HoaDonViewHoder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull HoaDonViewHoder holder, int position) {
        HoaDon hoaDon = mHoadon.get(position);
        holder.tvSohoadon.setText("Số Hóa Đơn: " + hoaDon.getMaHD() + "");
        holder.tvDanhsachmathang.setText(hoaDon.getDanhsachmathang());
        holder.tvNguoimua.setText("Người Mua: " + hoaDon.getName_khachhang().toUpperCase());
        holder.tvNgaytao.setText("Ngày Tạo: " + hoaDon.getNgaytaoHD());
        holder.tvGiotao.setText("Giờ Tạo: " + hoaDon.getGiotaoHD());
        if (hoaDon.getTinhtranghoadon() == false) {
            holder.tvTinhtrang.setText("Tình Trạng: Chưa Thanh Toán");
        } else {
            holder.tvTinhtrang.setText("Tình Trạng: Đã Thanh Toán");
            holder.tvTinhtrang.setTextColor(R.color.purple_500);
            holder.btnThanhToan.setVisibility(View.INVISIBLE);
            holder.view_hoadon.setBackgroundColor(R.color.white);
        }
        holder.tvTongtien.setText("Tổng Tiền: " + hoaDon.getTongtien() + "$");


        holder.btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                //CHờ 3s rồi mới chuyển activity
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Lấy dữ liệu rồi chuyển hướng sang activity để pushNotidication
                        Intent intent = new Intent(view.getContext(), ActivityGioHang.class);
                        intent.putExtra("key", (Serializable) hoaDon);
                        view.getContext().startActivity(intent);
                        ((Activity) view.getContext()).finish();
                    }
                }, 3000);


                FancyToast.makeText(view.getContext(), "Thanh Toán Thành Công!",
                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show();

                hoaDon.setTinhtranghoadon(true);

                hoaDonDAO.updateHoaDon(hoaDon);

                notifyDataSetChanged();
            }
        });

        holder.view_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(view.getContext()).
                        setTitle("Xóa Hóa Đơn").
                        setMessage("Bạn Có Chắc Muốn Xóa Hóa Đơn Này?").
                        setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                hoaDonDAO.deleteHoaDon(hoaDon);
                                mHoadon.remove(position);
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
        if (mHoadon != null) {
            return mHoadon.size();
        }
        return 0;
    }

    public class HoaDonViewHoder extends RecyclerView.ViewHolder {
        private TextView tvSohoadon;
        private TextView tvTongtien;
        private TextView tvTinhtrang;
        private TextView tvNguoimua;
        private TextView tvDanhsachmathang;
        private TextView tvNgaytao;
        private TextView tvGiotao;
        private Button btnThanhToan;
        private CardView view_hoadon;

        public HoaDonViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvDanhsachmathang = itemView.findViewById(R.id.tvDanhsachmathang);
            tvTongtien = itemView.findViewById(R.id.tvTongtien);
            tvTinhtrang = itemView.findViewById(R.id.tvTinhtrang);
            tvNguoimua = itemView.findViewById(R.id.tvNguoimua);
            tvSohoadon = itemView.findViewById(R.id.tvSohoadon);
            tvNgaytao = itemView.findViewById(R.id.tvNgaymua);
            tvGiotao = itemView.findViewById(R.id.tvGiomua);
            btnThanhToan = itemView.findViewById(R.id.btnThanhToan_hoadon);
            view_hoadon = itemView.findViewById(R.id.view_hoadon);
        }
    }
}
