package com.pgrela.pa;

import org.junit.Test;

import junit.framework.Assert;

/**
 * @author: pgrela
 */
public class mroTest2 {
    @Test
     public void test(){

        String sd = String.format("%1$-32d", 344545L);
        String ss = String.format("%1$-32s", "344545");
        Assert.assertEquals(sd,ss);
    }
}
