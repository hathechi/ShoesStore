package com.example.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Moder.SanPham;
import com.example.shoesstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.SanPhamViewHoder> {
    private final List<SanPham> mSanPham;
    private final Context context;

    public SanPhamAdapter(List<SanPham> mSanPham, Context context) {
        this.mSanPham = mSanPham;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public SanPhamViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_sanpham, parent, false);
        return new SanPhamViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SanPhamViewHoder holder, int position) {
        SanPham sanPham = mSanPham.get(position);
        if (sanPham == null) {
            return;
        }
        holder.tv_sanpham_home.setText(sanPham.getGia_sanpham() + " $");
        //sử dụng thư viện Glide để set hình từ url
        Glide.with(context).load(mSanPham.get(position).getImage_sanpham()).into(holder.iv_sanpham_home);

        holder.cardview_sanpham.startAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(),R.anim.animation_1));
    }

    @Override
    public int getItemCount() {
        if (mSanPham != null) {
            return mSanPham.size();
        }
        return 0;

    }

    public class SanPhamViewHoder extends RecyclerView.ViewHolder {
        private final ImageView iv_sanpham_home;
        private final TextView tv_sanpham_home;
        private  CardView cardview_sanpham;

        public SanPhamViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_sanpham_home = itemView.findViewById(R.id.iv_sanpham_home);
            tv_sanpham_home = itemView.findViewById(R.id.tv_gia_sanpham_home);
            cardview_sanpham = itemView.findViewById(R.id.cardview_sanpham);
        }
    }
}
