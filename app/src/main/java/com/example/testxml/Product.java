package com.example.testxml;

public class Product {

    private String name;
    private int picture;
    private double aznPrice;
    private boolean checked = false ;

    Product(String name, int picture, double usdPrice) {
        this.name = name;
        this.picture = picture;
        this.aznPrice = usdPrice;
        this.checked = false;
    }

    public int getPicture() {
        return this.picture;
    }

    public double getAznPrice() {
        return aznPrice;
    }

    public String getName() {
        return this.name;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public void toggleChecked() {
        checked = !checked ;
    }

}
