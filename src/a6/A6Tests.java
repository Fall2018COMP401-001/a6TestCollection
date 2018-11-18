package a6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import a6.NoIntersectionException;
import a6.RegionImpl;


class A6Tests {
	
	Region r3456 = new RegionImpl(3,4,5,6);
	Region r1234 = new RegionImpl(1,2,3,4);
    Region r3254 = new RegionImpl(3,2,5,4);
    Region r1436 = new RegionImpl(1,4,3,6);

	@Test
	public void testGeometry () {
		assertThrows(IllegalArgumentException.class,() -> new RegionImpl(7,7,3,9));
		assertThrows(IllegalArgumentException.class, ()-> new RegionImpl(9,1,1,0));
    	
    }
	
	@Test
	public void testUnion () {
		assertEquals(r3456.union(r1234).getBottom(),6);
		assertEquals(r3456.union(r1234).getLeft(),1);
		assertEquals(r3456.union(r1234).getRight(),5);	
	}
	
	@Test
	public void testIntersect() throws NoIntersectionException {
		assertThrows(NoIntersectionException.class, () -> r3456.intersect(null));
		assertEquals(r3456.intersect(r1234).getLeft(),3);
		assertEquals(r3456.intersect(r1234).getTop(),4);
		assertEquals(r3456.intersect(r1234).getRight(),3);
        

	}
	
	
	
  
}
