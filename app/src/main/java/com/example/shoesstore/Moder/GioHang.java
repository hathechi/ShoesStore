package com.example.shoesstore.Moder;

public class GioHang {
    private int slHienTai = 1;
    private int idSanpham;
    private int slMoiNhat;
    private int giasp;
    private int slSanphamDaban;
    private int tongtien;
    private String name;
    private String thuonghieu;
    private String mota;
    private String URLImage;
    private String Color;
    private String Size;

    public GioHang(int idSanpham, int slSanphamDaban, int giasp, String name, String thuonghieu, String mota, String URLImage, String Color, String Size) {
        this.idSanpham = idSanpham;
        this.slSanphamDaban = slSanphamDaban;
        this.giasp = giasp;
        this.name = name;
        this.thuonghieu = thuonghieu;
        this.mota = mota;
        this.URLImage = URLImage;
        this.Color = Color;
        this.Size = Size;
    }

    public GioHang() {
    }

    public int getSlSanphamDaban() {
        return slSanphamDaban;
    }

    public void setSlSanphamDaban(int slSanphamDaban) {
        this.slSanphamDaban = slSanphamDaban;
    }

    public int getIdSanpham() {
        return idSanpham;
    }

    public void setIdSanpham(int idSanpham) {
        this.idSanpham = idSanpham;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
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
