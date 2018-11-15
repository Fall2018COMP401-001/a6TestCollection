package a6test.sam1230;

import static org.junit.Assert.*;
import javax.swing.plaf.synth.Region;
import org.junit.Test;
import a6.*;

public class A6Tests {
  @Test
  public void regionImplConstructor() {
    RegionImpl r = new RegionImpl(2,5,5,8);
    assertEquals(5, r.getTop(), .1);
    assertEquals(2, r.getLeft(),.1 );
    assertEquals(5, r.getRight(), .1);
    assertEquals(8, r.getBottom(), .1);
  }
  
  @Test
  public void testIntersect() {
    RegionImpl r = new RegionImpl(1,5,7,8);
    RegionImpl s = new RegionImpl(2,6,7,8);
    try {
      r.intersect(s);
     
    }catch ( NoIntersectionException ex ){
     
    }
  }
  @Test
  public void testInterectConstruction() {
    RegionImpl r = new RegionImpl(1,5,7,8);
    RegionImpl s = new RegionImpl(2,6,7,8);
    try {
      r.intersect(s);
      a6.Region region = new RegionImpl(1,5,3,9);
      region = r.intersect(s);
      assertEquals(1, region.getLeft(),.1);
      assertEquals(5, region.getTop(), .1);
      assertEquals(7, region.getRight(),.1);
      assertEquals(8, region.getBottom(), .1);
    }catch ( NoIntersectionException ex ){
     
    }
  }
}

