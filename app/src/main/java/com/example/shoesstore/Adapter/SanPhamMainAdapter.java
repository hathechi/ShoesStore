package com.example.shoesstore.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Fragment.FragmentHome;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.R;
import com.example.shoesstore.SharedPreferences.MySharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SanPhamMainAdapter extends RecyclerView.Adapter<SanPhamMainAdapter.SanPhamMainViewHoder> {

    private final FragmentHome context;
    private final List<SanPhamMain> mSanPhamMainSearch; // list trung gian để tìm kiếm
    private final IclickAddGioHang iclickAddGioHang;
    private List<SanPhamMain> mSanPhamMain;

    public SanPhamMainAdapter(FragmentHome context, List<SanPhamMain> mSanPhamMain, IclickAddGioHang ivAddGioHang) {
        this.context = context;
        this.mSanPhamMain = mSanPhamMain;
        this.mSanPhamMainSearch = mSanPhamMain;
        this.iclickAddGioHang = ivAddGioHang;
    }

    @NonNull
    @NotNull
    @Override
    public SanPhamMainViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sanpham_main, parent, false);
        return new SanPhamMainViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SanPhamMainViewHoder holder, int position) {
        SanPhamMain sanPhamMain = mSanPhamMain.get(position);
        if (sanPhamMain == null) {
            return;
        }
        holder.tvName.setText(sanPhamMain.getName());
        holder.tvGia.setText(sanPhamMain.getGia() + " $");
        //sử dụng thư viện Glide để set hình từ url
        Glide.with(context).load(mSanPhamMain.get(position).getURLImage()).into(holder.iv_sanpham1);
        holder.tvThuongHieu.setText(sanPhamMain.getThuonghieu());
        holder.tvMota.setText(sanPhamMain.getMota());

        //Nếu đăng nhập thì hiện nút add giỏ hàng không thì ẩn
        MySharedPreferences mySharedPreferences = new MySharedPreferences(context.getContext());
        if (mySharedPreferences.getBooleanValue("login")) {
            holder.ivAddGioHang.setVisibility(View.VISIBLE);
        } else {
            holder.ivAddGioHang.setVisibility(View.INVISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                iclickAddGioHang.onClickAdd(sanPhamMain);
                //Dùng ContextCompat.getColor(context.getContext(), R.color.pig) để set màu cho imageView
                holder.ivAddGioHang.setColorFilter(ContextCompat.getColor(context.getContext(), R.color.purple_500));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mSanPhamMain != null) {
            return mSanPhamMain.size();
        }
        return 0;
    }

    // getFilter để tìm kiếm
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Search = charSequence.toString();
                if (Search.isEmpty()) {
                    mSanPhamMain = mSanPhamMainSearch;
                } else {
                    List<SanPhamMain> list = new ArrayList<>();
                    for (SanPhamMain sanPham : mSanPhamMainSearch) {
                        if (sanPham.getName().toLowerCase().contains(Search.toLowerCase())
                                || (sanPham.getThuonghieu().toLowerCase()).contains(Search.toLowerCase())
                                || String.valueOf(sanPham.getGia()).toLowerCase().contains(Search.toLowerCase())
                                || sanPham.getMota().toLowerCase().contains(Search.toLowerCase())) {
                            list.add(sanPham);
                        }
                    }
                    mSanPhamMain = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mSanPhamMain;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mSanPhamMain = (List<SanPhamMain>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IclickAddGioHang {
        void onClickAdd(SanPhamMain sanPhamMain);
    }

    public class SanPhamMainViewHoder extends RecyclerView.ViewHolder {
        private final ImageView iv_sanpham1;
        private final TextView tvGia;
        private final TextView tvName;
        private final TextView tvThuongHieu;
        private final TextView tvMota;
        private final ImageView ivAddGioHang;
        private CardView cardView;

        public SanPhamMainViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_sanpham1 = itemView.findViewById(R.id.iv_sanpham1_home);
            tvGia = itemView.findViewById(R.id.tv_gia_home);
            tvName = itemView.findViewById(R.id.tv_sanpham1_home);
            tvThuongHieu = itemView.findViewById(R.id.tv_thuonghieu1_home);
            tvMota = itemView.findViewById(R.id.tv_mota_home);
            ivAddGioHang = itemView.findViewById(R.id.btnAddgiohang);
            cardView = itemView.findViewById(R.id.layout_sanpham_main);
        }
    }
}
