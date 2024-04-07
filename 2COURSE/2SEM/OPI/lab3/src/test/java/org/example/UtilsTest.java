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
    public void testCheckHitFalse() throws InterruptedException {
        Utils util = new Utils(4, 4, 3);
        Thread.sleep(5000);
        Assert.assertFalse(util.checkHit());
    }


}
