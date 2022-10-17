package com.example.shoesstore.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoesstore.Moder.ThuongHieu;
import com.example.shoesstore.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ThuongHieuAdapter extends RecyclerView.Adapter<ThuongHieuAdapter.ThuongHieuViewHoder> {
    private final List<ThuongHieu> mThuongHieu;

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

        holder.cardview_thuonghieu.startAnimation(AnimationUtils.loadAnimation(
                holder.itemView.getContext(), R.anim.animation_1));

        holder.cardview_thuonghieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mThuongHieu != null) {
            return mThuongHieu.size();
        }
        return 0;

    }

    public class ThuongHieuViewHoder extends RecyclerView.ViewHolder {

        private final TextView tv_thuonghieu_home;
        private CardView cardview_thuonghieu;

        public ThuongHieuViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_thuonghieu_home = itemView.findViewById(R.id.tv_thuonghieu_home);
            cardview_thuonghieu = itemView.findViewById(R.id.cardview_thuonghieu);
        }
    }
}
