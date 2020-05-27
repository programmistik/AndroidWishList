package com.example.testxml;

import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

 class ProductViewHolder {
    private CheckBox checkBox ;
    private TextView textView ;
     private TextView priceView ;
     private ImageView pictureView ;

    public ProductViewHolder() {}
    public ProductViewHolder( TextView textView, TextView priceView, ImageView pictureView, CheckBox checkBox ) {
        this.checkBox = checkBox ;
        this.textView = textView ;
        this.priceView = priceView ;
        this.pictureView = pictureView;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public TextView getTextView() {
        return textView;
    }
    public void setTextView(TextView textView) {
        this.textView = textView;
    }

     public TextView getPriceView() {
         return priceView;
     }
     public void setPriceView(TextView priceView) {
         this.priceView = priceView;
     }

     public ImageView getPicView() {
         return pictureView;
     }
     public void setPicView(ImageView pictureView) {
         this.pictureView = pictureView;
     }
}
