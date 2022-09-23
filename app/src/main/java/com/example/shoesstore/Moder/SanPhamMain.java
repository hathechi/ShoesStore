package com.example.shoesstore.Moder;

import android.net.Uri;

import java.util.HashMap;
import java.util.Map;

public class SanPhamMain {
    private int id_sanpham1;
    private int gia;
    private String name;
    private String thuonghieu;
    private String mota;
    private String URLImage;

    public SanPhamMain() {
    }

    public SanPhamMain(int id_sanpham1, int gia, String name, String thuonghieu, String mota, String URLImage) {
        this.id_sanpham1 = id_sanpham1;
        this.gia = gia;
        this.name = name;
        this.thuonghieu = thuonghieu;
        this.mota = mota;
        this.URLImage = URLImage;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getId_sanpham1() {
        return id_sanpham1;
    }

    public void setId_sanpham1(int id_sanpham1) {
        this.id_sanpham1 = id_sanpham1;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getURLImage() {
        return URLImage;
    }

    public void setURLImage(String URLImage) {
        this.URLImage = URLImage;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gia", gia);
        result.put("name", name);
        result.put("thuonghieu", thuonghieu);
        result.put("mota", mota);
        result.put("urlimage", URLImage);


        return result;
    }
}
