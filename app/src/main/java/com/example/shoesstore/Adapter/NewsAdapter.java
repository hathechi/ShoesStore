package com.example.shoesstore.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shoesstore.Moder.NewsItem;
import com.example.shoesstore.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsItem> newsItems = new ArrayList<>();

    public NewsAdapter(Context context, List<NewsItem> newsItems) {
        this.context = context;
        this.newsItems = newsItems;
    }

    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public Object getItem(int i) {
        return newsItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView tvTitle, tvDes;
        ImageView imageView;

        NewsItem item = newsItems.get(i);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.custom_item_news, null);
        }

        tvTitle = view.findViewById(R.id.tvTitle_news);
        tvDes = view.findViewById(R.id.tvDes_news);
        imageView = view.findViewById(R.id.iv_img_news);

        tvTitle.setText(item.getTitle());
        Glide.with(context).load(newsItems.get(i).getLinkImage()).into(imageView);
        tvDes.setText(item.getDescription());


        return view;
    }


}
