package com.example.shoesstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Moder.SanPhamMain;
import com.example.shoesstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SanPhamMainAdapter extends RecyclerView.Adapter<SanPhamMainAdapter.SanPhamMainViewHoder> {
    private Context context;
    private List<SanPhamMain> mSanPhamMain;

    public SanPhamMainAdapter(Context context, List<SanPhamMain> mSanPhamMain) {
        this.context = context;
        this.mSanPhamMain = mSanPhamMain;
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
        holder.tvGia.setText(String.valueOf(sanPhamMain.getGia()));
        //sử dụng thư viện Glide để set hình từ url
        Glide.with(context).load(mSanPhamMain.get(position).getURLImage()).into(holder.iv_sanpham1);
        holder.tvThuongHieu.setText(sanPhamMain.getThuonghieu());
        holder.tvMota.setText(sanPhamMain.getMota());
    }

    @Override
    public int getItemCount() {
        if (mSanPhamMain != null) {
            return mSanPhamMain.size();
        }
        return 0;
    }

    public class SanPhamMainViewHoder extends RecyclerView.ViewHolder {
        private ImageView iv_sanpham1;
        private TextView tvGia, tvName, tvThuongHieu, tvMota;

        public SanPhamMainViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            iv_sanpham1 = itemView.findViewById(R.id.iv_sanpham1_home);
            tvGia = itemView.findViewById(R.id.tv_gia_home);
            tvName = itemView.findViewById(R.id.tv_sanpham1_home);
            tvThuongHieu = itemView.findViewById(R.id.tv_thuonghieu1_home);
            tvMota = itemView.findViewById(R.id.tv_mota_home);

        }
    }
}
