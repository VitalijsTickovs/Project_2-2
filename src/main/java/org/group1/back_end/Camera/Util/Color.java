package org.group1.back_end.Camera.Util;

public class Color {

    public int r, g, b;

    public Color(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(int[] rgb){
        this.r = rgb[0];
        this.g = rgb[1];
        this.b = rgb[2];
    }
}
