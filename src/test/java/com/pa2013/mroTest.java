package com.pa2013;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.pa2013.mro.Drop;
import com.pa2013.mro.Node;

//import org.testng.annotations.Test;
/**
 * @author: pgrela
 */
public class mroTest {
    Node root;
    mro m;
    int[] getAnts(){
        int[] tmp = {1,3,4,6,7};
        return tmp;
    }
    @Test
    public void initilazeRoot(){
        initializeMro();
        String expected = "1 3 4 6 7 ";
        assertRootState(expected);
        StringBuilder builder;
        String result;
        assertEquals(root.firstToTheLeftFrom(1),null);
        assertEquals(root.firstToTheLeftFrom(2).intValue(),1);
        assertEquals(root.firstToTheLeftFrom(3).intValue(),1);
        assertEquals(root.firstToTheLeftFrom(4).intValue(),3);
        assertEquals(root.firstToTheLeftFrom(5).intValue(),4);
        assertEquals(root.firstToTheLeftFrom(6).intValue(),4);
        assertEquals(root.firstToTheLeftFrom(7).intValue(),6);
        assertEquals(root.firstToTheLeftFrom(8).intValue(),7);
        assertEquals(root.firstToTheLeftFrom(9).intValue(),7);
        assertEquals(root.firstToTheRightFrom(0).intValue(),1);
        assertEquals(root.firstToTheRightFrom(1).intValue(),3);
        assertEquals(root.firstToTheRightFrom(2).intValue(),3);
        assertEquals(root.firstToTheRightFrom(3).intValue(),4);
        assertEquals(root.firstToTheRightFrom(4).intValue(),6);
        assertEquals(root.firstToTheRightFrom(5).intValue(),6);
        assertEquals(root.firstToTheRightFrom(6).intValue(),7);
        assertEquals(root.firstToTheRightFrom(7),null);
        assertEquals(root.firstToTheRightFrom(8),null);
        assertEquals(root.firstToTheRightFrom(9),null);
        root.move(3,5,-1);
        builder = new StringBuilder();
        root.readAll(builder,0);
        result = builder.toString();
        assertEquals("1 2 3 6 7 ",result);
        assertEquals(root.firstToTheLeftFrom(1),null);
        assertEquals(root.firstToTheLeftFrom(2).intValue(),1);
        assertEquals(root.firstToTheLeftFrom(3).intValue(),2);
        assertEquals(root.firstToTheLeftFrom(4).intValue(),3);
        assertEquals(root.firstToTheLeftFrom(5).intValue(),3);
        assertEquals(root.firstToTheLeftFrom(6).intValue(),3);
        assertEquals(root.firstToTheLeftFrom(7).intValue(),6);
        assertEquals(root.firstToTheLeftFrom(8).intValue(),7);
        assertEquals(root.firstToTheLeftFrom(9).intValue(),7);
        assertEquals(root.firstToTheRightFrom(0).intValue(),1);
        assertEquals(root.firstToTheRightFrom(1).intValue(),2);
        assertEquals(root.firstToTheRightFrom(2).intValue(),3);
        assertEquals(root.firstToTheRightFrom(3).intValue(),6);
        assertEquals(root.firstToTheRightFrom(4).intValue(),6);
        assertEquals(root.firstToTheRightFrom(5).intValue(),6);
        assertEquals(root.firstToTheRightFrom(6).intValue(),7);
        assertEquals(root.firstToTheRightFrom(7),null);
        assertEquals(root.firstToTheRightFrom(8),null);
        assertEquals(root.firstToTheRightFrom(9),null);
        root.move(2,5,1);
        builder = new StringBuilder();
        root.readAll(builder,0);
        result = builder.toString();
        assertEquals("1 3 4 6 7 ",result);
        assertEquals(root.firstToTheLeftFrom(1),null);
        assertEquals(root.firstToTheLeftFrom(2).intValue(),1);
        assertEquals(root.firstToTheLeftFrom(3).intValue(),1);
        assertEquals(root.firstToTheLeftFrom(4).intValue(),3);
        assertEquals(root.firstToTheLeftFrom(5).intValue(),4);
        assertEquals(root.firstToTheLeftFrom(6).intValue(),4);
        assertEquals(root.firstToTheLeftFrom(7).intValue(),6);
        assertEquals(root.firstToTheLeftFrom(8).intValue(),7);
        assertEquals(root.firstToTheLeftFrom(9).intValue(),7);
        assertEquals(root.firstToTheRightFrom(0).intValue(),1);
        assertEquals(root.firstToTheRightFrom(1).intValue(),3);
        assertEquals(root.firstToTheRightFrom(2).intValue(),3);
        assertEquals(root.firstToTheRightFrom(3).intValue(),4);
        assertEquals(root.firstToTheRightFrom(4).intValue(),6);
        assertEquals(root.firstToTheRightFrom(5).intValue(),6);
        assertEquals(root.firstToTheRightFrom(6).intValue(),7);
        assertEquals(root.firstToTheRightFrom(7),null);
        assertEquals(root.firstToTheRightFrom(8),null);
        assertEquals(root.firstToTheRightFrom(9),null);
    }

    private void assertRootState(String expected) {
        StringBuilder builder = new StringBuilder();
        root.readAll(builder,0);
        String result = builder.toString();
        assertEquals(expected,result);
    }

    private void initializeMro() {
        m=new mro();
        int ants[]=getAnts();
        m.root = root = m.createNodeBranch(ants,0,ants.length);
    }
    @Test public void testDrops(){
        initializeMro();
        assertRootState("1 3 4 6 7 ");
        Drop drop1 = m.new Drop(1,2);
        m.drop(drop1);
        assertEquals(2,drop1.eatenFromLeft);
        assertEquals(2,drop1.eatenFromRight);
        assertEquals(1,drop1.updateLeft);
        assertEquals(1,drop1.updateRight);
        m.drink(m.new Drop(2,2));
        assertRootState("2 2 3 5 6 ");
        Drop drop2 = m.new Drop(2,9);
        m.drop(drop2);
        assertEquals(5,drop2.eatenFromLeft);
        assertEquals(mro.MAX,drop2.eatenFromRight);
        assertEquals(2,drop2.updateLeft);
        assertEquals(2,drop2.updateRight);
        assertRootState("2 2 3 5 6 ");
        Drop drop3 = m.new Drop(4,1);
        m.drop(drop3);
        assertEquals(mro.MAX,drop3.eatenFromLeft);
        assertEquals(7, drop3.eatenFromRight);
        assertEquals(4,drop3.updateLeft);
        assertEquals(4,drop3.updateRight);
        assertRootState("4 4 5 7 8 ");
        Drop drop4 = m.new Drop(4,5);
        assertEquals(2,m.drops.size());
        m.drop(drop4);
        assertRootState("4 4 5 7 8 ");
        assertEquals(2,m.drops.size());
        m.drink(m.new Drop(5,9));
        assertRootState("3 3 4 8 9 ");
        assertThat(m.drops.size()).isEqualTo(1);
        m.drink(m.new Drop(7,1));
        assertRootState("1 1 2 6 7 ");
    }
}
