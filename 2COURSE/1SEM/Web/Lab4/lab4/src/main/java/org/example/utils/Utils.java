package org.example.utils;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    private final double x;
    private final double y;
    private final double r;

    public Utils(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public boolean checkHit() {
        return (-r <= x) && (x <= 0) && (-r / 2 <= y) && (y <= 0) || //rect
                (0 <= x) && (x <= r) && (-r <= y) && (y <= 0) && (x * x + y * y <= r * r) || // circle
                (0 <= x) && (x <= r / 2) && (0 <= y) && (y <= r) && (y <= -2 * x + r); //triangle
    }

    public boolean validate() {
        return x >= -3 && x <= 5 && y >= -5 && y <= 5 && r >= 1 && r <= 5;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
