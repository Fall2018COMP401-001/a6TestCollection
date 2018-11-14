package a6test.benespo;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;

public class A6Tests {

	
	
	@Test
	public void nullRegionUnionTest() {
		Region a = new RegionImpl(1,1,6,5);
		Region union = a.union(null);
		
		assertEquals(a.getLeft(), union.getLeft());
		assertEquals(a.getTop(), union.getTop());
		assertEquals(a.getRight(), union.getRight());
		assertEquals(a.getBottom(), union.getBottom());
		assertEquals(a, union);
	}
	
	@Test
	public void basicRegionUnionTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(6, 6, 10, 10);
		
		Region union = a.union(b);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
		// Try the other way also.
		
		union = b.union(a);
		
		assertEquals(0, union.getLeft());
		assertEquals(0, union.getTop());
		assertEquals(10, union.getRight());
		assertEquals(10, union.getBottom());
		
	}
	
	@Test
	public void basicRegionIntersectTest() {
		Region a = new RegionImpl(0, 0, 5, 5);
		Region b = new RegionImpl(3, 3, 7, 7);
		Region c = new RegionImpl(6, 6, 9, 9);
	
		Region intersect;
		try {
			intersect = a.intersect(b);
			assertEquals(3, intersect.getLeft());
			assertEquals(5, intersect.getRight());
			assertEquals(5, intersect.getBottom());
			assertEquals(3, intersect.getTop());
		} catch (NoIntersectionException e1) {
			e1.printStackTrace();
		}
		
		//Test no intersection 
		try {
			a.intersect(c);
		} catch (NoIntersectionException e) {
			
		}
			
	}
	
	

}
