package com.example.shoesstore.Moder;

import java.util.HashMap;
import java.util.Map;

public class ThuongHieu {
    private int id_thuonghieu;
    private String name_thuonghieu;

    public ThuongHieu() {
    }

    public ThuongHieu(int id_thuonghieu, String name_thuonghieu) {
        this.id_thuonghieu = id_thuonghieu;
        this.name_thuonghieu = name_thuonghieu;
    }

    public int getId_thuonghieu() {
        return id_thuonghieu;
    }

    public void setId_thuonghieu(int id_thuonghieu) {
        this.id_thuonghieu = id_thuonghieu;
    }

    public String getName_thuonghieu() {
        return name_thuonghieu;
    }

    public void setName_thuonghieu(String name_thuonghieu) {
        this.name_thuonghieu = name_thuonghieu;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id_thuonghieu", id_thuonghieu);
        result.put("name_thuonghieu", name_thuonghieu);
        return result;
    }
}
