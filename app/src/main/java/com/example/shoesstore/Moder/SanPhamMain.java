package com.example.shoesstore.Moder;

import java.util.HashMap;
import java.util.Map;

public class SanPhamMain {
    private int id_sanpham1;
    private int gia;
    private String name;
    private String thuonghieu;
    private String mota;
    private String chitiet;
    private String URLImage;
    private int slNhapvao;
    private int slDaban;

    public SanPhamMain() {
    }

    public SanPhamMain(int id_sanpham1, int gia, String name, String thuonghieu, String mota, String chitiet, String URLImage, int slNhapvao) {
        this.id_sanpham1 = id_sanpham1;
        this.gia = gia;
        this.name = name;
        this.thuonghieu = thuonghieu;
        this.mota = mota;
        this.chitiet = chitiet;
        this.URLImage = URLImage;
        this.slNhapvao = slNhapvao;
    }

    public int getSlNhapvao() {
        return slNhapvao;
    }

    public void setSlNhapvao(int slNhapvao) {
        this.slNhapvao = slNhapvao;
    }

    public int getSlDaban() {
        return slDaban;
    }

    public void setSlDaban(int slDaban) {
        this.slDaban = slDaban;
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

    public String getChitiet() {
        return chitiet;
    }

    public void setChitiet(String chitiet) {
        this.chitiet = chitiet;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("gia", gia);
        result.put("name", name);
        result.put("thuonghieu", thuonghieu);
        result.put("mota", mota);
        result.put("chitiet", chitiet);
        result.put("urlimage", URLImage);
        result.put("slNhapvao", slNhapvao);


        return result;
    }
}
