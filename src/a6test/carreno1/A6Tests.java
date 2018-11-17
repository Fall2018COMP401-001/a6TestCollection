package a6test.carreno1;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.NoIntersectionException;
import a6.Region;
import a6.RegionImpl;

public class A6Tests {

	@Test
	public void testConstructor() {	
		try {
			new RegionImpl(5,2,1,2);
			fail("left value cannot be greater than right value");
		} catch (IllegalArgumentException e) {
			
		}
		try {
			new RegionImpl(3,5,1,1);
			fail("top value cannot be greater than bottom value");
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	@Test
	public void testUnion() {
		Region region1 = new RegionImpl(4,3,7,6);
		Region region2 = new RegionImpl(10,10,2,2);
		
		Region union = region1.union(region2);
		
		assertEquals(4, union.getLeft());
		assertEquals(3, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(6, union.getBottom());
		
		union = region2.union(region1);
		
		assertEquals(4, union.getLeft());
		assertEquals(3, union.getTop());
		assertEquals(7, union.getRight());
		assertEquals(6, union.getBottom());
		
	}
	
	@Test
	public void testIntersectionNull() {
		Region region1 = new RegionImpl(1,2,3,4);
		Region region2 = null;
		
		try {
			region1.intersect(region2);
			fail("object cannot be null");
		} catch (NoIntersectionException e) {
			
		}
	}
	
	@Test
	public void testIntersection()  {
		try {
		Region region1 = new RegionImpl(7,7,4,4);
		Region region2 = new RegionImpl(10,10,2,2);
		
		Region region3 = region1.intersect(region2);
		
		assertEquals(10, region3.getLeft());
		assertEquals(10, region3.getTop());
		assertEquals(4, region3.getRight());
		assertEquals(4, region3.getBottom());
		
		region3 = region2.union(region1);
		
		assertEquals(10, region3.getLeft());
		assertEquals(10, region3.getTop());
		assertEquals(4, region3.getRight());
		assertEquals(4, region3.getBottom());
		
		} catch (NoIntersectionException e) {
			fail("no intersections were found");
		}
		
	}
	
	
	

}
