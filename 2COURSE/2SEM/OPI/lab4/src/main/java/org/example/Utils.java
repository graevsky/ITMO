package org.example;

public class Utils {
    private final double x;
    private final double y;
    private final double r;
    public Utils(double x, double y,double r){
        this.x = x;
        this.y = y;
        this.r = r;
    }
    public boolean checkHit(){
        return (0 >= x ) && (x >= -r) && (0 <= y ) && (y <= r/2) || //rect
                (r >= x) && (x >= 0) && (r >= y ) && (y >= 0) && (x * x + y * y <= (r/2) * (r/2)) || // circle
                (0 >= x) && (x >= -r) && (0 >= y) && (y >= -r) && (y >= -x -r); //triangle
    }
    public boolean validate(){
        return x >= -3 && x <= 4 && y >= -5 && y <= 3 && r >= 1 && r <= 5;
    }
}
