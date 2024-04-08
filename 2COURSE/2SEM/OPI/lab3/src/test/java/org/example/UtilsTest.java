package org.example;

import org.junit.Assert;
import org.junit.Test;


public class UtilsTest {

    @Test
    public void testCheckHitTrue() {
        Utils util = new Utils(1, 1, 3);
        Assert.assertTrue(util.checkHit());
    }

    @Test
    public void testCheckHitFalse() {
        Utils util = new Utils(4, 4, 3);
        Assert.assertFalse(util.checkHit());
    }

    @Test
    public void testRectangleAreaHit() {
        Utils util = new Utils(-1, 1, 2);
        Assert.assertTrue(util.checkHit());
    }

    @Test
    public void testRectangleAreaMiss() {
        Utils util = new Utils(-3, 1.5, 2);
        Assert.assertFalse(util.checkHit());
    }

    @Test
    public void testCircleAreaHit() {
        Utils util = new Utils(0.5, 0.5, 2);
        Assert.assertTrue(util.checkHit());
    }

    @Test
    public void testCircleAreaMiss() {
        Utils util = new Utils(1, 1, 1);
        Assert.assertFalse(util.checkHit());
    }

    @Test
    public void testTriangleAreaHit() {
        Utils util = new Utils(-1, -1, 2);
        Assert.assertTrue(util.checkHit());
    }

    @Test
    public void testTriangleAreaMiss() {
        Utils util = new Utils(-2, -3, 2);
        Assert.assertFalse(util.checkHit());
    }

    @Test
    public void testValidationTrue() {
        Utils util = new Utils(0, 0, 3);
        Assert.assertTrue(util.validate());
    }

    @Test
    public void testValidationFalseX() {
        Utils util = new Utils(5, 2, 3);
        Assert.assertFalse(util.validate());
    }

    @Test
    public void testValidationFalseY() {
        Utils util = new Utils(2, -6, 3);
        Assert.assertFalse(util.validate());
    }

    @Test
    public void testValidationFalseR() {
        Utils util = new Utils(2, 2, 0);
        Assert.assertFalse(util.validate());
    }
}
