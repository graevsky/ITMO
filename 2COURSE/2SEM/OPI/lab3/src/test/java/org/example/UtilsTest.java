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


}
