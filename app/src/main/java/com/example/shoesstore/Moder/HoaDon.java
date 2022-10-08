package com.example.shoesstore.Moder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HoaDon implements Serializable {
    private String maHD;
    private String name_khachhang;
    private String ngaytaoHD;
    private String giotaoHD;
    private boolean tinhtranghoadon;
    private String danhsachmathang;
    private long tongtien;

    public HoaDon() {
    }

    public HoaDon(String maHD, String name_khachhang, String ngaytaoHD, String giotaoHD, boolean tinhtranghoadon, String danhsachmathang, long tongtien) {
        this.maHD = maHD;
        this.name_khachhang = name_khachhang;
        this.ngaytaoHD = ngaytaoHD;
        this.giotaoHD = giotaoHD;
        this.tinhtranghoadon = tinhtranghoadon;
        this.danhsachmathang = danhsachmathang;
        this.tongtien = tongtien;
    }

    public String getGiotaoHD() {
        return giotaoHD;
    }

    public void setGiotaoHD(String giotaoHD) {
        this.giotaoHD = giotaoHD;
    }


    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getName_khachhang() {
        return name_khachhang;
    }

    public void setName_khachhang(String name_khachhang) {
        this.name_khachhang = name_khachhang;
    }

    public String getNgaytaoHD() {
        return ngaytaoHD;
    }

    public void setNgaytaoHD(String ngaytaoHD) {
        this.ngaytaoHD = ngaytaoHD;
    }

    public boolean getTinhtranghoadon() {
        return tinhtranghoadon;
    }

    public void setTinhtranghoadon(boolean tinhtranghoadon) {
        this.tinhtranghoadon = tinhtranghoadon;
    }

    public String getDanhsachmathang() {
        return danhsachmathang;
    }

    public void setDanhsachmathang(String danhsachmathang) {
        this.danhsachmathang = danhsachmathang;
    }

    public long getTongtien() {
        return tongtien;
    }

    public void setTongtien(long tongtien) {
        this.tongtien = tongtien;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("maHD", maHD);
        result.put("name_khachhang", name_khachhang);
        result.put("giotaoHD", giotaoHD);
        result.put("ngaytaoHD", ngaytaoHD);
        result.put("tinhtranghoadon", tinhtranghoadon);
        result.put("tongtien", tongtien);
        result.put("danhsachmathang", danhsachmathang);


        return result;
    }
}
