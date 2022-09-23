package com.example.shoesstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ThuongHieuAdapter extends RecyclerView.Adapter<ThuongHieuAdapter.ThuongHieuViewHoder> {
    private List<ThuongHieu> mThuongHieu;

    public ThuongHieuAdapter(List<ThuongHieu> mThuongHieu) {
        this.mThuongHieu = mThuongHieu;
    }

    @NonNull
    @NotNull
    @Override
    public ThuongHieuViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_thuonghieu, parent, false);
        return new ThuongHieuViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ThuongHieuViewHoder holder, int position) {
        ThuongHieu thuongHieu = mThuongHieu.get(position);
        if (thuongHieu == null) {
            return;
        }
        holder.tv_thuonghieu_home.setText(thuongHieu.getName_thuonghieu());
    }

    @Override
    public int getItemCount() {
        if (mThuongHieu != null) {
            return mThuongHieu.size();
        }
        return 0;

    }

    public class ThuongHieuViewHoder extends RecyclerView.ViewHolder {

        private TextView tv_thuonghieu_home;

        public ThuongHieuViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_thuonghieu_home = itemView.findViewById(R.id.tv_thuonghieu_home);
        }
    }
}
