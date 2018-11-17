 package a6test.davidgri;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.fail;

import org.junit.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

import static org.junit.Assert.*;

import org.junit.Test;
import a6.*;


public class A6Tests {

	
	@Test
	public void testRegionConstructors() {	
			Region r; 
		
			try {
				r = new RegionImpl(1, 1, 0, 0);
				fail("Bounds are out of order");
			} catch (IllegalArgumentException e) {				
			}			
			try {
				r = new RegionImpl(4, 4, 8, 3);
				fail("Bounds are out of order");
			} catch (IllegalArgumentException e) {				
			}	
} 
	@Test
	public void testRegionGetters() {
		Region r = new RegionImpl(1, 1, 1, 1); 
			assertEquals(r.getBottom(), 1); 
			assertEquals(r.getTop(), 1); 
			assertEquals(r.getLeft(), 1); 
			assertEquals(r.getRight(), 1); 		
	}
	
	@Test
	public void testUnion() {
		Region small = new RegionImpl(1, 1, 2, 2); 
		Region big = new RegionImpl(2, 2, 5, 5);
		Region union = small.union(big); 
		assertEquals(union.getLeft(), 1);
		assertEquals(union.getTop(), 1);
		assertEquals(union.getBottom(), 5); 
		assertEquals(union.getRight(), 5); 		
	}
}
