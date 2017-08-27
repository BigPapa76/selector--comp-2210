import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class SelectorTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @Before public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void minTest() {
   
      Selector s = new Selector();
      int [] a = {1,1,1,1,1};
      s.min(a);
      
      
      
      
      Assert.assertEquals("Default test added by jGRASP. Delete "
            + "this test once you have added your own.", s.min(a), 1);
   }
   
   @Test public void maxTest() {
   
      Selector s = new Selector();
      int[] a = {2, 7, 8, 8, 4, 7};
      s.max(a);
      
      Assert.assertEquals("Max Test", s.max(a), 8);
      
      
   }
   
   @Test public void rangeTest() {
   
      Selector s = new Selector();
      int [] a = {1, 2, 3, 4, 5};
      s.range(a, 2, 4);
      
      int [] e = {3};
   
x      
   }
   
}
