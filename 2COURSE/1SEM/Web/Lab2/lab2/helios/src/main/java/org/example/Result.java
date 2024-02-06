package org.example;
import java.time.LocalDateTime;

public class Result {
    private final double x, y, r;
    private final boolean hit;
    private final double executionTime;
    private final LocalDateTime currentTime;

    public Result(double x, double y, double r, boolean hit, double executionTime, LocalDateTime currentTime) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.executionTime = executionTime;
        this.currentTime = currentTime;
    }
    public double getExecutionTime() {
        return executionTime;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public boolean isHit() {
        return hit;
    }
}
