package com.example.admin.mydailystudy.bean;

public class SkinColor {
    private int color;

    private String colorName;

    private boolean isChoosed;

    public SkinColor(int color, String colorName) {
        this.color = color;
        this.colorName = colorName;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
