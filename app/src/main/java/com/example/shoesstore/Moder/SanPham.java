package com.example.shoesstore.Moder;

public class SanPham {
    private int id_sanpham;
    private String image_sanpham;
    private String title_sanpham;

    public SanPham(int id_sanpham, String image_sanpham, String title_sanpham) {
        this.id_sanpham = id_sanpham;
        this.image_sanpham = image_sanpham;
        this.title_sanpham = title_sanpham;
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

    public String getTitle_sanpham() {
        return title_sanpham;
    }

    public void setTitle_sanpham(String title_sanpham) {
        this.title_sanpham = title_sanpham;
    }
}
