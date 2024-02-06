package org.example.point;


import org.example.users.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "results")
public class Result implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "x")

    private double x;
    @Column(name = "y")

    private double y;
    @Column(name = "r")

    private double r;
    @Column(name = "hit")

    private boolean hit;

    @Column(name = "check_time")

    private String checkTime;
    @Column(name = "submit_time")

    private String submitTime;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    @Column(name = "is_valid")
    private boolean is_valid;

    public Result() {
        this.x = 0;
        this.y = 0;
        this.r = 0;
        this.hit = false;
        this.checkTime = "error";
        this.submitTime = "error";
        this.user = null;
        this.is_valid = false;
    }

    public Result(double x, double y, double r, boolean hit, String checkTime, String submitTime, User user) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.checkTime = checkTime;
        this.submitTime = submitTime;
        this.user = user;
    }

    public void setIs_valid(boolean is_valid) {
        this.is_valid = is_valid;
    }

    public boolean isIs_valid() {
        return is_valid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }
}