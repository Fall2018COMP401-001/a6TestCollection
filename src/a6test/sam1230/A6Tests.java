package a6test.sam1230;
import a6.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

class a6tests {

  @Test
  public void regionImplConstructor() {
    Region r = new RegionImpl(2,5,5,8);
    assertEquals(5, r.getTop(), .1);
    assertEquals(2, r.getLeft(),.1 );
    assertEquals(5, r.getRight(), .1);
    assertEquals(8, r.getBottom(), .1);
  }
  
  @Test
  public void testIntersect() {
    Region r = new RegionImpl(1,5,7,8);
    Region s = new RegionImpl(2,6,7,8);
    try {
      r.intersect(s);
     
    }catch ( NoIntersectionException ex ){
     
    }
    
  }
  @Test
  public void testInterectConstruction() {
    Region r = new RegionImpl(1,5,7,8);
    Region s = new RegionImpl(2,6,7,8);
    try {
      r.intersect(s);
      Region region = new RegionImpl(1,5,3,9);
      region = r.intersect(s);
      assertEquals(1, region.getLeft(),.1);
      assertEquals(5, region.getTop(), .1);
      assertEquals(7, region.getRight(),.1);
      assertEquals(8, region.getBottom(), .1);
    }catch ( NoIntersectionException ex ){
     
    }
  }
}
