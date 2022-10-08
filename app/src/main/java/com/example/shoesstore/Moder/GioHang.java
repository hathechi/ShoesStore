package com.example.shoesstore.Moder;

public class GioHang {
    private int slHienTai = 1;
    private int slMoiNhat;
    private int giasp;
    private int tongtien;
    private String name;
    private String thuonghieu;
    private String mota;
    private String URLImage;

    public GioHang(int giasp, String name, String thuonghieu, String mota, String URLImage) {
        this.giasp = giasp;
        this.name = name;
        this.thuonghieu = thuonghieu;
        this.mota = mota;
        this.URLImage = URLImage;
    }

    public GioHang() {
    }

    public int getSlHienTai() {
        return slHienTai;
    }

    public void setSlHienTai(int slHienTai) {
        this.slHienTai = slHienTai;
    }

    public int getSlMoiNhat() {
        return slMoiNhat;
    }

    public void setSlMoiNhat(int slMoiNhat) {
        this.slMoiNhat = slMoiNhat;
    }

    public int getGiasp() {
        return giasp;
    }

    public void setGiasp(int giasp) {
        this.giasp = giasp;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
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

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getURLImage() {
        return URLImage;
    }

    public void setURLImage(String URLImage) {
        this.URLImage = URLImage;
    }
}
