package com.example.shoesstore.Moder;

public class SanPham {
    private int id_sanpham;
    private String image_sanpham;
    private int gia_sanpham;

    public SanPham(int id_sanpham, String image_sanpham, int gia_sanpham) {
        this.id_sanpham = id_sanpham;
        this.image_sanpham = image_sanpham;
        this.gia_sanpham = gia_sanpham;
    }

    public int getId_sanpham() {
        return id_sanpham;
    }

    public void setId_sanpham(int id_sanpham) {
        this.id_sanpham = id_sanpham;
    }

    public String getImage_sanpham() {
        return image_sanpham;
    }

    public void setImage_sanpham(String image_sanpham) {
        this.image_sanpham = image_sanpham;
    }

    public int getGia_sanpham() {
        return gia_sanpham;
    }

    public void setGia_sanpham(int gia_sanpham) {
        this.gia_sanpham = gia_sanpham;
    }
}
