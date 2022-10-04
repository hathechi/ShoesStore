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

public class ThuongHieuSwipeAdapter extends RecyclerView.Adapter<ThuongHieuSwipeAdapter.ThuongHieuViewHoder> {
    private final List<ThuongHieu> mThuongHieu;
    private final IclickListenerThuonghieu mIclickListener;

    public ThuongHieuSwipeAdapter(List<ThuongHieu> mThuongHieu, IclickListenerThuonghieu mIclickListener) {
        this.mThuongHieu = mThuongHieu;
        this.mIclickListener = mIclickListener;
    }

    @NonNull
    @NotNull
    @Override
    public ThuongHieuViewHoder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_thuonghieu_swipe, parent, false);
        return new ThuongHieuViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ThuongHieuViewHoder holder, int position) {
        ThuongHieu thuongHieu = mThuongHieu.get(position);
        if (thuongHieu == null) {
            return;
        }
        holder.tv_thuonghieu_home.setText(thuongHieu.getName_thuonghieu());

        holder.btnTVsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListener.onClickupdateItem(thuongHieu);
            }
        });

        holder.btnTVxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIclickListener.onClickdeleteItem(thuongHieu);
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

    // tạo interface để gọi sự kiện update ra bên ngoài
    public interface IclickListenerThuonghieu {
        void onClickupdateItem(ThuongHieu thuongHieu);

        void onClickdeleteItem(ThuongHieu thuongHieu);
    }

    public class ThuongHieuViewHoder extends RecyclerView.ViewHolder {

        private final TextView tv_thuonghieu_home;
        private final TextView btnTVsua;
        private final TextView btnTVxoa;

        public ThuongHieuViewHoder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_thuonghieu_home = itemView.findViewById(R.id.tv_thuonghieu_edit);
            btnTVsua = itemView.findViewById(R.id.btntvSua_thuonghieu);
            btnTVxoa = itemView.findViewById(R.id.btntvXoa_thuonghieu);
        }
    }
}
